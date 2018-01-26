package com.ft.backend.work.client.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/10/23.
 */
public class TaskVo  implements Serializable{

    public static int WORKER_TYPE_NONE=0;
    public static int WORKER_TYPE_FUZEREN=1;
    public static int WORKER_TYPE_LUOSHIREN=2;

    public static Long TASKVO_TYPE_PARENT_DEFAULT=-1L;

    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.work_type
     *
     * @mbggenerated
     */
    private String workType;

    private Long chejianId;
    private String chejianName;


    private Long cheduiId;
    private String cheduiName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.woker_id
     *
     * @mbggenerated
     */
    private Long workerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.worker_name
     *
     * @mbggenerated
     */
    private String workerName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.role_type
     *
     * @mbggenerated
     */
    private Integer roleType;

    private Long leaderId;
    private String  leaderName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.start_time
     *
     * @mbggenerated
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.end_time
     *
     * @mbggenerated
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.summary
     *
     * @mbggenerated
     */
    private String summary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.details
     *
     * @mbggenerated
     */
    private String details;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.file_url
     *
     * @mbggenerated
     */
    private String fileUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.feedback
     *
     * @mbggenerated
     */
    private Integer feedback;
    private String feedbackType;
    private String feedbackContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.publisher
     *
     * @mbggenerated
     */
    private String publisher;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.is_received
     *
     * @mbggenerated
     */
    private Integer isReceived;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.is_completed
     *
     * @mbggenerated
     */
    private Integer isCompleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.parent_id
     *
     * @mbggenerated
     */

    private Integer score;
    private String scoreResult;
    private String scoreUsername;

    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_task.is_deleted
     *
     * @mbggenerated
     */
    private Integer isDeleted;

    private Date receiveTime;
    private Date completeTime;

    private String fuShow;
    private String fuHidden;
    private String luoShow;
    private String luoHidden;

    private Integer targetType;

    private Boolean completeFlag;

    private Boolean notCompleteFlag;

    private Boolean noReceiveNoCompleteFlag;
    private Boolean receiveNoCompleteFlag;
    private Boolean receiveCompleteFlag;
    private Boolean disable;


    private int canFeedBack=0;
    private int canDelete=0;
    private int canScore=0;

    private int containTaskFile=0;
    private int containFeedbackFile=0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Long getChejianId() {
        return chejianId;
    }

    public void setChejianId(Long chejianId) {
        this.chejianId = chejianId;
    }

    public String getChejianName() {
        return chejianName;
    }

    public void setChejianName(String chejianName) {
        this.chejianName = chejianName;
    }

    public Long getCheduiId() {
        return cheduiId;
    }

    public void setCheduiId(Long cheduiId) {
        this.cheduiId = cheduiId;
    }

    public String getCheduiName() {
        return cheduiName;
    }

