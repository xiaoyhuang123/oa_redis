package com.ft.backend.work.client.QueryCondition;

import com.ft.backend.work.client.common.QueryCondition;

/**
 * Created by huanghongyi on 2017/10/29.
 */
public class GonggaoQueryCondition extends QueryCondition{

    private String title;

    private String keyword;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GonggaoQueryCondition{");
        sb.append("title='").append(title).append('\'');
        sb.append(", keyword='").append(keyword).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
