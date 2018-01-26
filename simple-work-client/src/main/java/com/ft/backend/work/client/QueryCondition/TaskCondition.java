package com.ft.backend.work.client.QueryCondition;

import com.ft.backend.work.client.common.QueryCondition;

import java.util.Date;

/**
 * Created by huanghongyi on 2017/11/19.
 */
public class TaskCondition extends QueryCondition{

    private Long chejianId;
    private Long cheduiId;
    private Long workerId;

    private Date startTime;

    private Date endTime;

    //1:fuzern   2：luoshiren
    private Integer targetType;

    //1 chejian维度  2： chedui维度  3 worker维度
    private Integer staticsType;

    //group by
    private String groupByName;

    //0 已签收   1：未签收
    private Integer isReceived;

    //0 已完成   1：未完成
    private Integer isCompleted;

    private Integer ignoreParentIdNull;

    private Integer score;

    //1:未完成  2：已完成   3：超期已完成  4：超期未完成
    private Integer taskState;


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

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
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

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getStaticsType() {
        return staticsType;
    }

    public void setStaticsType(Integer staticsType) {
        this.staticsType = staticsType;
    }

    public String getGroupByName() {
        return groupByName;
    }

    public void setGroupByName(String groupByName) {
        this.groupByName = groupByName;
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

    public Integer getIgnoreParentIdNull() {
        return ignoreParentIdNull;
    }

    public void setIgnoreParentIdNull(Integer ignoreParentIdNull) {
        this.ignoreParentIdNull = ignoreParentIdNull;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }
}
