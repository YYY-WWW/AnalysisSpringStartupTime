package com.github.yyy.www.window;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

public class AsstToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        AsstWindow asstWindow = new AsstWindow(project);
        Content content = ContentFactory.SERVICE.getInstance().createContent(asstWindow, "", false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.setIcon(AllIcons.Actions.Find);
    }

}

