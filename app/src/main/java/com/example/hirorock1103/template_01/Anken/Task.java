package com.example.hirorock1103.template_01.Anken;

public class Task {

    private int id;
    private String taskName;
    private int ankenId;
    private String detail;
    private String endDate;
    private float manDays;
    private int status;//0:未対応 1:comlete 2:対応中
    private String createdate;

    public static String[] getStatusArray(){
        String[] str = {"未対応", "対応済", "対応中"};
        return str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getAnkenId() {
        return ankenId;
    }

    public void setAnkenId(int ankenId) {
        this.ankenId = ankenId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public float getManDays() {
        return manDays;
    }

    public void setManDays(float manDays) {
        this.manDays = manDays;
    }
}
