package com.example.hirorock1103.template_01.Anken;

public class Anken {

    private int id;
    private String ankenName;
    private int ankenType;
    private int budget;
    private String startDate;
    private String endDate;
    private boolean complete;
    private String createdate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnkenName() {
        return ankenName;
    }

    public void setAnkenName(String ankenName) {
        this.ankenName = ankenName;
    }

    public int getAnkenType() {
        return ankenType;
    }

    public void setAnkenType(int ankenType) {
        this.ankenType = ankenType;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
}
