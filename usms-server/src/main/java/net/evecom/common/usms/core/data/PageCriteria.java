/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.data;

/**
 * 定义存储库操作中，页面大小与页号的接口。
 *
 * @author Wash Wang
 */
public interface PageCriteria {

    int getPageNumber();

    int getPageSize();
}
