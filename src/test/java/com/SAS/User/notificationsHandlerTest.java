package com.SAS.User;

import com.SAS.dbstub.dbStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class notificationsHandlerTest {

    private dbStub db;

    @BeforeEach
    void setUp() {
        db = new dbStub();
        db.initializeDB();
    }

    @Test
    void sendEmailToUser() {
        NotificationsHandler notificationsHandler = new NotificationsHandler();
        List<String> userNames = new LinkedList<>();
        userNames.add("yaa");
        userNames.add("Chen");
        notificationsHandler.sendEmailToUser(userNames, "Hi there", "this is so cool");
    }
}