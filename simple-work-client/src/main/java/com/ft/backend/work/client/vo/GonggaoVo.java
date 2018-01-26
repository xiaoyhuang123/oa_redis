package com.ft.backend.work.client.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by huanghongyi on 2017/10/29.
 */
public class GonggaoVo implements Serializable{
    private Long id;
    private String title;
    private String keyword;
    private String content;
    private Date createTime;
    private String creater;
    private Integer mState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Integer getmState() {
        return mState;
    }

    public void setmState(Integer mState) {
        this.mState = mState;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Gonggao{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", keyword='").append(keyword).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", creater='").append(creater).append('\'');
        sb.append(", mState=").append(mState);
        sb.append('}');
        return sb.toString();
    }
}
