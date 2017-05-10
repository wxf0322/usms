/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.data;

/**
 * 默认PageCriteria，已经指定检索页面和页面大小在构造函数中。
 *
 * @author Wash Wang
 */
public class DefaultPageCriteria implements PageCriteria {
    /**
     * 默认页号
     */
    private static final int DEFAULT_PAGE_NUMBER = 0;

    /**
     * 默认页面大小
     */
    private static final int DEFAULT_PAGE_SIZE = 100;

    /**
     * 页号
     */
    private int pageNumber;

    /**
     * 页面大小
     */
    private int pageSize;

    public DefaultPageCriteria() {
        this(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
    }

    public DefaultPageCriteria(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
