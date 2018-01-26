package com.ft.backend.work.client.common;

import java.util.List;

/**
 * Created by huanghongyi on 2016/11/8.
 */
public class QueryResult<T> {
    private Integer totalCount;
    private List<T> resultList;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QueryResult [totalCount=").append(totalCount)
                .append(", resultList=").append(resultList).append("]");
        return builder.toString();
    }
}
