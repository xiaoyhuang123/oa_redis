package com.ft.backend.work.client.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by huanghongyi on 2017/10/23.
 */
public class WorkerVo implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.work_number
     *
     * @mbggenerated
     */
    private String workNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.sex
     *
     * @mbggenerated
     */
    private String sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.nation
     *
     * @mbggenerated
     */
    private String nation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.cardid
     *
     * @mbggenerated
     */
    private String cardid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.birthday
     *
     * @mbggenerated
     */
    private Date birthday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.join_time
     *
     * @mbggenerated
     */
    private Date joinTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.zhiwu_id
     *
     * @mbggenerated
     */
    private Long zhiwuId;
    private String zhiwuJc;
    private String zhiwuName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.telno
     *
     * @mbggenerated
     */
    private String telno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.chejianid
     *
     * @mbggenerated
     */
    private Long chejianId;
    private String chejianName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.cheduiid
     *
     * @mbggenerated
     */
    private Long cheduiId;
    private String cheduiName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.is_deleted
     *
     * @mbggenerated
     */
    private Integer isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worker.imgurl
     *
     * @mbggenerated
     */
    private String imgurl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.political_id
     *
     * @mbggenerated
     */
    private Long politicalId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.political
     *
     * @mbggenerated
     */
    private String political;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.work_card_no
     *
     * @mbggenerated
     */
    private String workCardNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.group_id
     *
     * @mbggenerated
     */
    private Long groupId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.group_name
     *
     * @mbggenerated
     */
    private String groupName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.allow_train_type_id
     *
     * @mbggenerated
     */
    private Long allowTrainTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.allow_train_type_name
     *
     * @mbggenerated
     */
    private String allowTrainTypeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.first_get_time
     *
     * @mbggenerated
     */
    private Date firstGetTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.start_time
     *
     * @mbggenerated
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.end_time
     *
     * @mbggenerated
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.address
     *
     * @mbggenerated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.license_img_url
     *
     * @mbggenerated
     */
    private String licenseImgUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.position_title_id
     *
     * @mbggenerated
     */
    private Long positionTitleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_worker.position_title
     *
     * @mbggenerated
     */
    private String positionTitle;

    private Long degreeId;
    private String degreeName;

    private Integer role;

    private String province;

    private String feature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Long getZhiwuId() {
        return zhiwuId;
    }

    public void setZhiwuId(Long zhiwuId) {
        this.zhiwuId = zhiwuId;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public Long getChejianId() {
        return chejianId;
    }

    public void setChejianId(Long chejianId) {
        this.chejianId = chejianId;
    }

    public Long getCheduiId() {
        return cheduiId;
    }

    public void setCheduiId(Long cheduiId) {
        this.cheduiId = cheduiId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getZhiwuJc() {
        return zhiwuJc;
    }

    public void setZhiwuJc(String zhiwuJc) {
        this.zhiwuJc = zhiwuJc;
    }

    public String getZhiwuName() {
        return zhiwuName;
    }

    public void setZhiwuName(String zhiwuName) {
        this.zhiwuName = zhiwuName;
    }

    public String getChejianName() {
        return chejianName;
    }

    public void setChejianName(String chejianName) {
        this.chejianName = chejianName;
    }

    public String getCheduiName() {
        return cheduiName;
    }

    public void setCheduiName(String cheduiName) {
        this.cheduiName = cheduiName;
    }

    public Long getPoliticalId() {
        return politicalId;
    }

    public void setPoliticalId(Long politicalId) {
        this.politicalId = politicalId;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getWorkCardNo() {
        return workCardNo;
    }

    public void setWorkCardNo(String workCardNo) {
        this.workCardNo = workCardNo;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getAllowTrainTypeId() {
        return allowTrainTypeId;
    }

    public void setAllowTrainTypeId(Long allowTrainTypeId) {
        this.allowTrainTypeId = allowTrainTypeId;
    }

    public String getAllowTrainTypeName() {
        return allowTrainTypeName;
    }

    public void setAllowTrainTypeName(String allowTrainTypeName) {
        this.allowTrainTypeName = allowTrainTypeName;
    }

    public Date getFirstGetTime() {
        return firstGetTime;
    }

    public void setFirstGetTime(Date firstGetTime) {
        this.firstGetTime = firstGetTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseImgUrl() {
        return licenseImgUrl;
    }

    public void setLicenseImgUrl(String licenseImgUrl) {
        this.licenseImgUrl = licenseImgUrl;
    }

    public Long getPositionTitleId() {
        return positionTitleId;
    }

    public void setPositionTitleId(Long positionTitleId) {
        this.positionTitleId = positionTitleId;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public Long getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Long degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WorkerVo{");
        sb.append("id=").append(id);
        sb.append(", workNumber='").append(workNumber).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", nation='").append(nation).append('\'');
        sb.append(", cardid='").append(cardid).append('\'');
        sb.append(", birthday=").append(birthday);
        sb.append(", joinTime=").append(joinTime);
        sb.append(", zhiwuId=").append(zhiwuId);
        sb.append(", zhiwuJc='").append(zhiwuJc).append('\'');
        sb.append(", zhiwuName='").append(zhiwuName).append('\'');
        sb.append(", telno='").append(telno).append('\'');
        sb.append(", chejianId=").append(chejianId);
        sb.append(", chejianName='").append(chejianName).append('\'');
        sb.append(", cheduiId=").append(cheduiId);
        sb.append(", cheduiName='").append(cheduiName).append('\'');
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", imgurl='").append(imgurl).append('\'');
        sb.append(", politicalId=").append(politicalId);
        sb.append(", political='").append(political).append('\'');
        sb.append(", workCardNo='").append(workCardNo).append('\'');
        sb.append(", groupId=").append(groupId);
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", allowTrainTypeId=").append(allowTrainTypeId);
        sb.append(", allowTrainTypeName='").append(allowTrainTypeName).append('\'');
        sb.append(", firstGetTime=").append(firstGetTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", address='").append(address).append('\'');
        sb.append(", licenseImgUrl='").append(licenseImgUrl).append('\'');
        sb.append(", positionTitleId=").append(positionTitleId);
        sb.append(", positionTitle='").append(positionTitle).append('\'');
        sb.append(", degreeId=").append(degreeId);
        sb.append(", degreeName='").append(degreeName).append('\'');
        sb.append(", role=").append(role);
        sb.append(", province='").append(province).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
