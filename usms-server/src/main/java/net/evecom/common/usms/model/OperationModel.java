/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.model;

import net.evecom.common.usms.entity.OperationEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * 描述 操作视图对象
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/24 12:57
 */
public class OperationModel {

    /**
     * 日志管理器
     */
    Logger logger = LoggerFactory.getLogger(OperationModel.class);
    /**
     * 序号
     */
    private Long id;
    /**
     * 父亲节点
     */
    private Long parentId;
    /**
     * 资源名称
     */
    private String label;
    /**
     * 资源编码
     */
    private String name;
    /**
     * 资源URL
     */
    private String resUrl;
    /**
     * 图标地址
     */
    private String iconPath;
    /**
     * 操作类型
     */
    private Long optType;
    /**
     * 状态
     */
    private Long enabled;

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

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Long getOptType() {
        return optType;
    }

    public void setOptType(Long optType) {
        this.optType = optType;
    }

    public Long getEnabled() {
        return enabled;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    public OperationModel() {
    }

    public OperationModel(OperationEntity operationEntity) {
        try {
            BeanUtils.copyProperties(this, operationEntity);
        } catch (IllegalAccessException e) {
           logger.error(e.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    public String toString() {
        return "OperationModel{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", label='" + label + '\'' +
                ", name='" + name + '\'' +
                ", resUrl='" + resUrl + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", optType='" + optType + '\'' +
                ", enabled='" + enabled + '\'' +
                '}';
    }
}
