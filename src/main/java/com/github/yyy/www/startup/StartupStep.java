package com.github.yyy.www.startup;

import java.util.List;

/**
 * 启动步骤实体类
 */
public class StartupStep {

    /**
     * ID，唯一标识
     */
    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级ID
     */
    private int parentId;

    /**
     * 标签
     */
    private List<StartupTag> startupTags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<StartupTag> getTags() {
        return startupTags;
    }

    public void setTags(List<StartupTag> startupTags) {
        this.startupTags = startupTags;
    }

}
