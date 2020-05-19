package com.SAS.crudoperations;

import com.SAS.User.*;
import com.SAS.team.Team;
import com.SAS.User.FieldRole;
import com.SAS.usersDB.UsersDB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class UsersCRUD {

    private static JdbcTemplate jdbcTemplate;
    private static HashMap<String, Integer> roleOrder;

    public UsersCRUD() {
        this.roleOrder = new HashMap<>();
        initRolesOrder();
    }

    /**
     * This function initializes the role order hash map
     */
    private void initRolesOrder() {
        this.roleOrder.put("fan", 0);
        this.roleOrder.put("system_admin", 0);
        this.roleOrder.put("referee", 0);
        this.roleOrder.put("association_representative", 0);
        this.roleOrder.put("player", 1);
        this.roleOrder.put("coach", 1);
        this.roleOrder.put("team_manager", 2);
        this.roleOrder.put("team_owner", 3);
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
    public static boolean postUser(String userName, String password, String fullName, String email, String role) {
        if(!validParams(userName, password)){
            return false;
        }

        String encrypt = hashPasswords(password);

        try {
            String query = String.format("insert into user (user_name, password, full_name, email) values ( \"%s\", \"%s\", \"%s\", \"%s\");", userName, encrypt, fullName, email);
            jdbcTemplate.update(query);

            return addRoleToUser(userName, role);

        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * This function returns the user id by username
     * @param userName
     * @return
     */
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
            String roleInsert = String.format("insert into user_role (user_id, user_name, user_role) values ( \"%d\", \"%s\", \"%s\");", userId, userName, role.toUpperCase());
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
    public static boolean setPlayerDetails(int userID, String dateOfBirth, String fieldRole, String teamName) {
        if(inRoleTable(userID, "player")){
            String queryUpdate = String.format("UPDATE player SET date_of_birth=\"%s\", field_role=\"%s\", team_name=\"%s\" WHERE user_id = \"%d\";", dateOfBirth, fieldRole.toUpperCase(), teamName, userID);
            try{
                jdbcTemplate.update(queryUpdate);
                return true;
            } catch (Exception e){
                return false;
            }
        }else{
            try{
                String playerToInsert = String.format("INSERT into player (user_id, date_of_birth, field_role) values ( \"%d\", \"%s\", \"%s\");", userID, dateOfBirth, fieldRole.toLowerCase());
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

    /**
     * This function builds and returns a registered user from userid
     * @param id
     * @return Registered
     */
    public static User getRegisteredUserByID(int id) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new Registered(
                            rs.getString("user_name"),
                            rs.getString("password"),
                            rs.getString("full_name"),
                            rs.getString("email")
                    ));
            return user;
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * This function returns the user name by id
     * @param id
     * @return username
     */
    public static String getUserNameById(int id){
        try {
            String queryUserId = String.format("SELECT user_name FROM user WHERE user_id = \"%d\";", id);
            return jdbcTemplate.queryForObject(queryUserId, String.class);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * The function receives a userName and returns the full name of the user
     * @param userName
     * @return
     */
    public static String getFullNameByUserName(String userName) {
        try {
            String queryUserfullname = String.format("SELECT full_name FROM user WHERE user_name = \"%s\";", userName);
            return jdbcTemplate.queryForObject(queryUserfullname, String.class);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * The function receives a userName and returns the email of the user
     * @param userName
     * @return
     */
    public static String getEmailByUserName(String userName) {
        try {
            String queryUserEmail = String.format("SELECT email FROM user WHERE user_name = \"%s\";", userName);
            return jdbcTemplate.queryForObject(queryUserEmail, String.class);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * This function restore the user from db
     * @param id
     * @return
     */
    public static User restoreRoleForUser(int id) {
        User user = UsersCRUD.getRegisteredUserByID(id); //return registered
        String username= ((Registered)user).getUserName();
        //check roles
        try {
            String sqlroles = String.format("SELECT user_role FROM user_role WHERE user_id = \"%d\";", id);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlroles);
            List<String> roles = new LinkedList<>();
            for(Map<String, Object> row: rows){
                roles.add((String)row.get("user_role"));
            }
            /*build user*/
            String role = roles.get(0);
            if (roles.size() == 1 && roleOrder.get(role) == 0){
                return createRoleSimple(id, user, username, role);
            }
            else {
                return createRoleComplex(id, user, username, roles);
            }
        }catch (Exception e){
            return null;
        }
    }

    private static User createRoleComplex(int id, User user, String username, List<String> roles) {
        String sql;
        Map<String, Object> result;

        for (String role : roles) {
            if (roleOrder.get(role) == 1) {
                switch (role) {
                    case "player":
                        user = new Player(user, username);
                        sql = "SELECT team_name, date_of_birth,field_role FROM player WHERE user_id = ?";
                        result = jdbcTemplate.queryForMap(sql, new Object[]{id});
                        ((Player) user).setDateOfBirth(LocalDate.parse(result.get("date_of_birth").toString()));
                        String fieldRole = (String)result.get("field_role");
                        ((Player) user).setFieldRole(Role.convertStringToFieldRole(fieldRole.substring(0,1) + fieldRole.substring(1).toLowerCase()));
                        ((Player) user).setTeam(new Team((String)result.get("team_name")));
                        return user;
                    case "coach":
                        user = new Coach(user, username);
                        sql = "SELECT team_name, level, field_role FROM coach WHERE user_id = ?";
                        result = jdbcTemplate.queryForMap(sql, new Object[]{id});
                        ((Coach) user).setLevel(Integer.parseInt(result.get("date_of_birth").toString()));
                        ((Coach) user).setFieldRole(Role.convertStringToFieldRole((String)result.get("field_role")));
                        ((Coach) user).setTeam(new Team((String)result.get("team_name")));
                        return user;
                    default:
                        return null;
                }
            }
        }

        if (roles.contains("team_manager")) {
            user = new TeamManager(user, username);
            sql = String.format("SELECT team_name FROM team_manager WHERE user_id = \"%d\";", id);
            ((TeamManager) user).setTeam(new Team(jdbcTemplate.queryForObject(sql, String.class)));
        }

        if (roles.contains("team_owner")) {
            user = new TeamOwner(user, username);
            sql = String.format("SELECT team_name FROM team_owner WHERE ser_id = \"%d\";", id);
            ((TeamOwner) user).setTeam(new Team(jdbcTemplate.queryForObject(sql, String.class)));
        }

        return user;
    }


    /**
     * This function creates a simple role user
     * @param id
     * @param user
     * @param username
     * @param role
     * @return
     */
    private static User createRoleSimple(int id, User user, String username, String role) {
        switch(role){
            case "fan":
                return new Fan(user, username);

            case "referee":
                user= new Referee(user, username);
                String refereelevel = String.format("SELECT level FROM referee WHERE user_id = \"%d\";", id);
                ((Referee)user).setLevel(jdbcTemplate.queryForObject(refereelevel, Integer.class));
                return user;

            case "system_admin":
               return new SystemAdmin(user, username);

            case "association_representative":
              return new AssociationRepresentative(user, username);

              default: return null;
        }
    }

}
