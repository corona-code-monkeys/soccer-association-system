package com.SAS.crudoperations;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.PushBuilder;
import java.util.LinkedList;
import java.util.List;

public class SystemCRUD {

    private static JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * The function returns all the available systems from the db
     * @return
     */
    public static List<String> getAllExternalSystems() {

        List<String> externalSystems = new LinkedList<>();

        String query = String.format("SELECT system_name FROM external_systems;");

        try {
            externalSystems.addAll(jdbcTemplate.query(query, (rs, rowNum) -> (rs.getString("system_name"))));
        } catch (Exception e) {
        }

        return externalSystems;
    }

    /**
     * The function adds the new system into the db
     * @param systemName
     * @return
     */
    public static boolean connectToSystem(String systemName) {
        try {

            String query = String.format("insert into external_systems_connected () values ( \"%s\");", systemName);
            jdbcTemplate.update(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The function returns true if our system is already connect to this external system
     * @param systemName
     * @return
     */
    public static boolean isSystemConnected(String systemName) {
        try {
            String query = String.format("SELECT system_name FROM external_systems_connected WHERE system_name = \"%s\";", systemName);
            jdbcTemplate.queryForObject(query, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String isSystemInitiate(){
        try {
            String query = String.format("SELECT isActive FROM isActive;");
            String res = jdbcTemplate.queryForObject(query, String.class);
            if(res.equals("1")){
                return "true";
            }

            return "false";

        } catch (Exception e) {
            return "false";
        }
    }

    public static boolean removeSystem(String systemName) {
        try {
            String delete = String.format("delete from external_systems_connected where system_name = \"%s\";", systemName);
            jdbcTemplate.update(delete);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
