/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.uma.dao.ApplicationDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述 管辖区域管理相关Dao层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/24 下午5:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class ApplicationTestCase {

    @Autowired
    private ApplicationDao applicationDao;

    @Test
    public void test() {
        ApplicationEntity application = applicationDao.getAppByClientId("6433ada9-de68-40ba-89a0-7aa8ee9128df");
        System.out.printf(application.toString());
    }

}
