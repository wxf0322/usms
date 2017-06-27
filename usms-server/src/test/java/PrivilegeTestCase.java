/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.uma.service.PrivilegeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-19 16:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class PrivilegeTestCase {

    /**
     * @see PrivilegeService
     */
    @Autowired
    private PrivilegeService privilegeService;

    @Test
    public void test(){
        System.out.println("-----------------------");
       // --System.out.println(privilegeService.listPrivsByAppName("签到管理后端"));
       //System.out.println(privilegeService.listPrivsByUserId(77L));
        //System.out.println(privilegeService.hasPrivilege(77L,"22"));
       // System.out.println(privilegeService.listUsersByPrivName("22"));
       // System.out.println(privilegeService.listTargetRoles(2L));
        System.out.println(privilegeService.listSourceRoles(2L));

    }
}
