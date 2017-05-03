/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.data;

/**
 * Interface which defines page criteria for use in
 * a repository operation.
 *
 * @author Colm Smyth
 */
public interface PageCriteria {

    int getPageNumber();

    int getPageSize();
}
