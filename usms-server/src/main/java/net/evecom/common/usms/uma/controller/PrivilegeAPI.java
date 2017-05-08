/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 10:48
 */
@RestController
@RequestMapping("/v1/openapi/")
public class PrivilegeAPI {

    public ResponseEntity getPrivilege(HttpServletRequest request) {

        return null;
    }

    public ResponseEntity getPrivileges(HttpServletRequest request) {

        return null;
    }
}
