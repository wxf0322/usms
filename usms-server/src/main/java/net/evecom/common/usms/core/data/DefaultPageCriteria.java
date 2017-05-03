/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.data;

/**
 * Default implementation of PageCriteria which specifies
 * both page to be retrieved and page size in the constructor.
 * @author Colm Smyth
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
