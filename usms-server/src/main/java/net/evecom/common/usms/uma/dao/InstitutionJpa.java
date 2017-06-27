/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-26 10:43
 */
public interface InstitutionJpa extends JpaRepository<InstitutionEntity, Long> {
    List<InstitutionEntity> findByName(String instName);
    List<InstitutionEntity> findByTypeAndEnabled(Long type, Long enabled);
}
