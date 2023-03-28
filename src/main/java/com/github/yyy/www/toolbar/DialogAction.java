package com.github.yyy.www.toolbar;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpUtil;
import com.github.yyy.www.window.AsstWindow;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author yeyu
 * @since 2023-03-22 11:02
 */
public class DialogAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        SampleDialogWrapper dialogWrapper = new SampleDialogWrapper();
        // 如果是点击确定的话，就会返回true
        boolean ok = dialogWrapper.showAndGet();
        if (ok) {
            String url = dialogWrapper.getTextField();
            if (CharSequenceUtil.isNotBlank(url)) {
                String jsonStr = HttpUtil.createPost(url)
                        .header("Content-Type", "application/json")
                        .execute()
                        .body();

                // 从Manager 拿到对应的toolWindow
                ToolWindowManager instance = ToolWindowManager.getInstance(Objects.requireNonNull(event.getProject()));
                ToolWindow toolWindow = instance.getToolWindow("Spring 启动耗时分析");
                if (Objects.nonNull(toolWindow)) {
                    // 先移除所有内容
                    toolWindow.getContentManager().removeAllContents(true);
                    // 再重新生成
                    AsstWindow asstWindow = new AsstWindow(event.getProject(), jsonStr);
                    Content content = ContentFactory.SERVICE.getInstance().createContent(asstWindow, "", false);
                    toolWindow.setIcon(AllIcons.Actions.Find);
                    toolWindow.getContentManager().addContent(content);
                }
            }
        }

    }
}
