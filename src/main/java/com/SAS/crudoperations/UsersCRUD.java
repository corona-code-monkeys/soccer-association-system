package com.SAS.crudoperations;

import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;

public class UsersCRUD {

    private static JdbcTemplate jdbcTemplate;


    public UsersCRUD() {
    }


    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * The function receives userName and password and insert the user to our DB
     * @param userName
     * @param password
     * @return
     */
    public static boolean postUser(String userName, String password, String fullName, String role) {
        if(!validParams(userName, password)){
            return false;
        }

        String encrypt = hashPasswords(password);

        try {
            String query = String.format("insert into user (user_name, password, full_name) values ( \"%s\", \"%s\", \"%s\");", userName, encrypt, fullName);
            jdbcTemplate.update(query);

            return addRoleToUser(userName, role);

        }
        catch (Exception e){
            return false;
        }
    }

    public static int getUserIdByUserName(String userName){
        try {
            String queryUserId = String.format("SELECT user_id FROM user WHERE user_name = \"%s\";", userName);
            return jdbcTemplate.queryForObject(queryUserId, Integer.class);
        }
        catch (Exception e){
            return -1;
        }
    }

    public static boolean addRoleToUser(String userName, String role){
        int userId = getUserIdByUserName(userName);
        if(userId == -1){
            return false;
        }
        try {
            String roleInsert = String.format("insert into user_role (user_id, user_name, user_role) values ( \"%d\", \"%s\", \"%s\");", userId, userName, role.toLowerCase());
            jdbcTemplate.update(roleInsert);
        }
        catch (Exception e){
            return false;
        }

        return true;
    }


    /**
     * The function returns true if the parameters are valid
     * @param userName
     * @param password
     * @return
     */
    private static boolean validParams(String userName, String password) {
        return userName != null && password != null && !userName.trim().isEmpty() && !password.trim().isEmpty();
    }

