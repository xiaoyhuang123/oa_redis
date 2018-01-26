package com.ft.backend.work.client.vo;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2017/11/13.
 */
public class ZhiwuVo implements Serializable{

    private  Long id;

    private String jc;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ZhiwuVo{");
        sb.append("id=").append(id);
        sb.append(", jc='").append(jc).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
