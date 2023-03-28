package com.github.yyy.www.startup;

import java.time.Instant;
import java.util.List;

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
    private Instant startTime;

    /**
     * 结束时间
     */
    private Instant endTime;

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

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
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
