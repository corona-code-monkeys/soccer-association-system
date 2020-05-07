package com.SAS.crudoperations;

import com.SAS.usersDB.UsersDB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.PushBuilder;
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


}
