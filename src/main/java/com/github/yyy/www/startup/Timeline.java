package com.github.yyy.www.startup;

import java.time.ZonedDateTime;

/**
 * 启动耗时时间线实体类
 */
public class Timeline {

    /**
     * 项目启动时间
     */
    private ZonedDateTime startTime;

    /**
     * 启动耗时事件
     */
    private StartupEvent[] events;

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public StartupEvent[] getEvents() {
        return events;
    }

    public void setEvents(StartupEvent[] events) {
        this.events = events;
    }

}
