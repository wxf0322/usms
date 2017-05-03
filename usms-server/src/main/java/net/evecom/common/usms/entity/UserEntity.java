/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/26 11:31
 */
@Entity
@Table(name = "USMS_USERS")
@NamedQueries({
        @NamedQuery(name = UserEntity.QUERY_ALL, query = "select u from UserEntity u"),
        @NamedQuery(name = UserEntity.QUERY_BY_LOGIN_NAME,
                query = "select u from UserEntity u where u.loginName = :"
                        + UserEntity.PARAM_LOGIN_NAME),
})
public class UserEntity {

    /**
     * 查找全部
     */
    public static final String QUERY_ALL = "UserEntity.findAll";

    /**
     * 根据登入名查找
     */
    public static final String QUERY_BY_LOGIN_NAME = "UserEntity.getByLoginName";

    /**
     * loginName参数
     */
    public static final String PARAM_LOGIN_NAME = "loginName";
    /**
     * 用户ID
     */
    private long id;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 加密盐值
     */
    private String salt;
    /**
     * 姓名
     */
    private String name;
    /**
     * 可用状态(1;正常 0:冻结)
     */
    private long enabled;
    /**
     * 关联员工ID
     */
    private Long staffId;
    /**
     * 备注说明
     */
    private String ramarks;
    /**
     * 最后修改密码日期
     */
    private Date pwdModified;
    /**
     * 更新时间
     */
    private Date lastModified;
    /**
     * 更新人ID
     */
    private Long modifierId;
    /**
     * 更新人
     */
    private String modifier;
    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 创建人ID
     */
    private Long creatorId;
    /**
     * 创建人
     */
    private String creator;

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "LOGIN_NAME")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "SALT")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ENABLED")
    public long getEnabled() {
        return enabled;
    }

    public void setEnabled(long enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "STAFF_ID")
    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    @Basic
    @Column(name = "RAMARKS")
    public String getRamarks() {
        return ramarks;
    }

    public void setRamarks(String ramarks) {
        this.ramarks = ramarks;
    }

    @Basic
    @Column(name = "PWD_MODIFIED")
    public Date getPwdModified() {
        return pwdModified;
    }

    public void setPwdModified(Date pwdModified) {
        this.pwdModified = pwdModified;
    }

    @Basic
    @Column(name = "LAST_MODIFIED")
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Basic
    @Column(name = "MODIFIER_ID")
    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    @Basic
    @Column(name = "MODIFIER")
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "TIME_CREATED")
    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "CREATOR_ID")
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "CREATOR")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Transient
    public String getCredentialsSalt() {
        return loginName + salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (enabled != that.enabled) return false;
        if (loginName != null ? !loginName.equals(that.loginName) : that.loginName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (salt != null ? !salt.equals(that.salt) : that.salt != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (staffId != null ? !staffId.equals(that.staffId) : that.staffId != null) return false;
        if (ramarks != null ? !ramarks.equals(that.ramarks) : that.ramarks != null) return false;
        if (pwdModified != null ? !pwdModified.equals(that.pwdModified) : that.pwdModified != null) return false;
        if (lastModified != null ? !lastModified.equals(that.lastModified) : that.lastModified != null) return false;
        if (modifierId != null ? !modifierId.equals(that.modifierId) : that.modifierId != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (timeCreated != null ? !timeCreated.equals(that.timeCreated) : that.timeCreated != null) return false;
        if (creatorId != null ? !creatorId.equals(that.creatorId) : that.creatorId != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (enabled ^ (enabled >>> 32));
        result = 31 * result + (staffId != null ? staffId.hashCode() : 0);
        result = 31 * result + (ramarks != null ? ramarks.hashCode() : 0);
        result = 31 * result + (pwdModified != null ? pwdModified.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + (modifierId != null ? modifierId.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (timeCreated != null ? timeCreated.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        return result;
    }
}
