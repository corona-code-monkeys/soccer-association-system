/**
 * The class represents a personal page of a coach / player or a team
 */

package com.SAS.domain.User;

public class PersonalPage {

    public static int counter = 0;

    private int pageID;
    private String description;

    /**
     * Constructor
     * @param description - String
     */
    public PersonalPage(String description) {
        this.pageID = counter++;
        this.description = description;
    }

    /**
     * The function returns the pageID of the personal page
     * @return
     */
    public int getPageID() {
        return pageID;
    }

    /**
     * The function returns the description of the personal page
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * The function sets the the description of the personal page
     * @param description
     */
    public void setDescription(String description) {
        if (description != null & !description.trim().isEmpty()) {
            this.description = description;
        }
    }

    /**
     * This function presents the personal page
     */
    public void showPersonalPage(){
        System.out.println("Illustration of the personal page presentation: " + '\n' +
                "⚽⚽⚽⚽⚽⚽⚽⚽⚽⚽ " + this.description + " ⚽⚽⚽⚽⚽⚽⚽⚽⚽⚽" + '\n' + '\n');
    }


}
