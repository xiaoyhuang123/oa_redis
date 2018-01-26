package com.ft.backend.work.client.QueryCondition;

import com.ft.backend.work.client.common.QueryCondition;

/**
 * Created by huanghongyi on 2016/11/8.
 */
public class ChejianQueryCondition extends QueryCondition{

    private Long id;

    private String chejianName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChejianName() {
        return chejianName;
    }

    public void setChejianName(String chejianName) {
        this.chejianName = chejianName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChejianQueryCondition{");
        sb.append("chejianName='").append(chejianName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
