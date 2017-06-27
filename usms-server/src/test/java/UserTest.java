/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.uma.dao.UserJpa;
import net.evecom.common.usms.uma.service.UserService;
import org.hibernate.engine.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;
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
public class UserTest {

    /**
     * @see UserService
     */
    @Autowired
    private UserService userService;
    /**
     * @see UserDao
     */
    @Autowired
    private UserDao userDao;

    @Test
    public void test(){
        System.out.println("____________________________");
       // System.out.println(userService.getUserByLoginName("ptwg08"));
       // System.out.println(userDao.listRolesByUserId(77L));
       // System.out.println(userService.listOpersByUserId(77L));
        //System.out.println(userService.listAppsByUserId(77L));
        //System.out.println(userService.listGridsByLoginName("rr6"));
        //System.out.println(userService.listInstsByUserId(22L));
       // System.out.println(userService.listTargetRoles(77L));
        System.out.println(userService.listSourceRoles(22L));
    }
}
