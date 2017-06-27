/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.uma.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-26 15:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class RoleTest {

    /**
     * @see RoleService
     */
    @Autowired
    private RoleService roleService;
    @Test
    public void test(){
        System.out.println("-------------------------------");
        //System.out.println(roleService.listRolesByUserId(77L));
       // System.out.println(roleService.hasRole(77L,"gridmember"));
        //System.out.println(roleService.listUsersByRoleName("gridmember"));
        //System.out.println(roleService.listUsersByRoleId(111L));
        System.out.println(roleService.listSourcePrivileges(111L));
    }
}
