/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * @created 2017/5/8 17:25
 */
@RestController
@RequestMapping("/v1/openapi/")
public class GridAPI {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/grids", produces = "application/json; charset=UTF-8")
    public ResponseEntity getGrids(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();
        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获取网格数据
        List<GridEntity> grids = userService.findGridsByLoginName(loginName);
        JSONArray gridJsonArr = new JSONArray();
        // 遍历网格数据
        for (GridEntity grid : grids) {
            JSONObject gridJson = getGridJSONObject(grid);
            gridJsonArr.add(gridJson);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("grids", gridJsonArr);
        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

    private JSONObject getGridJSONObject(GridEntity grid) {
        JSONObject gridJson = new JSONObject();
        gridJson.put("id", grid.getId());
        gridJson.put("code", grid.getCode());
        gridJson.put("lvl", grid.getLvl());
        gridJson.put("name", grid.getName());
        gridJson.put("grid_type", grid.getGridType());
        gridJson.put("description", grid.getDescripiton());
        gridJson.put("duty_phone", grid.getDutyPhone());
        gridJson.put("photo_url", grid.getPhotoUrl());
        gridJson.put("member_num", grid.getMemberNum());
        gridJson.put("householde_num", grid.getHouseholdeNum());
        gridJson.put("area", grid.getArea());
        gridJson.put("parent_id", grid.getParentId());
        gridJson.put("admin_division_code", grid.getAdminDivisionCode());
        gridJson.put("admin_division", grid.getAdminDivision());
        gridJson.put("manual_sn", grid.getManualSn());
        gridJson.put("geo_outline_id", grid.getGeoOutlineId());
        return gridJson;
    }

}


