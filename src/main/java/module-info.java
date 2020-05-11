module soccer.association.system {
    //javafx
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    requires java.sql;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.jdbc;
    requires log4j;
    requires spring.tx;
    requires org.apache.logging.log4j;
    requires org.jboss.logging;
    requires com.fasterxml.classmate;


    exports com.SAS.presentation;
    opens com.SAS.presentation;
}