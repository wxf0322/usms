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
     * 客户端认证失败
     */
    String INVALID_CLIENT_ID = "客户端验证失败，如：错误的client_id/client_secret。";

    /**
     * 无效的AccessToken
     */
    String INVALID_ACCESS_TOKEN = "accessToken无效或已过期。";

    /**
     * 无效的回调地址
     */
    String INVALID_REDIRECT_URI = "缺少授权成功后的回调地址。";

    /**
     * 不同的重定向地址
     */
    String DIFFERENT_REDIRECT_URI = "当前redirectUri与获取授权码时的redirectUri不同";

    /**
     * 无效的授权码
     */
    String INVALID_AUTH_CODE = "错误的授权码。";

    /**
     * 错误或无效的参数
     */
    String INVALID_PARAMS = "错误或无效的参数";

    /**
     * 用户密码错误
     */
    String INCORRECT_CREDENTIALS = "登录失败: 用户密码错误";

    /**
     * 账号不存在
     */
    String UNKNOWN_ACCOUNT = "登录失败: 用户名不存在";

    /**
     * 该账号被管理员锁定
     */
    String LOCKED_ACCOUNT = "登录失败: 该账号被管理员锁定";

    /**
     * 用户名或密码不能为空
     */
    String EMPTY_ACCOUNT_OR_PASSWORD = "登录失败: 用户名或密码不能为空";
}

