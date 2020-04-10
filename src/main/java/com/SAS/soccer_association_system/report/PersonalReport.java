package com.SAS.soccer_association_system.report;

public class PersonalReport {

    private int pageId;
    private String descr;
    private int userId;

    public PersonalReport() {
    }

    public PersonalReport(int pageId, String descr, int userId) {
        this.pageId = pageId;
        this.descr = descr;
        this.userId = userId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
