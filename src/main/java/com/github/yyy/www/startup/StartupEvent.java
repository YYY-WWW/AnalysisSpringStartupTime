package com.github.yyy.www.startup;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 启动耗时事件
 */
public class StartupEvent {

    /**
     * 耗时
     * eg：PT0.004235176S
     */
    private String duration;

    /**
     * 开始时间
     */
    private ZonedDateTime startTime;

    /**
     * 结束时间
     */
    private ZonedDateTime endTime;

    /**
     * 启动步骤
     */
    private StartupStep startupStep;

    /**
     * 下级
     */
    private List<StartupEvent> children;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public StartupStep getStartupStep() {
        return startupStep;
    }

    public void setStartupStep(StartupStep startupStep) {
        this.startupStep = startupStep;
    }

    public List<StartupEvent> getChildren() {
        return children;
    }

    public void setChildren(List<StartupEvent> children) {
        this.children = children;
    }

}
