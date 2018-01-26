package com.ft.backend.work.client.vo;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2017/11/25.
 */
public class PoliticVo implements Serializable{

    private Long id;

    private String politicname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoliticname() {
        return politicname;
    }

    public void setPoliticname(String politicname) {
        this.politicname = politicname;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PoliticVo{");
        sb.append("id=").append(id);
        sb.append(", politicname='").append(politicname).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
