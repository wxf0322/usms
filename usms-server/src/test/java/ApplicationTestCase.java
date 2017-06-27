/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.uma.service.ApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/26 上午9:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class ApplicationTestCase {

    /**
     * @see ApplicationService
     */
    @Autowired
    private ApplicationService applicationService;

    @Test
    public void test() {
    }

}