    public void setCheduiName(String cheduiName) {
        this.cheduiName = cheduiName;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getFeedback() {
        return feedback;
    }

    public void setFeedback(Integer feedback) {
        this.feedback = feedback;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Integer isReceived) {
        this.isReceived = isReceived;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getScoreResult() {
        return scoreResult;
    }

    public void setScoreResult(String scoreResult) {
        this.scoreResult = scoreResult;
    }

    public String getScoreUsername() {
        return scoreUsername;
    }

    public void setScoreUsername(String scoreUsername) {
        this.scoreUsername = scoreUsername;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getFuShow() {
        return fuShow;
    }

    public void setFuShow(String fuShow) {
        this.fuShow = fuShow;
    }

    public String getFuHidden() {
        return fuHidden;
    }

    public void setFuHidden(String fuHidden) {
        this.fuHidden = fuHidden;
    }

    public String getLuoShow() {
        return luoShow;
    }

    public void setLuoShow(String luoShow) {
        this.luoShow = luoShow;
    }

    public String getLuoHidden() {
        return luoHidden;
    }

    public void setLuoHidden(String luoHidden) {
        this.luoHidden = luoHidden;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Boolean getCompleteFlag() {
        return completeFlag;
    }

    public void setCompleteFlag(Boolean completeFlag) {
        this.completeFlag = completeFlag;
    }

    public Boolean getNotCompleteFlag() {
        return notCompleteFlag;
    }

    public void setNotCompleteFlag(Boolean notCompleteFlag) {
        this.notCompleteFlag = notCompleteFlag;
    }

    public Boolean getNoReceiveNoCompleteFlag() {
        return noReceiveNoCompleteFlag;
    }

    public void setNoReceiveNoCompleteFlag(Boolean noReceiveNoCompleteFlag) {
        this.noReceiveNoCompleteFlag = noReceiveNoCompleteFlag;
    }

    public Boolean getReceiveNoCompleteFlag() {
        return receiveNoCompleteFlag;
    }

    public void setReceiveNoCompleteFlag(Boolean receiveNoCompleteFlag) {
        this.receiveNoCompleteFlag = receiveNoCompleteFlag;
    }

    public Boolean getReceiveCompleteFlag() {
        return receiveCompleteFlag;
    }

    public void setReceiveCompleteFlag(Boolean receiveCompleteFlag) {
        this.receiveCompleteFlag = receiveCompleteFlag;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public int getCanFeedBack() {
        return canFeedBack;
    }

    public void setCanFeedBack(int canFeedBack) {
        this.canFeedBack = canFeedBack;
    }

    public int getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(int canDelete) {
        this.canDelete = canDelete;
    }

    public int getCanScore() {
        return canScore;
    }

    public void setCanScore(int canScore) {
        this.canScore = canScore;
    }

    public int getContainTaskFile() {
        return containTaskFile;
    }

    public void setContainTaskFile(int containTaskFile) {
        this.containTaskFile = containTaskFile;
    }

    public int getContainFeedbackFile() {
        return containFeedbackFile;
    }

    public void setContainFeedbackFile(int containFeedbackFile) {
        this.containFeedbackFile = containFeedbackFile;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TaskVo{");
        sb.append("id=").append(id);
        sb.append(", workType='").append(workType).append('\'');
        sb.append(", chejianId=").append(chejianId);
        sb.append(", chejianName='").append(chejianName).append('\'');
        sb.append(", cheduiId=").append(cheduiId);
        sb.append(", cheduiName='").append(cheduiName).append('\'');
        sb.append(", workerId=").append(workerId);
        sb.append(", workerName='").append(workerName).append('\'');
        sb.append(", roleType=").append(roleType);
        sb.append(", leaderId=").append(leaderId);
        sb.append(", leaderName='").append(leaderName).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", details='").append(details).append('\'');
        sb.append(", fileUrl='").append(fileUrl).append('\'');
        sb.append(", feedback=").append(feedback);
        sb.append(", feedbackType='").append(feedbackType).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append(", isReceived=").append(isReceived);
        sb.append(", isCompleted=").append(isCompleted);
        sb.append(", score=").append(score);
        sb.append(", scoreResult='").append(scoreResult).append('\'');
        sb.append(", scoreUsername='").append(scoreUsername).append('\'');
        sb.append(", parentId=").append(parentId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", receiveTime=").append(receiveTime);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", fuShow='").append(fuShow).append('\'');
        sb.append(", fuHidden='").append(fuHidden).append('\'');
        sb.append(", luoShow='").append(luoShow).append('\'');
        sb.append(", luoHidden='").append(luoHidden).append('\'');
        sb.append(", targetType=").append(targetType);
        sb.append(", completeFlag=").append(completeFlag);
        sb.append(", notCompleteFlag=").append(notCompleteFlag);
        sb.append(", noReceiveNoCompleteFlag=").append(noReceiveNoCompleteFlag);
        sb.append(", receiveNoCompleteFlag=").append(receiveNoCompleteFlag);
        sb.append(", receiveCompleteFlag=").append(receiveCompleteFlag);
        sb.append(", disable=").append(disable);
        sb.append(", canFeedBack=").append(canFeedBack);
        sb.append(", canDelete=").append(canDelete);
        sb.append(", canScore=").append(canScore);
        sb.append(", containTaskFile=").append(containTaskFile);
        sb.append(", containFeedbackFile=").append(containFeedbackFile);
        sb.append(", feedbackContent=").append(feedbackContent);
        sb.append('}');
        return sb.toString();
    }
}