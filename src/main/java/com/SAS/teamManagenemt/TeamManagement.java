/**
 * Thr class represents the management of the team
 */
package com.SAS.teamManagenemt;

import com.SAS.User.*;
import com.SAS.stadium.Stadium;
import com.SAS.team.Team;

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
     * This function allows the team owner to add an asset to the team. Assets are facility, player, coach.
     * @param assetType
     * @param team
     * @return the created asset
     */
    public TeamAsset AddAssetToTeam (String assetType, Team team) {
        TeamAsset asset = null;
        //create the asset
        switch (assetType) {
            case "Player":
                asset = (Player) userController.createUser(null, null, null, UserType.PLAYER, true);
                team.addPlayerToTeam((Player)asset);
                break;
            case "Facility":
                asset = new Stadium();
//                team.addFacility((Facility)asset);
                break;
            case "Coach":
                asset = (Coach) userController.createUser(null, null, null, UserType.COACH, true);
                team.setCoach((Coach)asset);
                break;
        }
        asset.setTeam(team);
        return asset;
    }

    /**
     * This function receives a list of details and a teamAsset and updates the asset's details
     * @param asset
     * @param details
     */
    public void editAssetDetails(TeamAsset asset, List<String> details){
        asset.editDetails(details);
    }

    /**
     * This function removes a team asset
     * @param asset
     * @param team
     */
    public void removeAsset(TeamAsset asset, Team team){
        asset.removeAssetFromTeam();
        asset = null;
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


}
