/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2;

/**
 * 描述 错误常量类
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:43
 */
public interface Constants {
    /**
     * 资源服务器名
     */
    String RESOURCE_SERVER_NAME = "oauth-server";

    /**
     * 客户端认证失败
     */
    String INVALID_CLIENT_ID = "客户端验证失败，如：错误的client_id。";

    /**
     * 客户端认证失败
     */
    String INVALID_CLIENT_SECRET = "客户端验证失败，错误的client_secret。";


    /**
     * 无效的AccessToken
     */
    String INVALID_ACCESS_TOKEN = "accessToken无效或已过期。";

    /**
     * 无效的回调地址
     */
    String INVALID_REDIRECT_URI = "缺少授权成功后的回调地址。";

    /**
     * 无效的授权码
     */
    String INVALID_AUTH_CODE = "错误的授权码。";
}
