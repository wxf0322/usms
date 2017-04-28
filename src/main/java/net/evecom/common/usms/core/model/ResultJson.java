/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.model;

/**
 * 描述 返回消息Json
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 9:37
 */
public class ResultJson {

    /**
     * 成功
     */
    public final static String SUCCESS = "1";

    /**
     * 失败
     */
    public final static String FAILED = "0";

    /**
     * 返回结果
     */
    private String result;

    /**
     * 返回数据
     */
    private Object data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultJson(String result, Object data) {
        this.result = result;
        this.data = data;
    }

    public ResultJson() {
    }

    @Override
    public String toString() {
        return "ResultJson{" +
                "result='" + result + '\'' +
                ", data=" + data +
                '}';
    }
}
