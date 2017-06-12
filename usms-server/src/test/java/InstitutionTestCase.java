/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.InstitutionDao;
import net.evecom.common.usms.uma.dao.RoleDao;
import net.evecom.common.usms.uma.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/6 11:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class InstitutionTestCase {

    @Autowired
    private InstitutionDao institutionDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void test() {
        List<UserEntity> list = institutionDao.findUsersByInstNames(new String[]{"yfzx", "tree1"});
        System.out.println(list);
    }

    @Test
    public void test1() {
        List<UserEntity> list = roleDao.findUsersByRoleNames(new String[]{"88", "test12"});
        System.out.println(list.size());
        System.out.println(list);
    }

    @Test
    public void test2() {
        List<UserEntity> list = userDao.findByLoginNames(null);
        System.out.println(list.size());
        System.out.println(list);
    }

}
