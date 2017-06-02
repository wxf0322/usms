/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.model;

/**
 * 树形组件
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017-5-22 11:27
 */
public class TreeDataModel {

    /**
     * 序号
     */
    Long id;
    /**
     * 父节点
     */
    Long parentId;
    /**
     * 名称
     */
    String label;
    /**
     * 编号
     */
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TreeNodeModel{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", label='" + label + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

