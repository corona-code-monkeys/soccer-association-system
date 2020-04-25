package com.SAS.crudoperations;

import com.SAS.usersDB.UsersDB;
import java.math.BigInteger;
import java.security.MessageDigest;

public class UsersCRUD {

    private static UsersDB db = UsersDB.getInstance();


    /**
     * The function receives userName and password and insert the user to our DB
     * @param userName
     * @param password
     * @return
     */
    public static boolean postUser(String userName, String password) {
        if(!validParams(userName, password)){
            return false;
        }

        String encrypt = hashPasswords(password);
        return db.postUser(userName, encrypt);

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
        return db.isUserValid(userName, encrypt);
    }

    /**
     * The function receives userName and password and delete the user from our DB
     * @param userName
     * @param password
     * @return
     */
    public static boolean deleteUser(String userName, String password) {
        if(!validParams(userName, password)) {
            return false;
        }

        String encrypt = hashPasswords(password);
        return db.deleteUser(userName, encrypt);

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
