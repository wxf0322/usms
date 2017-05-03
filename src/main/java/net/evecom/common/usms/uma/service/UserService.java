/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * 描述 用户Service层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:51
 */
public interface UserService {
    /**
     * 创建用户
     *
     * @param user
     */
    UserEntity createUser(UserEntity user);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    UserEntity updateUser(UserEntity user);

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 修改密码
     *
     * @param id
     * @param newPassword
     */
    void changePassword(Long id, String newPassword);

    /**
     * 根据id查询单个
     *
     * @param id
     * @return
     */
    UserEntity findOne(Long id);

    /**
     * 查询全部
     *
     * @return
     */
    List<UserEntity> findAll();

    /**
     * 根据登入名查找用户
     *
     * @param loginName
     * @return
     */
    UserEntity findByLoginName(String loginName);

    /**
     * 通过 Id 查找权限
     * @param id
     * @return
     */
    List<OperationEntity> findOperationsById(Long id);

    /**
     * 通过 Id 查找应用
     * @param id
     * @return
     */
    List<ApplicationEntity> findApplicationsById(Long id);

    /**
     * 验证登录
     *
     * @param loginName  用户名
     * @param password   密码
     * @param salt       盐
     * @param encryptpwd 加密后的密码
     * @return
     */
    boolean checkUser(String loginName, String password, String salt, String encryptpwd);
}
