package com.ft.backend.work.model;

import java.io.Serializable;

public class Zhiwu implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_zhiwu.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_zhiwu.name
     *
     * @mbggenerated
     */
    private String jc;
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_zhiwu.id
     *
     * @return the value of sys_zhiwu.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_zhiwu.id
     *
     * @param id the value for sys_zhiwu.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_zhiwu.name
     *
     * @return the value of sys_zhiwu.name
     *
     * @mbggenerated
     */



    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_zhiwu.name
     *
     * @param name the value for sys_zhiwu.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Zhiwu{");
        sb.append("id=").append(id);
        sb.append(", jc='").append(jc).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}