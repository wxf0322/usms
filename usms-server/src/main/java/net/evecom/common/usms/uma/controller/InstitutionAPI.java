/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ErrorStatus;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.oauth2.Constants;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.InstitutionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 10:48
 */
@RestController
@RequestMapping("/v1/openapi/")
public class InstitutionAPI {

    /**
     * 注入OAuthService
     */
    @Autowired
    private OAuthService oAuthService;

    /**
     * 注入InstitutionService
     */
    @Autowired
    private InstitutionService institutionService;

    /**
     * 组织机构信息，根据组织机构编码查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/institution", produces = "application/json; charset=UTF-8")
    public ResponseEntity getInstitution(HttpServletRequest request) {
        String instName = request.getParameter("institution");
        if (StringUtils.isEmpty(instName)) {
            ErrorStatus errorStatus = new ErrorStatus
                    .Builder(ErrorStatus.INVALID_PARAMS, Constants.INVALID_PARAMS)
                    .buildJSONMessage();
            return new ResponseEntity(errorStatus.getBody(), HttpStatus.BAD_REQUEST);
        }
        InstitutionEntity inst = institutionService.getByName(instName);
        JSONObject instJson = getInstJSONObject(inst);
        return new ResponseEntity(instJson.toString(), HttpStatus.OK);
    }

    /**
     * 组织机构列表，根据access_token查询
     *
     * @param request
     * @return
     * @throws OAuthSystemException
     * @throws OAuthProblemException
     */
    @RequestMapping(value = "/institutions", produces = "application/json; charset=UTF-8")
    public ResponseEntity getInstitutions(HttpServletRequest request)
            throws OAuthSystemException, OAuthProblemException {
        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();
        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获得机构信息
        List<InstitutionEntity> institutions = institutionService.findInstByLoginName(loginName);
        // 返回数组
        JSONArray instJsonArr = new JSONArray();
        for (InstitutionEntity inst : institutions) {
            JSONObject instJson = getInstJSONObject(inst);
            instJsonArr.add(instJson);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("institutions", instJsonArr);
        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

    /**
     * @param inst
     * @return
     */
    private JSONObject getInstJSONObject(InstitutionEntity inst) {
        JSONObject instJson = new JSONObject();
        if (inst != null) {
            instJson.put("id", inst.getId());
            instJson.put("label", inst.getLabel());
            instJson.put("name", inst.getName());
            instJson.put("tree_level", inst.getTreeLevel());
            instJson.put("parent_id", inst.getParentId());
            instJson.put("type", inst.getType());
            instJson.put("manual_sn", inst.getManualSn());
            instJson.put("admin_division_code", inst.getAdminDivisionCode());
            instJson.put("admin_division", inst.getAdminDivision());
            instJson.put("social_credit_unicode", inst.getSocialCreditUnicode());
            instJson.put("org_code", inst.getOrgCode());
        }
        return instJson;
    }

}
