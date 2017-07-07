/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.custom.InstitutionDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 14:25
 */
@Repository
public interface InstitutionDao extends JpaRepository<InstitutionEntity, Long>, InstitutionDaoCustom {

    /**
     * 根据登陆名获取机构列表
     *
     * @param loginName
     * @return
     */
    @Query(value = "select * from usms_institutions i where i.id in " +
            " (select ui.institution_id from usms_user_institution ui " +
            " where ui.user_id in " +
            " (select u.id from usms_users u " +
            " where u.login_name = ?1))", nativeQuery = true)
    List<InstitutionEntity> listInstsByLoginName(String loginName);

    @Query(value = "select * from usms_institutions i where i.id in " +
            "(select ui.institution_id from usms_user_institution ui where ui.user_id = ?1) ",
            nativeQuery = true)
    List<InstitutionEntity> listInstsByUserId(Long userId);

    /**
     *
     * @param instName
     * @return
     */
    InstitutionEntity findFirstByName(String instName);

}
