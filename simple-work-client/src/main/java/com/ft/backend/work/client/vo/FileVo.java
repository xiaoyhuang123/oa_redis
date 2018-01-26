package com.ft.backend.work.client.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Wonderful on 2017/11/15.
 */
public class FileVo implements Serializable {
    private Long id;
    private String fileName;
    private String filePath;
    private Long taskId;
    private String createrName;
    private Integer fileType;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FileVo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", taskId=" + taskId +
                ", createrName='" + createrName + '\'' +
                ", fileType=" + fileType +
                ", createTime=" + createTime +
                '}';
    }
}
