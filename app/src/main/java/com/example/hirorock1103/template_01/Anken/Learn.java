package com.example.hirorock1103.template_01.Anken;

public class Learn {

    private int id;
    private String learnTitle;
    private int status;//1:完了,2:疑問,3:疑問→解決
    private String createdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLearnTitle() {
        return learnTitle;
    }

    public void setLearnTitle(String learnTitle) {
        this.learnTitle = learnTitle;
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
}
