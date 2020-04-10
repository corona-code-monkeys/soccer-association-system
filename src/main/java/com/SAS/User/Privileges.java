/**
 * The class represents the a privileges singleton that will be held by the role class
 */

package com.SAS.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Privileges {

    private volatile static Privileges instance = null;
    public HashMap<String, HashSet<String>> privileges;

    /**
     * constructor for the singleton
     */
    private Privileges(){
        privileges = new HashMap<>();
        initPrivileges();
    }

    public static Privileges getInstance() {
        if(instance == null){
            synchronized(Privileges.class){
                if(instance == null) {
                    instance = new Privileges();
                }
            }
        }
        return instance;
    }

    // <editor-fold desc="Initialize privileges HashMap"
    private void initPrivileges() {
        this.privileges.put("Guest", new HashSet<>(Arrays.asList
                ("viewPages+LS", "searchByNCK")));
        this.privileges.put("Fan", new HashSet<>(Arrays.asList
                ("followPage", "getNoti", "fileReport", "viewSHistory", "editPDetails")));
        this.privileges.put("Player", new HashSet<>(Arrays.asList
                ("editPDetails", "uploadPContent")));
        this.privileges.put("Coach", new HashSet<>(Arrays.asList
                ("editPDetails", "uploadPContent")));
        this.privileges.put("TeamOwner", new HashSet<>(Arrays.asList
                ("add/removeA", "add/removeTO", "add/removeTM", "editTMPriv", "closeTNP", "addTrans", "editPDetails")));
        this.privileges.put("TeamManager", new HashSet<>(Arrays.asList
                ("add/removeA", "editPDetails")));
        this.privileges.put("SystemAdmin", new HashSet<>(Arrays.asList
                ("editPDetails", "closeTP", "removeRegistered", "view/replyReport", "viewSysSettings", "enableRS")));
        this.privileges.put("AssociationRepresentative", new HashSet<>(Arrays.asList
                ("editPDetails", "defineL", "defineSL", "add/removeR", "setRinL", "define/changePolicy", "enableGScheduling", "defineBudgetRules", "manageAssBudget")));
        this.privileges.put("Referee", new HashSet<>(Arrays.asList
                ("editPDetails", "viewMyGames","editGameEvent")));
    }
    // </editor-fold>

    /**
     * This function returns the appropriate privileges for the received role
     * @param role - a string representing the role
     * @param approved- a boolean representing whether the team manager can add/remove assets
     * @return - a hashset of strings representing the role privileges
     */
    public HashSet<String> getPrivileges(String role, boolean approved) {
        switch (role){
            case "TeamOwner":
                //a team owner need to get approval to receive privileges from the team owner, once we have
                //UI will need to popup a window to get approval
                if (approved)
                    return privileges.get("TeamOwner");
            default:
                return privileges.get(role);
        }


    }
}
