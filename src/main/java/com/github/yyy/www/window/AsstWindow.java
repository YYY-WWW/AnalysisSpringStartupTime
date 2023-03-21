package com.github.yyy.www.window;

import cn.hutool.core.io.FileUtil;
import com.github.yyy.www.util.StartupUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.nio.charset.StandardCharsets;

public class AsstWindow extends SimpleToolWindowPanel {

    public AsstWindow(Project project) {
        super(true, true);

        // 创建树形结构
        DefaultMutableTreeNode root = StartupUtil.toSwing(FileUtil.readString("/home/meta/Desktop/tmp.txt", StandardCharsets.UTF_8));
        Tree tree = new Tree(new DefaultTreeModel(root));

        // 设置自定义单元格渲染器
        tree.setCellRenderer(new ColoredTreeCellRenderer() {
            @Override
            public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    Object userObject = node.getUserObject();
                    append(userObject.toString(), SimpleTextAttributes.GRAYED_ATTRIBUTES);
                }
            }
        });

        // 添加树形结构到工具窗口中
        setContent(tree);
    }

}
