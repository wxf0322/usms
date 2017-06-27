/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.core.service.BaseService;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.vo.UserVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 描述 用户Service层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:51
 */
public interface UserService extends BaseService<UserEntity, Long> {
    /**
     * 创建用户
     *
     * @param userVO
     */
    UserEntity createUser(UserVO userVO);

    /**
     * 更新用户
     *
     * @param userVO
     * @return
     */
    UserEntity updateUser(UserVO userVO);


    /**
     * 修改密码
     *
     * @param id
     * @param newPassword
     */
    void changePassword(Long id, String newPassword);

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param institutionId
     * @return
     */
    Page<UserVO> listUsersByPage(int page, int size, Long institutionId, SqlFilter sqlFilter);

    /**
     * 根据登入名查找用户
     *
     * @param loginName
     * @return
     */
    UserEntity getUserByLoginName(String loginName);

    /**
     * 根据登入名列表查找用户
     *
     * @param loginNames
     * @return
     */
    List<UserEntity> listUsersByLoginNames(String[] loginNames);

    /**
     * 通过 userId 查找操作
     *
     * @param userId
     * @return
     */
    List<OperationEntity> listOpersByUserId(Long userId);

    /**
     * 通过 Id 查找应用
     *
     * @param id
     * @return
     */
    List<ApplicationEntity> listAppsByUserId(Long userId);

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

    /**
     * 根据登入名获取网格数据
     *
     * @param loginName
     * @return
     */
    List<GridEntity> listGridsByLoginName(String loginName);

    /**
     * 更新用户与组织机构的关系
     *
     * @param userId
     * @param institutionIds
     */
    void updateInstitutions(Long userId, String[] institutionIds);

    /**
     * 获取用户相关的组织机构列表
     *
     * @param userId
     * @return
     */
    List<InstitutionEntity> listInstsByUserId(Long userId);

    /**
     * 获得当前用户已选中的用户
     *
     * @param userId
     * @return
     */
    List<RoleEntity> listTargetRoles(Long userId);

    /**
     * 获得当前用户未选中的用户
     *
     * @param userId
     * @return
     */
    List<RoleEntity> listSourceRoles(Long userId);

    /**
     * 更新用户与角色之间的关系
     *
     * @param userId
     * @param roleIds
     */
    void updateRoles(Long userId, String[] roleIds);

}
