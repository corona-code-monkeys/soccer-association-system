/**
 * Thr class represents the management of the team
 */
package com.SAS.teamManagenemt;

import com.SAS.User.*;
import com.SAS.team.Team;

import java.util.LinkedList;
import java.util.List;

public class TeamManagement {

    private UserController userController;

    /**
     * Constructor
     * @param userController
     */
    public TeamManagement(UserController userController) {
        this.userController = userController;
    }

    /**
     * The function receives a user and a team and adds the user to the owners of the team
     * in case he's not already a team owner
     * @param newTeamOwner
     * @param team
     * @param nominatedBy
     */
    public User addAdditionalTeamOwner(User newTeamOwner, Team team, User nominatedBy) {
        //check if the user can owns the team and that the nominate is the owner of the team
        if (validateTeamOwner(newTeamOwner, team) && ownsTeam(team, nominatedBy)) {
            newTeamOwner = userController.addRoleToUser(newTeamOwner, UserType.TEAM_OWNER, true);
            team.addTeamOwner((TeamOwner)newTeamOwner);
            ((TeamOwner) newTeamOwner).setTeam(team);
            ((TeamOwner) newTeamOwner).setNominatedBy((TeamOwner)nominatedBy);

            return newTeamOwner;
        }

        else {
            System.out.println("The user is unauthorized to own the team");
            return null;
        }
    }

    /**
     * The function returns true if the user can be the team owner (he's a player / coach / manager of the team)
     * @param user
     * @param team
     * @return
     */
    private boolean validateTeamOwner(User user, Team team) {
        boolean valid = false;

        // checks that the user doesn't owns other teams
        if (!(user instanceof TeamOwner)) {
            if (user instanceof Player && ((Player)user).getTeam() == team) {
                valid = true;
            }

            else if (user instanceof Coach && ((Coach)user).getTeam() == team) {
                valid = true;
            }

            else if (user instanceof TeamManager && ((TeamManager)user).getTeam() == team) {
                valid = true;
            }

        }

        return valid;
    }

    /**
     * The function returns true if the user is the owner of the team, otherwise returns false
     * @param team
     * @param nominatedBy
     * @return true - if the user is the team owner, otherwise - false
     */
    private boolean ownsTeam(Team team, User nominatedBy) {
         return nominatedBy instanceof TeamOwner && ((TeamOwner) nominatedBy).getTeam() == team;
    }

    /**
     * The function receives a team and a team owner and closes this team
     * @param team
     * @param teamOwner
     */
    public void closeTeam(Team team, User teamOwner) {
        if (ownsTeam(team, teamOwner) && team.isActive()) {
            team.inactivateTeam();
            sendNotificationClose(team, "closed");
        }

        else {
            System.out.println("The user is unauthorized to close the team");
        }
    }

    /**
     * The function receives a team and a team owner and opens this team
     * @param team
     * @param teamOwner
     */
    public void openTeam(Team team, User teamOwner) {
        if (ownsTeam(team, teamOwner) && !team.isActive()) {
            team.reactivateTeam();
            sendNotificationClose(team, "opened");
        }

        else {
            System.out.println("The user is unauthorized to open the team");
        }
    }

    /**
     * The function receives a team that has been closed and send the message to all the team management
     * and the system admins
     * @param team
     */
    //TODO: add system admins from DB
    private void sendNotificationClose(Team team, String message) {
        String close = "The team " + team.getName() + " has been " + message + ".";

        //get all the management of the team
        List<User> management = new LinkedList<>();
        management.addAll(team.getOwners());
        User manager = team.getManager();
        if (manager != null) {
            management.add(manager);
        }

        //add the system admins from DB

        for (User user: management) {
            ((Role)user).getNotification(close);
        }

    }


}
