/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 9:58
 */
@Controller
@RequestMapping("/v1/openapi/users")
public class UsersAPI {

    /**
     * 获取用户列表
     *
     * @param request
     * @return
     */
    @RequestMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity getUsers(HttpServletRequest request) {

        return null;
    }

}

