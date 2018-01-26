package com.ft.backend.work.client.vo;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2017/11/25.
 */
public class PositionTitleVo implements Serializable{


    private Long id;

    private String positionTitleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionTitleName() {
        return positionTitleName;
    }

    public void setPositionTitleName(String positionTitleName) {
        this.positionTitleName = positionTitleName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PositionTitleVo{");
        sb.append("id=").append(id);
        sb.append(", positionTitleName='").append(positionTitleName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
