package com.ft.backend.work.client.vo;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2017/11/19.
 */
public class TaskStasticsVo implements Serializable{

    private String title;
    private Long targetId;
    private String targetName;
    private String workType;

    private Integer taskTotal=0;
    private Integer completeTotal=0;
    private String completePercentage="-";

    private Integer better=0;
    private Integer good=0;
    private Integer middle=0;
    private Integer terriable=0;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Integer getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(Integer taskTotal) {
        this.taskTotal = taskTotal;
    }

    public Integer getCompleteTotal() {
        return completeTotal;
    }

    public void setCompleteTotal(Integer completeTotal) {
        this.completeTotal = completeTotal;
    }

    public String getCompletePercentage() {
        return completePercentage;
    }

    public void setCompletePercentage(String completePercentage) {
        this.completePercentage = completePercentage;
    }

    public Integer getBetter() {
        return better;
    }

    public void setBetter(Integer better) {
        this.better = better;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getMiddle() {
        return middle;
    }

    public void setMiddle(Integer middle) {
        this.middle = middle;
    }

    public Integer getTerriable() {
        return terriable;
    }

    public void setTerriable(Integer terriable) {
        this.terriable = terriable;
    }

    @Override
    public String toString() {
        return "TaskStasticsVo{" +
                "title='" + title + '\'' +
                ", targetId=" + targetId +
                ", targetName='" + targetName + '\'' +
                ", workType='" + workType + '\'' +
                ", taskTotal=" + taskTotal +
                ", completeTotal=" + completeTotal +
                ", completePercentage='" + completePercentage + '\'' +
                ", better=" + better +
                ", good=" + good +
                ", middle=" + middle +
                ", terriable=" + terriable +
                '}';
    }
}
