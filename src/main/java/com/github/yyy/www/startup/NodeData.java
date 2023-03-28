package com.github.yyy.www.startup;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * @author yeyu
 * @since 2023-03-27 15:54
 */
public class NodeData {

    private String stage;

    private String beanName;

    private Long costTime;

    @Override
    public String toString() {

        if (CharSequenceUtil.isNotBlank(beanName)) {
            return "beanName:[" + beanName + "] costTime:[" + costTime + "] ms";
        }

        return "Stage:[" + stage + "] costTime:[" + costTime + "] ms";
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }
}
