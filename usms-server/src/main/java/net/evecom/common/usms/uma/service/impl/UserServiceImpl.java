/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.model.UserModel;
import net.evecom.common.usms.uma.dao.StaffDao;
import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.uma.service.PasswordHelper;
import net.evecom.common.usms.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述 用户Service实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:52
 */
@Transactional
@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, Long>
        implements UserService {

    /**
     * 注入UserDao
     */
    @Autowired
    private UserDao userDao;

    @Autowired
    private StaffDao staffDao;

    /**
     * 注入密码工具类
     */
    @Autowired
    private PasswordHelper passwordHelper;


    @Override
    public BaseDao<UserEntity, Long> getBaseDao() {
        return userDao;
    }

    @Override
    public UserEntity createUser(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity = userModel.mergeUserEntity(userEntity);
        userEntity.setPassword("123456");
        userEntity.setTimeCreated(new Date());
        //加密密码
        passwordHelper.encryptPassword(userEntity);
        return userDao.saveOrUpdate(userEntity);
    }

    @Override
    public UserEntity updateUser(UserModel userModel) {
        UserEntity found = userDao.findOne(userModel.getId());
        found = userModel.mergeUserEntity(found);
        return userDao.saveOrUpdate(found);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param newPassword
     */
    @Override
    public void changePassword(Long id, String newPassword) {
        UserEntity user = userDao.findOne(id);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.saveOrUpdate(user);
    }

    @Override
    public Page<UserModel> findModelsByPage(int page, int size) {
        return userDao.findModelsByPage(page, size);
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

    @Override
    public void updateInstitutions(Long userId, String[] institutionIds) {
        userDao.updateInstitutions(userId,institutionIds);
    }

    @Override
    public List<InstitutionEntity> findInstByUserId(Long userId) {
        return userDao.findInstByUserId(userId);
    }

}
