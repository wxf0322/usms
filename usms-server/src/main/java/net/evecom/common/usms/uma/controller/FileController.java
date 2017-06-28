/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传组件控制器
 *
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-27 19:08
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @ResponseBody
    @RequestMapping(value = "upload")
    public JSONObject upload(HttpServletRequest request) {
        Part part ;
        JSONObject jsonObject = new JSONObject();
        try {
            part = request.getPart("uploadFile");
            String root = request.getServletContext().getRealPath("/attachment");
            File file = new File(root);
            if(!file.exists()){
               file.mkdirs();
            }
            String headname = part.getHeader("content-disposition");
            String ext = headname.substring(headname.lastIndexOf("."), headname.length() - 1);
            String ranString = UUID.randomUUID().toString();
            System.out.println(root);
            String filename = root + "/" + ranString + ext;
            System.out.println(filename);
            part.write(filename);
            filename = "/attachment/"+ranString + ext;
            jsonObject.put("filename", filename);
        } catch (IOException | ServletException e) {
            jsonObject.put("fail","error");
        }
        return jsonObject;
    }
}
