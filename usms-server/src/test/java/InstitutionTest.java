/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.uma.service.InstitutionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-26 10:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class InstitutionTest {

    /**
     * @see InstitutionService
     */
    @Autowired
    private InstitutionService institutionService;

    @Test
    public void test(){
        System.out.println("--------------------------------------------");
        //System.out.println(institutionService.listInstsByLoginName("admin"));
        //System.out.println(institutionService.getInstByInstName("平潭综合实验区文学艺术界联合会"));
        //System.out.println(institutionService.listUsersByInstName("平潭综合实验区文学艺术界联合会"));
        System.out.println(institutionService.listInstsByType(1L));
    }
}
