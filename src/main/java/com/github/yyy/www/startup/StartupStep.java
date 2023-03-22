package com.github.yyy.www.startup;

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
    private String[] tags;

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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

}
