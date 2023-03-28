package com.github.yyy.www.window;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.github.yyy.www.startup.*;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring 启动耗时展示窗口
 */
public class AsstWindow extends SimpleToolWindowPanel {

    private Project project;


    public AsstWindow(Project project, String jsonStr) {
        super(true, true);
        this.project = project;

        // 创建树形结构
        if (CharSequenceUtil.isBlank(jsonStr)) {
            return;
        }
        // 解析json 生成树形结构
        StartupData startupData = JSON.parseObject(jsonStr, StartupData.class);


        // 创建树
        JScrollPane scrollPane = new JBScrollPane(buildTree(startupData));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // 添加树形结构到工具窗口中
        setContent(scrollPane);

    }

    @NotNull
    private static Icon getIcon(long duration) {
        Icon icon;
        if (duration < 100) {
            icon = AllIcons.General.Information;
        } else if (duration < 500) {
            icon = AllIcons.General.Warning;
        } else {
            icon = AllIcons.General.Error;
        }
        return icon;
    }

    private Tree buildTree(StartupData startupData) {
        DefaultMutableTreeNode root = this.toSwing(startupData);
        Tree tree = new Tree(new DefaultTreeModel(root));

        // 设置自定义单元格渲染器
        tree.setCellRenderer(new ColoredTreeCellRenderer() {


            @Override
            public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value instanceof DefaultMutableTreeNode) {
                    // 设置字体
                    Font font = new Font("Arial", Font.BOLD, 12);
                    setFont(font);
                    long duration = 0L;
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    Object userObject = node.getUserObject();
                    if (userObject instanceof NodeData) {
                        NodeData nodeData = (NodeData) userObject;
                        duration = parseDuration(nodeData);
                        // 设置图标
                        Icon icon = AsstWindow.getIcon(duration);
                        setIcon(icon);
                    }


                    SimpleTextAttributes simpleTextAttributes = new SimpleTextAttributes(
                            SimpleTextAttributes.STYLE_PLAIN | SimpleTextAttributes.STYLE_BOLD,
                            getColorByDuration(duration));
                    append(userObject.toString(), simpleTextAttributes);
                }
            }
        });

        return tree;
    }

    /**
     * 将树形结构转为 swing
     */
    private DefaultMutableTreeNode toSwing(StartupData startupData) {

        // 转为树形结构
        List<StartupEvent> roots = startupData.convertTree();

        // 将根节点转换为 DefaultMutableTreeNode 对象
        List<DefaultMutableTreeNode> rootNodes = new ArrayList<>();
        for (StartupEvent event : roots) {
            NodeData nodeData = generateNodeData(event);
            DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(nodeData.toString());
            treeNode.setUserObject(nodeData);
            createTreeNodes(treeNode, event.getChildren());
            rootNodes.add(treeNode);
        }

        // 定义 JTree
        DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
        rootTreeNode.setUserObject("SpringBootVersion:" + startupData.getSpringBootVersion());
        for (DefaultMutableTreeNode rootNode : rootNodes) {
            rootTreeNode.add(rootNode);
        }

        return rootTreeNode;

    }

    /**
     * 递归创建树节点
     */
    private void createTreeNodes(DefaultMutableTreeNode parent, List<StartupEvent> children) {
        if (children != null) {
            for (StartupEvent childEvent : children) {
                NodeData nodeData = generateNodeData(childEvent);
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeData.toString());
                child.setUserObject(nodeData);
                parent.add(child);
                createTreeNodes(child, childEvent.getChildren());
            }
        }
    }

    /**
     * 生成节点名称
     */
    private NodeData generateNodeData(StartupEvent event) {
        StartupStep startupStep = event.getStartupStep();

        NodeData nodeData = new NodeData();
        nodeData.setStage(startupStep.getName());
        List<StartupTag> tags = startupStep.getTags();
        if (CollUtil.isNotEmpty(tags)) {
            for (StartupTag tag : tags) {
                if (tag.getKey().equals("beanName")) {
                    nodeData.setBeanName(tag.getValue());
                }
            }
        }
        if (CharSequenceUtil.isNotBlank(event.getDuration())) {
            nodeData.setCostTime(Duration.parse(event.getDuration()).toMillis());
        }
        return nodeData;
    }

    //--------------- 设置渲染 ---------------//

    /**
     * 解析出消耗时长
     */
    private long parseDuration(NodeData nodeData) {
        return nodeData.getCostTime() == null ? 0L : nodeData.getCostTime();
    }

    /**
     * 获取颜色
     */
    private JBColor getColorByDuration(long duration) {
        if (duration < 100) {
            return JBColor.GREEN;
        } else if (duration < 500) {
            return JBColor.ORANGE;
        } else {
            return JBColor.RED;
        }
    }

}
