/**
 * Thr class represents the management of the team
 */
package com.SAS.teamManagenemt;

import com.SAS.User.*;
import com.SAS.team.Team;
import com.SAS.transaction.Transaction;
import com.SAS.transaction.TransactionType;

import java.time.LocalDate;
import java.util.List;

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
     * The function receives a team owner, the team and the nominated user and removes the owner from the team owners
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
     * The fcuntion receives the parameters of new transaction and the team and add it if it's legal -
     * not exceed the budget of the team
     * @param team
     * @param transDetails
     * @return
     */
    public Transaction addTransactionToTeam(Team team, List<String> transDetails, User teamOwner) {
        if (ownsTeam(team, teamOwner)) {
            //first amount, second type, third date and last description
            double amount = Double.parseDouble(transDetails.get(0));
            TransactionType type = convertStringToTrasactionType(transDetails.get(1));
            LocalDate date = LocalDate.parse(transDetails.get(2));
            String description = transDetails.get(3);

            //create the transaction
            Transaction transaction = new Transaction(amount, type, date, team, description, (TeamOwner) teamOwner);

            boolean isTransAdded = team.addTransactionToTeam(transaction);
            if (!isTransAdded) {
                System.out.println("The transaction could not be added to the team, it exceeded the team's budget.");
            }

            return transaction;
        }

        else {
            System.out.println("The user is unauthorized to report transactions.");
            return null;
        }
    }

    /**
     * The function converts string to transaction type
     * @param type
     * @return
     */
    private TransactionType convertStringToTrasactionType(String type){
        switch (type) {
            case "Expense":
                return TransactionType.EXPENSE;
            case "Income":
                return TransactionType.INCOME;
            default:
                return null;
        }
    }

}
