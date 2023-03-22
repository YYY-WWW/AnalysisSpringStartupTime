package com.github.yyy.www.startup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * spring 启动耗时数据实体类
 * 整体结构为：data -> timeline -> events -> step
 * 结构与 spring 原生提供一致
 */
public class StartupData {

    /**
     * spring boot 版本，非 spring boot 项目为空
     */
    private String springBootVersion;

    /**
     * 时间线
     */
    private Timeline timeline;

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public void setSpringBootVersion(String springBootVersion) {
        this.springBootVersion = springBootVersion;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    /**
     * 将原始启动耗时数据转为树形结构
     */
    public List<StartupEvent> convertTree() {

        // 用 Map 存储每个节点的 ID 和对应的 event
        StartupEvent[] events = this.getTimeline().getEvents();
        Map<Integer, StartupEvent> nodeMap = Arrays.stream(events)
                .collect(Collectors.toMap(e -> e.getStartupStep().getId(), Function.identity()));

        // 构建树形结构
        List<StartupEvent> roots = new ArrayList<>();
        for (StartupEvent event : events) {
            StartupStep startupStep = event.getStartupStep();
            int parentId = startupStep.getParentId();
            if (parentId == 0) {
                // 根节点
                roots.add(event);
            } else {
                // 非根节点
                StartupEvent parentNode = nodeMap.get(parentId);
                List<StartupEvent> children = parentNode.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parentNode.setChildren(children);
                }
                children.add(event);
            }
        }

        return roots;
    }

}