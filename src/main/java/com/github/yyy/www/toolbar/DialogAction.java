package com.github.yyy.www.toolbar;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author yeyu
 * @since 2023-03-22 11:02
 */
public class DialogAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        JBPopupFactory instance = JBPopupFactory.getInstance();//创建JBPopupFactory实例

        JPanel mainPanel = buildDialogPanel(event);

        // 创建弹出窗口并设置大小、位置等属性
        JBPopup popup = instance.createComponentPopupBuilder(mainPanel, null)
                .setTitle("弹窗")
                .setMovable(true)
                .setResizable(true)
                .setNormalWindowLevel(false)
                .setRequestFocus(true)
                .setCancelOnClickOutside(true)
                .setCancelOnOtherWindowOpen(true)
                .setCancelKeyEnabled(true)
                .createPopup();

        // 显示弹窗
        popup.showInFocusCenter();
    }

    private JPanel buildDialogPanel(AnActionEvent event) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3));

        JLabel label = new JLabel("Choose an option:");
        topPanel.add(label);
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton button1 = new JRadioButton("A");
        JRadioButton button2 = new JRadioButton("B");
        buttonGroup.add(button1);
        buttonGroup.add(button2);
        topPanel.add(button1);
        topPanel.add(button2);

        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(1, 2));
        JLabel label1 = new JLabel("Enter JSON Input:");
        JTextArea textArea = new JBTextArea();
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JBScrollPane(textArea);

        midPanel.add(label1);
        midPanel.add(scrollPane);
        label1.setVisible(false);
        scrollPane.setVisible(false);

        button1.addActionListener(e -> {
            label1.setVisible(true);
            scrollPane.setVisible(true);
        });

        button2.addActionListener(e -> {
            label1.setVisible(false);
            scrollPane.setVisible(false);
        });


        JPanel bottomPanel = new JPanel(new GridBagLayout());
        JButton button = new JButton("Start");
        // Start按钮最终用于启动
        button.addActionListener(new StartButtonListener(textArea, event));
        // 设置 GridBagConstraints 对象
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;

        bottomPanel.add(button, c); // 将按钮添加到面板中


        mainPanel.add(topPanel);
        mainPanel.add(midPanel);
        mainPanel.add(bottomPanel);

        return mainPanel;
    }
}
