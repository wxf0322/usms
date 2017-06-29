/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.uma.service.GridService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-26 10:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class GridTestCase {
    /**
     * @see GridService
     */
    @Autowired
    private GridService gridService;

    @Test
    public void test() {
        String code = "351011";
        System.out.println(gridService.listUsersByGridCode(code));
    }
}
