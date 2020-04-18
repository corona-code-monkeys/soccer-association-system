/**
 * Thr class represents the management of the team
 */
package com.SAS.teamManagenemt;

import com.SAS.User.*;
import com.SAS.team.Team;

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


}
