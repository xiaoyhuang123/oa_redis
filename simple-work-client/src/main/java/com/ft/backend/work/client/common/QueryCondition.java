package com.ft.backend.work.client.common;

/**
 * Created by huanghongyi on 2016/11/8.
 */
public class QueryCondition extends PageCondition{

    /**
     * 排序字段，必须与表字段一致
     */
    private String orderColumn;
    /**
     * 排序类型，DESC / ASC
     */
    private String orderType;

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
