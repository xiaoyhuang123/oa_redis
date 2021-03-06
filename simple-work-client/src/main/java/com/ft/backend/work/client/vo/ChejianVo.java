package com.ft.backend.work.client.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by huanghongyi on 2016/11/8.
 */
public class ChejianVo implements Serializable{

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_chejian.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_chejian.workshop_name
     *
     * @mbggenerated
     */
    private String chejianName;
    private String chejianIntroduction;
    private String chejianInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_chejian.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_chejian.is_deleted
     *
     * @mbggenerated
     */
    private Integer isDeleted;

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

    public String getChejianIntroduction() {
        return chejianIntroduction;
    }

    public void setChejianIntroduction(String chejianIntroduction) {
        this.chejianIntroduction = chejianIntroduction;
    }

    public String getChejianInfo() {
        return chejianInfo;
    }

    public void setChejianInfo(String chejianInfo) {
        this.chejianInfo = chejianInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChejianVo{");
        sb.append("id=").append(id);
        sb.append(", chejianName='").append(chejianName).append('\'');
        sb.append(", chejianIntroduction='").append(chejianIntroduction).append('\'');
        sb.append(", chejianInfo='").append(chejianInfo).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append('}');
        return sb.toString();
    }
}
