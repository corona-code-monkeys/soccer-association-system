package com.SAS.presentation;

import javafx.application.Application;
//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, XADataSourceAutoConfiguration.class})
public class SoccerAssociationSystemApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SoccerAssociationSystemApplication.class, args);
        Application.launch(UserApplication.class, args);
    }

//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/sample.fxml"));
//        Parent root = loader.load();
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

}
