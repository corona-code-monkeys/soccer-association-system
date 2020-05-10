package com.SAS.crudoperations;

import org.springframework.jdbc.core.JdbcTemplate;

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
    public static boolean setTeamRegistration( String teamName, boolean isRegistered) {
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
}
