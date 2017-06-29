/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.uma.dao.GridDao;
import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.uma.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-26 16:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class UserTestCase {

    /**
     * @see UserService
     */
    @Autowired
    private UserService userService;
    /**
     * @see UserDao
     */
    @Autowired
    private GridDao gridDao;

    @Test
    public void test(){
        System.out.println(gridDao.listGridsByLoginName("admin"));
    }
}
