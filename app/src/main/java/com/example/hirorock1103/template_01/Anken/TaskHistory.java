package com.example.hirorock1103.template_01.Anken;

public class TaskHistory {

    private int id;
    private int taskId;
    private String targetdate;
    private String content;
    private float manDay;
    private String createdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(String targetdate) {
        this.targetdate = targetdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getManDay() {
        return manDay;
    }

    public void setManDay(float manDay) {
        this.manDay = manDay;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
}
