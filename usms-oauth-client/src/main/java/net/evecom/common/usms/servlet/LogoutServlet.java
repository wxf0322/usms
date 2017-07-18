/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.servlet;

import net.evecom.common.usms.vo.ResultStatus;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 描述 登出servlet
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/7/17 下午4:50
 */
public class LogoutServlet extends HttpServlet {

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String logoutUrl = this.getInitParameter("logoutUrl");

        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");

        URL url = new URL(logoutUrl + "?access_token=" + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.disconnect();

        PrintWriter writer = response.getWriter();
        if (HttpServletResponse.SC_OK == conn.getResponseCode()) {
            session.invalidate();

            ResultStatus resultStatus = new ResultStatus(true, "");
            JSONObject resultJson = JSONObject.fromObject(resultStatus);
            writer.write(resultJson.toString());
        } else {
            ResultStatus resultStatus = new ResultStatus(false, "");
            JSONObject resultJson = JSONObject.fromObject(resultStatus);
            writer.write(resultJson.toString());
        }
    }

}

