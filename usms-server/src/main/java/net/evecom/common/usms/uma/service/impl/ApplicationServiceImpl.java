/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.service.BaseService;
import net.evecom.common.usms.core.service.TreeService;
import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.core.util.MapUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.OperationModel;
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
     * 注入ApplicationDao
     */
    @Autowired
    private ApplicationDao applicationDao;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ApplicationServiceImpl.class);


    /**
     * 注入TreeService
     */
    @Autowired
    private TreeService treeService;

    @Override
    public BaseDao<ApplicationEntity, Long> getBaseDao() {
        return applicationDao;
    }

    @Override
    public Page<ApplicationEntity> findByPage(int page, int size , SqlFilter sqlFilter) {
        return applicationDao.findByPage(page, size ,sqlFilter);
    }

    @Override
    public ApplicationEntity createApplication(ApplicationEntity applicationEntity) {
        applicationEntity.setClientId(UUID.randomUUID().toString());
        applicationEntity.setClientSecret(UUID.randomUUID().toString());
        applicationEntity = applicationDao.saveOrUpdate(applicationEntity);
        OperationModel operationModel  = new OperationModel();
        operationModel.setLabel(applicationEntity.getLabel());
        operationModel.setName(applicationEntity.getName());
        operationModel.setEnabled(applicationEntity.getEnabled());
        operationModel.setOptType(3L);
        try {
            Map<String, Object> underlineMap = MapUtil.toUnderlineStringMap(MapUtil.toMap(operationModel));
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
        return applicationDao.saveOrUpdate(application);
    }

    @Override
    public ApplicationEntity findByClientId(String clientId) {
        return applicationDao.findByClientId(clientId);
    }

    @Override
    public ApplicationEntity findByClientSecret(String clientSecret) {
        return applicationDao.findByClientSecret(clientSecret);
    }

    /**
     * 根据应用编码查询用户列表
     *
     * @param appName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByApplicationName(String appName) {
        return applicationDao.getUsersByApplicationName(appName);
    }
}
