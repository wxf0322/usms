/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述 网页相关工具类
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:55
 */
public class WebUtil {

    private WebUtil() {
    }

    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":"
                + request.getServerPort() + path + "/";
        return basePath;
    }

}
