/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.service;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * 描述 OAuth Service层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:50
 */
public interface OAuthService {

    /**
     * 添加 auth code
     *
     * @param authCode
     * @param loginName
     */
    void addAuthCode(String authCode, String loginName, String clientId);

    /**
     * 添加 access token
     *
     * @param accessToken
     * @param loginName
     * @param clientId
     */
    void addAccessToken(String accessToken, String loginName, String clientId);

    /**
     * 验证auth code是否有效
     *
     * @param authCode
     * @return
     */
    boolean checkAuthCode(String authCode);

    /**
     * 验证access token是否有效
     *
     * @param accessToken
     * @return
     */
    boolean checkAccessToken(String accessToken);

    /**
     * 通过 authCode 获得loginName
     *
     * @param authCode
     * @return
     */
    String getLoginNameByAuthCode(String authCode);

    /**
     * 通过accessToken获得loginName
     *
     * @param accessToken
     * @return
     */
    String getLoginNameByAccessToken(String accessToken);

    /**
     * auth code / access token 过期时间
     *
     * @return
     */
    long getExpiresIn();

    /**
     * 判断 clientId 是否存在
     *
     * @param clientId
     * @return
     */
    boolean checkClientId(String clientId);

    /**
     * 判断 clientSecret 是否存在
     *
     * @param clientSecret
     * @return
     */
    boolean checkClientSecret(String clientSecret);

    /**
     * 删除关联关系
     *
     * @param paramName
     * @param loginName
     * @param clientId
     */
    void deleteRelation(String paramName, String loginName, String clientId);

    /**
     * 获得新的AccessToken，并删除旧的AccessToken和已注册过的AuthCode
     *
     * @return
     */
    String getNewAccessToken(String loginName, String clientId, String authCode) throws OAuthSystemException;

    /**
     * 获得新的AuthCode，并删除旧的AuthCode
     *
     * @param loginName
     * @param clientId
     * @return
     * @throws OAuthSystemException
     */
    String getNewAuthCode(String loginName, String clientId) throws OAuthSystemException;

}
