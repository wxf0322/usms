/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.service.TreeService;
import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.core.util.MapUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.constant.OperationTypeEnum;
import net.evecom.common.usms.vo.OperationVO;
import net.evecom.common.usms.uma.dao.ApplicationDao;
import net.evecom.common.usms.uma.service.ApplicationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 描述 应用Service层实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:37
 */
@Transactional
@Service
public class ApplicationServiceImpl extends BaseServiceImpl<ApplicationEntity, Long>
        implements ApplicationService {

    /**
     * 日志管理器
     */
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ApplicationServiceImpl.class);

    /**
     * @see ApplicationDao
     */
    @Autowired
    private ApplicationDao applicationDao;

    /**
     * @see TreeService
     */
    @Autowired
    private TreeService treeService;

    @Override
    public Page<ApplicationEntity> listAppsByPage(int page, int size , SqlFilter sqlFilter) {
        return applicationDao.listAppsByPage(page, size ,sqlFilter);
    }

    @Override
    public ApplicationEntity createApplication(ApplicationEntity applicationEntity) {
        applicationEntity.setClientId(UUID.randomUUID().toString());
        applicationEntity.setClientSecret(UUID.randomUUID().toString());
        applicationEntity = applicationDao.save(applicationEntity);
        OperationVO operationVO = new OperationVO();
        operationVO.setLabel(applicationEntity.getLabel());
        operationVO.setName(applicationEntity.getName());
        operationVO.setEnabled(applicationEntity.getEnabled());
        operationVO.setOptType(OperationTypeEnum.SYSTEM.getValue());
        try {
            Map<String, Object> underlineMap = MapUtil.toUnderlineStringMap(MapUtil.toMap(operationVO));
            underlineMap.put("APPLICATION_ID", applicationEntity.getId());
            treeService.saveOrUpdateTreeData(null, 0L, underlineMap,
                    "usms_operations", "usms_operations_s");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            return applicationEntity;
        }
        return applicationEntity;
    }

    @Override
    public ApplicationEntity updateApplication(ApplicationEntity application) {
        return applicationDao.save(application);
    }

    @Override
    public ApplicationEntity getAppByClientId(String clientId) {
        return applicationDao.findFirstByClientId(clientId);
    }

    @Override
    public ApplicationEntity getAppByClientSecret(String clientSecret) {
        return applicationDao.findFirstByClientSecret(clientSecret);
    }

    @Override
    public ApplicationEntity getApplication(String clientId, String clientSecret) {
        return applicationDao.findFirstByClientIdAndClientSecret(clientId, clientSecret);
    }

    @Override
    public List<ApplicationEntity> listAppsByUserId(Long userId) {
        return applicationDao.listAppsByUserId(userId);
    }

}
