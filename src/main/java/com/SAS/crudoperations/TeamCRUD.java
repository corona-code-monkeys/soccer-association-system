package com.SAS.crudoperations;

import org.springframework.jdbc.core.JdbcTemplate;

public class TeamCRUD {

    private static JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
