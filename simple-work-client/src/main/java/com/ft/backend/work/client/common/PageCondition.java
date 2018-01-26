package com.ft.backend.work.client.common;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2016/11/8.
 */
public class PageCondition implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 当前页序号，从1开始
     */
    private Integer pageIndex = 1;
    /**
     * 每页结果数量
     */
    private Integer pageSize = 10;

    private Integer offset=0;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        if(pageIndex!=null && pageSize!=null) {
            this.offset = (pageIndex - 1) * pageSize;
        }
        else{
            offset=null;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        if(pageIndex!=null && pageSize!=null) {
            this.offset = (pageIndex - 1) * pageSize;
        }
        else{
            offset=null;
        }
    }

    /**
     * 计算mysql中的偏移量
     */
    public int getOffset() {
        return (pageIndex - 1) * pageSize;
    }

    public void setNull(){
        this.pageSize=null;
        this.pageIndex=null;
        this.offset=null;
    }
}
