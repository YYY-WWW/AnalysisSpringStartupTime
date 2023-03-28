package com.github.yyy.www.startup;

import java.time.Instant;
import java.util.List;

/**
 * 启动耗时时间线实体类
 */
public class Timeline {

    /**
     * 项目启动时间
     */
    private Instant startTime;

    /**
     * 启动耗时事件
     */
    private List<StartupEvent> events;

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public List<StartupEvent> getEvents() {
        return events;
    }

    public void setEvents(List<StartupEvent> events) {
        this.events = events;
    }

}
