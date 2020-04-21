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
        if (validateUserCanOwnTeam(newTeamOwner, team) && ownsTeam(team, nominatedBy)) {
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
     * This function removes a user from being a team manager by the team owner
     * @param teamManager
     * @param team
     * @param removedBy
     * @return
     */
    public User removeTeamManager(User teamManager, Team team, User removedBy) {
        //checks if the user manages the team and that the removing user is the owner of the team and nominated him
        if (validateTeamManager(teamManager, team, removedBy) && ownsTeam(team, removedBy)) {
            team.removeTeamManager((TeamManager)teamManager);
            ((TeamManager)teamManager).removeTeam();
            //get the inner user (previous role)
            teamManager = ((TeamManager) teamManager).getUser();
        }

        else {
            System.out.println("The user is unauthorized to remove the team manager");
        }

        return teamManager;
    }

    /**
     * This function verify whether the user is the team manager of the team,
     * and that the team owner that nominated him is the same user trying to remove him
     * @param teamManager
     * @param team
     * @param removedBy
     * @return true or false
     */
    private boolean validateTeamManager(User teamManager, Team team, User removedBy) {
        return teamManager instanceof TeamManager && ((TeamManager)teamManager).getTeam() == team &&
                removedBy instanceof TeamOwner && ((TeamManager)teamManager).getNominatedBy() == removedBy;
    }

     /* The function receives a team owner, the team and the nominated user and removes the owner from the team owners
     * if the user authorized to do so
     * @param teamOwner
     * @param team
     * @param nominatedBy
     * @return user
     */
    public User removeTeamOwner(User teamOwner, Team team, User nominatedBy) {
        //check if the user is owns the team and that the nominate is the owner of the team and nominated this user
        if (validateTeamOwner(teamOwner, team, nominatedBy) && ownsTeam(team, nominatedBy)) {
            removeTeamOwnerAndNominees(teamOwner, team);
        }

        else {
            System.out.println("The user is unauthorized to remove the team owner");
        }

        return teamOwner;
    }

    /**
     * The function receives a team owner and a team and removes the team owner and all his nominees
     * @param teamOwner
     * @param team
     */
    private void removeTeamOwnerAndNominees(User teamOwner, Team team) {
        List<TeamOwner> nominees = getUserNominees(teamOwner, team);
        team.removeTeamOwner((TeamOwner)teamOwner);
        ((TeamOwner) teamOwner).removeTeam();
        teamOwner = ((TeamOwner) teamOwner).getUser();

        for (User nominee: nominees) {
            removeTeamOwnerAndNominees(nominee, team);
        }

    }

    /**
     * The function receives a team owner and a team and returns the team owners that nominated by this team owner
     * @param teamOwner
     * @param team
     * @return
     */
    private List<TeamOwner> getUserNominees(User teamOwner, Team team) {
        List<TeamOwner> teamOwners = team.getOwners();
        List<TeamOwner> nominees = new LinkedList<>();

        for (TeamOwner owner: teamOwners) {
            if (owner.getNominatedBy() != null && owner.getNominatedBy().getUserID() == teamOwner.getUserID()) {
                nominees.add(owner);
            }
        }

        return nominees;
    }

    /**
     * The function returns true if the user is the owner of the team and if the nominated is the user
     * that nominates him to be the team's owner
     * @param teamOwner
     * @param team
     * @param nominatedBy
     * @return true or false
     */
    private boolean validateTeamOwner(User teamOwner, Team team, User nominatedBy) {
        return teamOwner instanceof TeamOwner && ((TeamOwner)teamOwner).getTeam() == team &&
                ((TeamOwner)teamOwner).getNominatedBy() == nominatedBy;
    }

    /**
     * The function returns true if the user can be the team owner (he's a player / coach / manager of the team)
     * @param user
     * @param team
     * @return
     */
    private boolean validateUserCanOwnTeam(User user, Team team) {
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
