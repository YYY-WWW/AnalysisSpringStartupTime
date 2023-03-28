package com.github.yyy.www.toolbar;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import java.awt.*;

/**
 * @author yeyu
 * @since 2023-03-28 15:03
 */
public class SampleDialogWrapper extends DialogWrapper {

    private JPanel jPanel;

    private JTextField jTextField;

    private JLabel jLabel;

    public SampleDialogWrapper() {
        super(true); // use current window as parent
        setTitle("Spring Boot Actuator");
        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        jPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        jLabel = new JLabel("Input SpringBoot Actuator URL:");
        jLabel.setFont(new Font("Arial", Font.BOLD, 16));
        jLabel.setPreferredSize(new Dimension(300, 30));
        jTextField = new JBTextField();
        jPanel.add(jLabel, BorderLayout.NORTH);
        jPanel.add(jTextField, BorderLayout.SOUTH);
        return jPanel;
    }

    public String getTextField() {
        return jTextField.getText();
    }
}
