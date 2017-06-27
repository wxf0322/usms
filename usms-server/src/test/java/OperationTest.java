/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.uma.service.OperationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-26 11:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class OperationTest {

   /**
    * @see OperationService
    */
    @Autowired
    private OperationService operationService;

    @Test
    public void test(){
        System.out.println("-------------------------------------------");
        //System.out.println(operationService.listOpersByAppName("giap"));
       // System.out.println(operationService.listUsersByOperName("城乡管理系统"));
        System.out.println(operationService.hasOperation(77L,"gmsa"));
    }
}
