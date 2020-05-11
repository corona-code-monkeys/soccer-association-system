package com.SAS.crudoperations;

import com.SAS.User.*;
import com.SAS.team.Team;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;
import java.util.Map;

public class TeamCRUD {

    private static JdbcTemplate jdbcTemplate;


    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * The function creates a new team
     * @param teamName
     * @return true if the team created, otherwise returns false
     */
    public static boolean postTeam(String teamName) {

        try {
            String query = String.format("insert into team (team_name, isActive, isRegistered) values ( \"%s\", 0, 0);", teamName);
            jdbcTemplate.update(query);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function removes a team
     * @param teamName
     * @return
     */
    public static boolean removeTeam(String teamName) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        try {
            String query = String.format("delete from team where team_name = \"%s\";", teamName);
            jdbcTemplate.update(query);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * This function returns true if the team is active, otherwise false
     * @param teamName
     * @return
     */
    public static boolean isTeamActive(String teamName) {
        try {
            String queryTeamActive = String.format("SELECT is_active FROM team WHERE team_name = \"%s\";", teamName);
            jdbcTemplate.queryForObject(queryTeamActive, Boolean.class);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * This function inactivates a team
     * @param teamName
     * @return
     */
    public static boolean inactivateTeam(String teamName) {
        try {
            String query = String.format("UPDATE team SET is_active=\"%s\" WHERE team_name = \"%s\";", "false", teamName);
            jdbcTemplate.update(query);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * This function reactivates a team
     * @param teamName
     * @return
     */
    public static boolean reactivateTeam(String teamName) {
        try {
            String query = String.format("UPDATE team SET is_active=\"%s\" WHERE team_name = \"%s\";", "true", teamName);
            jdbcTemplate.update(query);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The fucntion returns true if the team exists, otherwise returns false
     * @param teamName
     * @return true or false
     */
    public static boolean isTeamExist(String teamName) {

        try {
            String queryTeamExist = String.format("SELECT team_name FROM team WHERE team_name = \"%s\";", teamName);
            jdbcTemplate.queryForObject(queryTeamExist, String.class);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function set coach to a team
     * @param coachUserName
     * @param teamName
     * @return
     */
    public static boolean setCoachToTeam(String coachUserName, String teamName) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        int coachId = UsersCRUD.getUserIdByUserName(coachUserName);

        //The user doesnt exist
        if(coachId == -1){
            return false;
        }

        try {
            String query = String.format("update team set coach_user_id = %d where team_name = \"%s\";", coachId, teamName);
            jdbcTemplate.update(query);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function remove coach to a team
     * @param coachUserName
     * @param teamName
     * @return
     */
    public static boolean removeCoachFromTeam(String coachUserName, String teamName) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        int coachId = UsersCRUD.getUserIdByUserName(coachUserName);

        //The user doesnt exist
        if(coachId == -1){
            return false;
        }

        try {
            String query = String.format("update team set coach_user_id = NULL where team_name = \"%s\";", teamName);
            jdbcTemplate.update(query);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function set manager to a team
     * @param managerUserName
     * @param teamName
     * @return
     */
    public static boolean setManagerToTeam(String managerUserName, String teamName) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        int managerId = UsersCRUD.getUserIdByUserName(managerUserName);

        //The user doesnt exist
        if(managerId == -1){
            return false;
        }

        try {
            String query = String.format("update team set manager_user_id = %d where team_name = \"%s\";", managerId, teamName);
            jdbcTemplate.update(query);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function remove manager to a team
     * @param managerUserName
     * @param teamName
     * @return
     */
    public static boolean removeManagerFromTeam(String managerUserName, String teamName) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        int managerId = UsersCRUD.getUserIdByUserName(managerUserName);

        //The user doesnt exist
        if(managerId == -1){
            return false;
        }

        try {
            String query = String.format("update team set manager_user_id = NULL where team_name = \"%s\";", teamName);
            jdbcTemplate.update(query);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function set owner to a team
     * @param ownerUserName
     * @param teamName
     * @return
     */
    public static boolean addOwnerToTeam(String ownerUserName, String teamName) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        int ownerId = UsersCRUD.getUserIdByUserName(ownerUserName);

        //The user doesnt exist
        if(ownerId == -1){
            return false;
        }

        try {
            String query = String.format("insert into team_owners values(\"%s\", %d);", teamName, ownerId);
            jdbcTemplate.update(query);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function remove owner to a team
     * @param ownerUserName
     * @param teamName
     * @return
     */
    public static boolean removeOwnerFromTeam(String ownerUserName, String teamName) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        int ownerId = UsersCRUD.getUserIdByUserName(ownerUserName);

        //The user doesnt exist
        if(ownerId == -1){
            return false;
        }

        try {
            String query = String.format("delete from team_owners where team_name = \"%s\" AND owner_user_id = %d;", teamName, ownerId);
            jdbcTemplate.update(query);
            return true;

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function adds facility to a team
     * @param homeTeamName
     * @param facilityName
     * @param type
     * @param location
     * @return
     */
    public static boolean addFacilityToTeam( String homeTeamName, String facilityName, String type, String location) {
        if (!isTeamExist(homeTeamName)) {
            return false;
        }

        try {
            String query = String.format("insert into facilities values(\"%s\", \"%s\", \"%s\", \"%s\");", homeTeamName, facilityName, type, location);
            jdbcTemplate.update(query);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The function remove facility to a team
     * @param homeTeamName
     * @param facilityName

     * @return
     */
    public static boolean removeFacilityFromTeam( String homeTeamName, String facilityName) {
        if (!isTeamExist(homeTeamName)) {
            return false;
        }

        try {
            String query = String.format("delete from facilities where home_team_name = \"%s\" AND facility_name = \"%s\";", homeTeamName, facilityName);
            jdbcTemplate.update(query);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The function sets the team activity
     * @param teamName
     * @param isActive
     * @return
     */
    public static boolean setTeamActivity( String teamName, boolean isActive) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        String activity = isActive ? "1" : "0";

        try {
            String query = String.format("update team set isActive = \"%s\" where team_name = \"%s\";", activity, teamName);
            jdbcTemplate.update(query);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The function sets the team registration status
     * @param teamName
     * @param isRegistered
     * @return
     */
    public static boolean setTeamRegistration(String teamName, boolean isRegistered) {
        if (!isTeamExist(teamName)) {
            return false;
        }

        String registration = isRegistered ? "1" : "0";

        try {
            String query = String.format("update team set isRegistered = \"%s\" where team_name = \"%s\";", registration, teamName);
            jdbcTemplate.update(query);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This function creates a team from DB
     * @param teamName
     * @return
     */
    public static Team getTeamByName(String teamName){
        if (!isTeamExist(teamName)) {
            return null;
        }
        try {

            Team team = new Team (teamName);
            //owner
            String sql = String.format("SELECT owner_user_id FROM team_owners WHERE team_name = \"%s\";", teamName);
            List<Integer> team_owners = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Integer.class));
            for (Integer id: team_owners){
                User user = UsersCRUD.getRegisteredUserByID(id); //return registered


                String username= ((Registered)user).getUserName();
                //check roles
                String sqlroles = String.format("SELECT user_role FROM user_role WHERE user_id = \"%d\";", id);
                List<String> roles = jdbcTemplate.query(sqlroles, new BeanPropertyRowMapper(String.class));
                boolean manager = false;
                /*build user*/
                for (String role: roles){
                    if (role.equals("PLAYER")) {
                        user = new Player(user, username);
                        ((Player) user).setTeam(team);
                    }
                    else if (role.equals("COACH")) {
                        user = new Coach(user, username);
                        ((Coach) user).setTeam(team);
                    }
                    if (role.equals("TEAM_MANAGER"))
                        manager = true;
                }
                if (manager) {
                    user = new TeamManager(user, username);
                    ((TeamManager) user).setTeam(team);
                }
                user= new TeamOwner(user, username);
                ((TeamOwner) user).setTeam(team);
                team.addTeamOwner((TeamOwner)user);
            }

            //players
            String sqlplayers = String.format("SELECT user_id FROM player WHERE team_name = \"%s\";", teamName);
            List<Integer> players = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Integer.class));
            for (Integer idp: players) {
                User player = UsersCRUD.getRegisteredUserByID(idp); //return registered
                String fullName = ((Registered) player).getFullName();
                player = new Player(player, fullName);
                ((Player) player).setTeam(team);
                team.addPlayerToTeam((Player) player);
            }

        }catch (Exception e) {
            return null;
        }
        return null;
    }

}
