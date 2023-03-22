package com.github.yyy.www.window;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.alibaba.fastjson.JSON;
import com.github.yyy.www.startup.StartupData;
import com.github.yyy.www.startup.StartupEvent;
import com.github.yyy.www.startup.StartupStep;
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
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Spring 启动耗时展示窗口
 */
public class AsstWindow extends SimpleToolWindowPanel {

    public AsstWindow(Project project) {
        super(true, true);

        // 创建树形结构
        String filePath;
        if (System.getProperty("os.name").startsWith("Windows")) {
            filePath = "D:\\Idea\\workplace\\danding-business-order-monitor\\tmp.txt";
        } else {
            filePath = "/home/meta/Desktop/tmp.txt";
        }
        String jsonStr = FileUtil.readString(filePath, StandardCharsets.UTF_8);
        StartupData startupData = JSON.parseObject(jsonStr, StartupData.class);
        DefaultMutableTreeNode root = this.toSwing(startupData);
        Tree tree = new Tree(new DefaultTreeModel(root));

        // 设置自定义单元格渲染器
        tree.setCellRenderer(new ColoredTreeCellRenderer() {
            @Override
            public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    Object userObject = node.getUserObject();
                    String str = userObject.toString();
                    long duration = parseDuration(str);
                    append(str, new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, getColorByDuration(duration)));
                }
            }
        });

        // 创建滚动panel
        JScrollPane scrollPane = new JBScrollPane(tree);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // 添加树形结构到工具窗口中
        setContent(scrollPane);

    }

    //--------------- 转为 swing ---------------//

    /**
     * 将树形结构转为 swing
     */
    private DefaultMutableTreeNode toSwing(StartupData startupData) {

        // 转为树形结构
        List<StartupEvent> roots = startupData.convertTree();

        // 将根节点转换为 DefaultMutableTreeNode 对象
        List<DefaultMutableTreeNode> rootNodes = new ArrayList<>();
        for (StartupEvent event : roots) {
            DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(generalName(event));
            createTreeNodes(treeNode, event.getChildren());
            rootNodes.add(treeNode);
        }

        // 定义 JTree
        DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
        rootTreeNode.setUserObject("ROOT\n"
                + "springBootVersion：" + startupData.getSpringBootVersion() + "\n"
                + "startTime：" + startupData.getTimeline().getStartTime()
        );
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
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(generalName(childEvent));
                parent.add(child);
                createTreeNodes(child, childEvent.getChildren());
            }
        }
    }

    /**
     * 生成节点名称
     */
    private String generalName(StartupEvent event) {
        StartupStep startupStep = event.getStartupStep();
        // name
        String name = startupStep.getName();
        // tag
        String[] tags = startupStep.getTags();
        String tagsStr = String.join("\n", tags);
        // duration
        String durationStr = event.getDuration();
        if (CharSequenceUtil.isNotBlank(durationStr)) {
            Duration duration = Duration.parse(durationStr);
            long millis = duration.toMillis();
            durationStr = "(" + millis + ")ms";
        }
        return "类型：" + name + "\n" + (CharSequenceUtil.isNotBlank(tagsStr) ? tagsStr + "\n" : "") + " 耗时：" + durationStr;
    }

    //--------------- 设置渲染 ---------------//

    /**
     * 解析出消耗时长
     */
    private long parseDuration(String s) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            String durationStr = matcher.group(1);
            return Long.parseLong(durationStr);
        }
        return 0L;
    }

    /**
     * 获取颜色
     */
    private JBColor getColorByDuration(long duration) {
        if (duration < 1000) {
            return JBColor.GREEN;
        } else if (duration < 5000) {
            return JBColor.ORANGE;
        } else {
            return JBColor.RED;
        }
    }

}
