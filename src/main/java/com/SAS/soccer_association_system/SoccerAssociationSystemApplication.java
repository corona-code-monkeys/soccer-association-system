package com.SAS.soccer_association_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SoccerAssociationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoccerAssociationSystemApplication.class, args);
    }

}