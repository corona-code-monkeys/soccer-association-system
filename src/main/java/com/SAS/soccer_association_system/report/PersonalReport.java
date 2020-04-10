package com.SAS.soccer_association_system.report;

/**
 * The class represent a complain report made by a registered user about the system
 */
public class PersonalReport {

    private int pageId;
    private String descr;
    private int userId;

    /**
     * Empty constructor
     */
    public PersonalReport() {
    }

    /**
     * Params constructor
     * @param pageId
     * @param descr
     * @param userId
     */
    public PersonalReport(int pageId, String descr, int userId) {
        this.pageId = pageId;
        this.descr = descr;
        this.userId = userId;
    }

    /**
     * The function returns the pageId of the page that user complain about
     * @return
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * The function sets the pageId of the page that user complain about
     * @return
     */
    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    /**
     * The function returns the description of the complain
     * @return
     */
    public String getDescr() {
        return descr;
    }

    /**
     * The function sets the description of the complain
     * @return
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * The function returns the userId of the user that complain
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * The function sets the userId of the user that complain
     * @return
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
