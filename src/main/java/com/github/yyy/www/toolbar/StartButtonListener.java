package com.github.yyy.www.toolbar;

import cn.hutool.core.text.CharSequenceUtil;
import com.github.yyy.www.window.AsstWindow;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * @author yeyu
 * @since 2023-03-22 15:04
 * 这个
 */
public class StartButtonListener implements ActionListener {

    private final JTextArea textArea;

    private final AnActionEvent event;

    public StartButtonListener(JTextArea textArea, AnActionEvent event) {
        this.textArea = textArea;
        this.event = event;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (CharSequenceUtil.isNotBlank(textArea.getText())) {
            // 从Manager 拿到对应的toolWindow
            ToolWindowManager instance = ToolWindowManager.getInstance(Objects.requireNonNull(event.getProject()));
            ToolWindow toolWindow = instance.getToolWindow("Spring 启动耗时分析");
            if (Objects.nonNull(toolWindow)) {
                // 先移除所有内容
                toolWindow.getContentManager().removeAllContents(true);
                // 再重新生成
                AsstWindow asstWindow = new AsstWindow(event.getProject(), textArea.getText());
                Content content = ContentFactory.SERVICE.getInstance().createContent(asstWindow, "", false);
                toolWindow.setIcon(AllIcons.Actions.Find);
                toolWindow.getContentManager().addContent(content);
            }
        }
    }
}
