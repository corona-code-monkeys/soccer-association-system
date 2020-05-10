package com.SAS.domain.report;

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
     */
    public boolean setPageId(int pageId) {
        if (pageId > 0) {
            this.pageId = pageId;
            return true;
        }
        return false;
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
     */
    public boolean setDescr(String descr) {
        if(descr != null && !descr.trim().isEmpty()){
            this.descr = descr;
            return true;
        }
        return false;
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
     */
    public boolean setUserId(int userId) {
        if (userId > 0) {
            this.userId = userId;
            return true;
        }
        return false;
    }
}
