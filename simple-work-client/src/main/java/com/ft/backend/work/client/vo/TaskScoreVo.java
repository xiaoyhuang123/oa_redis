package com.ft.backend.work.client.vo;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2017/11/19.
 */
public class TaskScoreVo implements Serializable{

    public static int SCORE_LEVEL_BETTER=1;
    public static int SCORE_LEVEL_GOOD=2;
    public static int SCORE_LEVEL_MIDDLE=3;
    public static int SCORE_LEVEL_WORSE=4;

    private Long targetId;

    private String targetName;

    private Integer score=0;

    private Integer taskTotal=0;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(Integer taskTotal) {
        this.taskTotal = taskTotal;
    }

    @Override
    public String toString() {
        return "TaskScoreVo{" +
                "targetId=" + targetId +
                ", targetName=" + targetName +
                ", score=" + score +
                ", taskTotal=" + taskTotal +
                '}';
    }
}
