package com.ft.backend.work.client.vo;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2017/11/26.
 */
public class DegreeVo implements Serializable{

    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column xueli.degreename
     *
     * @mbggenerated
     */
    private String degreename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDegreename() {
        return degreename;
    }

    public void setDegreename(String degreename) {
        this.degreename = degreename;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DegreeVo{");
        sb.append("id=").append(id);
        sb.append(", degreename='").append(degreename).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
