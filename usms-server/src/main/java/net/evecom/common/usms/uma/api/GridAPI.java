/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.api;

import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.vo.GridVO;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.GridService;
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

    /**
     * 注入OAuthService
     */
    @Autowired
    private OAuthService oAuthService;

    /**
     * 注入UserService
     */
    @Autowired
    private UserService userService;

    /**
     * 注入GridService
     */
    @Autowired
    private GridService gridService;

    /**
     * 根据 access_token 返回网格信息
     *
     * @param request
     * @return
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @RequestMapping(value = "/grids", produces = "application/json; charset=UTF-8")
    public ResponseEntity getGrids(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();
        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获取网格数据
        List<GridEntity> grids = userService.listGridsByLoginName(loginName);
        JSONArray gridJsonArr = new JSONArray();
        // 遍历网格数据
        for (GridEntity grid : grids) {
            GridVO gridVO = new GridVO(grid);
            JSONObject gridJson = JSONObject.fromObject(gridVO);
            gridJsonArr.add(gridJson);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("grids", gridJsonArr);
        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

    /**
     * 返回所有网格的信息
     *
     * @return
     */
    @RequestMapping(value = "/grids/all", produces = "application/json; charset=UTF-8")
    public ResponseEntity findAll() {
        List<GridEntity> grids = gridService.findAll();
        JSONArray gridJsonArr = new JSONArray();
        for (GridEntity grid : grids) {
            GridVO gridVO = new GridVO(grid);
            JSONObject gridJson = JSONObject.fromObject(gridVO);
            gridJsonArr.add(gridJson);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("grids", gridJsonArr);
        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

}