    /**
     * The function receives userName and password and checks if the userName and password are valid
     * @param userName
     * @param password
     * @return
     */
    public static boolean isUserValid(String userName, String password) {
        if(!validParams(userName, password)) {
            return false;
        }

        String encrypt = hashPasswords(password);

        String queryUserId = String.format("SELECT user_id FROM user WHERE user_name = \"%s\" AND password = \"%s\";", userName, encrypt);
        try {
            jdbcTemplate.queryForObject(queryUserId, Integer.class);
            return  true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function receives userName and password and delete the user from our DB
     * @param userName
     * @return
     */
    public static boolean deleteUser(String userName) {
        if(userName == null || userName.trim().isEmpty()) {
            return false;
        }

        try {
            int userId = getUserIdByUserName(userName);
            String delete = String.format("delete from user where user_id = \"%d\";", userId);
            jdbcTemplate.update(delete);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * This function deletes the user from the role table
     * @param userName
     * @param role
     * @return
     */
    public static boolean deleteRole(String userName, String role) {
        if(userName == null || userName.trim().isEmpty()) {
            return false;
        }
        try {
            int userId = getUserIdByUserName(userName);
            String delete = String.format("delete from user_role where user_role = \"%s\" user_id = \"%d\";", role.toUpperCase(), userId);
            jdbcTemplate.update(delete);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * The function receives a password and encrypt it using sha1 algorithm
     * @param password
     * @return
     */
    private static String hashPasswords(String password){
        String encryptPass = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes("utf8"));
            encryptPass = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return encryptPass;
    }

    /**
     * This function sets the player details in the DB
     * @param userID
     * @param dateOfBirth
     * @param fieldRole
     * @return
     */
    public static boolean setPlayerDetails(int userID, String dateOfBirth, String fieldRole) {
        if(inRoleTable(userID, "player")){
            String queryUpdate = String.format("UPDATE player SET date_of_birth=\"%s\", field_role=\"%s\" WHERE user_id = \"%d\";", dateOfBirth, fieldRole.toUpperCase(), userID);
            try{
                jdbcTemplate.update(queryUpdate);
                return true;
            } catch (Exception e){
                return false;
            }
        }else{
            try{
                String playerToInsert = String.format("INSERT into player (user_id, date_of_birth, field_role) values ( \"%d\", \"%s\", \"%s\");", userID, dateOfBirth, fieldRole.toUpperCase());
                jdbcTemplate.update(playerToInsert);
                return true;
            } catch (Exception e){
                return false;
            }
        }
    }

    /**
     * This function checks if the user is in the role table
     * @param userID
     * @param role
     * @return
     */
    private static boolean inRoleTable(int userID, String role) {
        String queryUserRole = String.format("SELECT user_id FROM %s WHERE user_id = %d;", role, userID);
        try {
            jdbcTemplate.queryForObject(queryUserRole, Integer.class);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * This function edits the team ownerdetails in the DB- including nominated by
     * @param userID
     * @param teamName
     * @return
     */
    public static boolean setTeamOwnerDetails(int userID, String teamName) {
        if(!inRoleTable(userID, "team_owner")){
            try{
                String teamOwnerToInsert = String.format("INSERT into team_owner (user_id, team_name) values ( \"%d\", \"%s\");", userID, teamName);
                jdbcTemplate.update(teamOwnerToInsert);
                return true;
            } catch (Exception e){
                return false;
            }
        }
        return false;
    }

    /**
     * This function edits the team owner or manager details in the DB- including nominated by
     * @param userID
     * @param teamName
     * @param nominatedBy
     * @param role
     * @return
     */
    public static boolean setTeamOwnerOrManagerDetails(int userID, String teamName, String nominatedBy, String role) {
        int nominatedByID = getUserIdByUserName(nominatedBy);
        if(!inRoleTable(userID, role)){
            try{
                String teamOwnerToInsert = String.format("INSERT into %s (user_id, team_name, nominated_by) values ( \"%d\", \"%s\", \"%s\");", role, userID, teamName, nominatedByID);
                jdbcTemplate.update(teamOwnerToInsert);
                return true;
            } catch (Exception e){
                return false;
            }
        }
        return false;
    }

    /**
     * This function sets the coach details in the DB
     * @param userID
     * @param level
     * @param fieldRole
     * @param teamName
     * @return
     */
    public static boolean setCoachDetails(int userID, String level, String fieldRole, String teamName) {
        if(inRoleTable(userID, "coach")) {
            //edit level and field role only
            String queryUpdate = String.format("UPDATE coach SET level= \"%d\", field_role=\"%s\", WHERE user_id = \"%d\";", Integer.parseInt(level), fieldRole.toUpperCase(), userID);
            try{
                jdbcTemplate.update(queryUpdate);
                return true;
            } catch (Exception e){
                return false;
            }
        }else{
            try{
                String coachToInsert = String.format("INSERT into coach (user_id, level, field_role, team_name) values ( \"%d\", \"%d\", \"%s\", \"%s\");", userID, Integer.parseInt(level), fieldRole.toUpperCase(), teamName);
                jdbcTemplate.update(coachToInsert);
                return true;
            } catch (Exception e){
                return false;
            }
        }
    }

    /**
     * This function sets the referee details in the DB
     * @param userID
     * @param level
     * @return
     */
    public static boolean setRefereeDetails(int userID, String level) {
        if(inRoleTable(userID, "referee")) {
            //edit level and field role only
            String queryUpdate = String.format("UPDATE referee SET level=%d WHERE user_id = %d;", Integer.parseInt(level),userID);
            try{
                jdbcTemplate.update(queryUpdate);
                return true;
            } catch (Exception e){
                return false;
            }
        }else{
            try{
                String refereeToInsert = String.format("INSERT into referee (user_id, level) values ( \"%d\", \"%d\");", userID, Integer.parseInt(level));
                jdbcTemplate.update(refereeToInsert);
                return true;
            } catch (Exception e){
                return false;
            }
        }
    }

}
