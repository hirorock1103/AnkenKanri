package com.example.hirorock1103.template_01.Anken;

public class MileStone {

    private int id;
    private String name;
    private String detail;
    private int ankenId;
    private String endDate;
    private int status;//0:未対応 1:対応中 2:対応完了 9:削除
    private String createdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnkenId() {
        return ankenId;
    }

    public void setAnkenId(int ankenId) {
        this.ankenId = ankenId;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
