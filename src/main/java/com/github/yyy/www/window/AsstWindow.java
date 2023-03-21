package com.github.yyy.www.window;

import cn.hutool.core.io.FileUtil;
import com.github.yyy.www.util.StartupUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class AsstWindow extends SimpleToolWindowPanel {

    public AsstWindow(Project project) {
        super(true, true);

        // 创建树形结构
        DefaultMutableTreeNode root = StartupUtil.toSwing(FileUtil.readString("/home/meta/Desktop/tmp.txt", StandardCharsets.UTF_8));
        Tree tree = new Tree(new DefaultTreeModel(root));

        // 设置自定义单元格渲染器
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                // 调用父类方法获取默认的渲染组件
                JLabel renderer = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

                // 获取节点名称
                Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
                String text = userObject.toString();

                // 在换行符处拆分文本
                String[] lines = text.split("\n");
                String title = lines[0];

                // 组合文本，使用 HTML 标签进行换行
                StringBuilder sb = new StringBuilder("<html>");
                for (int i = 0; i < lines.length; i++) {
                    // desc: 第一行是 title
                    if (i == 0) {
                        continue;
                    }
                    sb.append(lines[i]);
                    if (i < lines.length - 1) {
                        sb.append("<br>");
                    }
                }
                sb.append("</html>");

                // 设置渲染器文本
                renderer.setText(sb.toString());

                // 设置标题
                TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
                renderer.setBorder(titledBorder);

                return renderer;
            }
        });

        // 添加树形结构到工具窗口中
        setContent(tree);
    }

}
