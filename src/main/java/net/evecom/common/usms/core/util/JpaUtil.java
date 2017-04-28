/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import net.evecom.common.usms.core.data.PageCriteria;

/**
 * 描述 Jpa工具类
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:54
 */
public class JpaUtil {

    private JpaUtil() {
    }

    /**
     * 获得单个结果
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getSingleResult(List<T> list) {
        switch (list.size()) {
            case 0:
                return null;
            case 1:
                return list.get(0);
            default:
                throw new IllegalStateException("Expected single result, got " + list.size());
        }
    }


    /**
     * Get a page of results from the specified TypedQuery
     * by using the given PageCriteria to limit the query
     * results. The PageCriteria will override any size or
     * offset already specified on the query.
     *
     * @param <T>          the type parameter
     * @param query        the query
     * @param pageCriteria the page criteria
     * @return the list
     */
    public static <T> List<T> getResultPage(TypedQuery<T> query, PageCriteria pageCriteria) {
        query.setMaxResults(pageCriteria.getPageSize());
        query.setFirstResult(pageCriteria.getPageNumber() * pageCriteria.getPageSize());

        return query.getResultList();
    }

    public static <T, I> T saveOrUpdate(I id, EntityManager entityManager, T entity) {
        T tmp = entityManager.merge(entity);
        entityManager.flush();
        return tmp;
    }
}
