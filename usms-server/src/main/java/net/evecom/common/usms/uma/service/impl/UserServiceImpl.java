/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.vo.UserVO;
import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.uma.service.PasswordHelper;
import net.evecom.common.usms.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
public class UserServiceImpl extends BaseServiceImpl<UserEntity, Long>
        implements UserService {

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
    public BaseDao<UserEntity, Long> getBaseDao() {
        return userDao;
    }

    /**
     * @param userVO
     * @return
     */
    @Override
    public UserEntity createUser(UserVO userVO) {
        UserEntity userEntity = new UserEntity();
        userEntity = userVO.mergeUserEntity(userEntity);
        userEntity.setPassword("123456");
        userEntity.setTimeCreated(new Date());
        //加密密码
        passwordHelper.encryptPassword(userEntity);
        return userDao.saveOrUpdate(userEntity);
    }

    /**
     * @param userVO
     * @return
     */
    @Override
    public UserEntity updateUser(UserVO userVO) {
        UserEntity found = userDao.findOne(userVO.getId());
        found = userVO.mergeUserEntity(found);
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

    /**
     * @param page
     * @param size
     * @param sqlFilter
     * @return
     */
    @Override
    public Page<UserVO> listUsersByPage(int page, int size, Long institutionId, SqlFilter sqlFilter) {
        return userDao.listUsersByPage(page, size, institutionId, sqlFilter);
    }

    /**
     * 根据用户名查找用户
     *
     * @param loginName
     * @return
     */
    @Override
    public UserEntity getUserByLoginName(String loginName) {
        return userDao.getUserByLoginName(loginName);
    }

    @Override
    public List<UserEntity> listUsersByLoginNames(String[] loginNames) {
        return userDao.listUsersByLoginNames(loginNames);
    }

    /**
     * 通过id查找用户权限
     *
     * @param userId
     * @return
     */
    @Override
    public List<OperationEntity> listOpersByUserId(Long userId) {
        return userDao.listOpersByUserId(userId);
    }

    @Override
    public List<ApplicationEntity> listAppsByUserId(Long userId) {
        return userDao.listAppsByUserId(userId);
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
     *
     * @param loginName
     * @return
     */
    @Override
    public List<GridEntity> listGridsByLoginName(String loginName) {
        return userDao.listGridsByLoginName(loginName);
    }


    /**
     * @param userId
     * @param institutionIds
     */
    @Override
    public void updateInstitutions(Long userId, String[] institutionIds) {
        userDao.updateInstitutions(userId, institutionIds);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<InstitutionEntity> listInstsByUserId(Long userId) {
        return userDao.listInstsByUserId(userId);
    }

    /**
     * @param userId
     * @param institutionId
     */
    @Override
    public void createUserInstitution(Long userId, Long institutionId) {
        userDao.createUserInstitution(userId, institutionId);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<RoleEntity> listTargetRoles(Long userId) {
        return userDao.listTargetRoles(userId);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<RoleEntity> listSourceRoles(Long userId) {
        return userDao.listSourceRoles(userId);
    }

    /**
     * @param userId
     * @param roleIds
     */
    @Override
    public void updateRoles(Long userId, String[] roleIds) {
        userDao.updateRoles(userId, roleIds);
    }

}
