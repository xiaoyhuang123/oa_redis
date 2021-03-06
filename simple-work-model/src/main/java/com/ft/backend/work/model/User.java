package com.ft.backend.work.model;

import java.util.Date;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.user_name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.password
     *
     * @mbggenerated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.chejian_id
     *
     * @mbggenerated
     */
    private Long chejianId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.chedui_id
     *
     * @mbggenerated
     */
    private Long cheduiId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.work_id
     *
     * @mbggenerated
     */
    private Long workId;

    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.role
     *
     * @mbggenerated
     */
    private Integer role;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.id
     *
     * @return the value of sys_user.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.id
     *
     * @param id the value for sys_user.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.user_name
     *
     * @return the value of sys_user.user_name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.user_name
     *
     * @param userName the value for sys_user.user_name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.password
     *
     * @return the value of sys_user.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.password
     *
     * @param password the value for sys_user.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.chejian_id
     *
     * @return the value of sys_user.chejian_id
     *
     * @mbggenerated
     */
    public Long getChejianId() {
        return chejianId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.chejian_id
     *
     * @param chejianId the value for sys_user.chejian_id
     *
     * @mbggenerated
     */
    public void setChejianId(Long chejianId) {
        this.chejianId = chejianId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.chedui_id
     *
     * @return the value of sys_user.chedui_id
     *
     * @mbggenerated
     */
    public Long getCheduiId() {
        return cheduiId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.chedui_id
     *
     * @param cheduiId the value for sys_user.chedui_id
     *
     * @mbggenerated
     */
    public void setCheduiId(Long cheduiId) {
        this.cheduiId = cheduiId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.work_id
     *
     * @return the value of sys_user.work_id
     *
     * @mbggenerated
     */
    public Long getWorkId() {
        return workId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.work_id
     *
     * @param workId the value for sys_user.work_id
     *
     * @mbggenerated
     */
    public void setWorkId(Long workId) {
        this.workId = workId ;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.role
     *
     * @return the value of sys_user.role
     *
     * @mbggenerated
     */
    public Integer getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.role
     *
     * @param role the value for sys_user.role
     *
     * @mbggenerated
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", chejianId=").append(chejianId);
        sb.append(", cheduiId=").append(cheduiId);
        sb.append(", workId='").append(workId).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}