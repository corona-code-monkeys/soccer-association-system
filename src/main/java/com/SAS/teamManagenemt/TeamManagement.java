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
        }

        else {
            System.out.println("The user is unauthorized to own the team");
        }

        return newTeamOwner;
    }

    /**
     * The function receives a user and a team and adds the user as the team manager
     * TODO: UI will need to popup a window to get approval
     * @param newTeamManager
     * @param team
     * @param nominatedBy
     * @param approval - a boolean which determines if the manager got privileges from the team owner
     * @return newTeamManager - the team manager
     */
    public User addTeamManager(User newTeamManager, Team team, User nominatedBy, boolean approval){
        //check if the team already has a manager
        if(teamHasManager(team)){
            System.out.println("The team already has a manager, please remove it first to nominate a new manager");
            return newTeamManager;
        }
        //check if the user can manage this team and that the user that nominates him is the team owner
        if (validateTeamManager(newTeamManager, team) && ownsTeam(team, nominatedBy)){
            newTeamManager = userController.addRoleToUser(newTeamManager, UserType.TEAM_MANAGER,approval);
            team.setTeamManager((TeamManager)newTeamManager);
            ((TeamManager)newTeamManager).setTeam(team);
            ((TeamManager)newTeamManager).setNominatedBy((TeamOwner)nominatedBy);
        }
        else{
            System.out.println("The user is unauthorized to manage the team");
        }
        return newTeamManager;
    }

    /**
     * This function checks if the team already has a manager, if so returns true, otherwise returns false
     * @param team
     * @return true or false
     */
    private boolean teamHasManager(Team team) {
        return team.getManager() != null;
    }

    /**
     * This function checks if the user is not already a team manager or owner
     * @param newTeamManager
     * @param team
     * @return true or false
     */
    private boolean validateTeamManager(User newTeamManager, Team team) {
        if ((newTeamManager instanceof TeamManager || newTeamManager instanceof TeamOwner)){
            return false;
        }
        return true;
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
