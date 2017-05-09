/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.uma.service.PasswordHelper;
import net.evecom.common.usms.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 用户Service实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:52
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    /**
     * 注入UserDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 注入密码工具类
     */
    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public UserEntity createUser(UserEntity user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        return userDao.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param newPassword
     */
    public void changePassword(Long id, String newPassword) {
        UserEntity user = userDao.findOne(id);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    @Override
    public UserEntity findOne(Long id) {
        return userDao.findOne(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    /**
     * 根据用户名查找用户
     *
     * @param loginName
     * @return
     */
    @Override
    public UserEntity findByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    /**
     * 通过id查找用户权限
     *
     * @param id
     * @return
     */
    @Override
    public List<OperationEntity> findOperationsById(Long id) {
        return userDao.findOperationsById(id);
    }

    @Override
    public List<ApplicationEntity> findApplicationsById(Long id) {
        return userDao.findApplicationsById(id);
    }

    /**
     * 验证登录
     *
     * @param loginName  用户名
     * @param password   密码
     * @param salt       盐
     * @param encryptpwd 加密后的密码
     * @return
     */
    @Override
    public boolean checkUser(String loginName, String password, String salt, String encryptpwd) {
        String pwd = passwordHelper.encryptPassword(loginName, password, salt);
        return pwd.equals(encryptpwd);
    }

    /**
     * 根据登入名获取网格的数据
     * @param loginName
     * @return
     */
    @Override
    public List<GridEntity> findGridsByLoginName(String loginName) {
        return userDao.findGridsByLoginName(loginName);
    }

}
