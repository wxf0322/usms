/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.model;

/**
 * 描述 返回结果的工具类
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/19 11:30
 */
public class ResultStatus {

    private boolean success;

    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultStatus() {
        this.success = true;
        this.msg = "";
    }

    public ResultStatus(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
