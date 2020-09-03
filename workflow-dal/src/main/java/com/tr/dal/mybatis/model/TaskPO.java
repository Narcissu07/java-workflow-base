package com.tr.dal.mybatis.model;

import java.util.Date;

/**
 * Created by yangjie on 2018/2/2.
 */
public class TaskPO {
    private String taskId;
    private String processInstanceId;
    private String taskName;
    private String taskAssignee;
    private Date taskCreateTime;
    private String taskDescription;
    private String taskTenantId;
    private String taskFormKey;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }

    public void setTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }

    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskTenantId() {
        return taskTenantId;
    }

    public void setTaskTenantId(String taskTenantId) {
        this.taskTenantId = taskTenantId;
    }

    public String getTaskFormKey() {
        return taskFormKey;
    }

    public void setTaskFormKey(String taskFormKey) {
        this.taskFormKey = taskFormKey;
    }
}
