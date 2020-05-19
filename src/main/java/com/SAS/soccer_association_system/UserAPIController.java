/**
 * The class represents the api of the sas application
 */
package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value ="/users")
@RestController
public class UserAPIController {

    @Autowired
    private static SASApplication app = new SASApplication();


    /**
     * The function receives username and password and returns response OK if the exists in the system,
     * otherwise returns false
     * @return the list
     */
    @PostMapping(value ="/login")
    public String postUser(@RequestBody String credentials) {
        JSONObject json = new JSONObject(credentials);
        String username = json.get("username").toString();
        String password = json.get("password").toString();
        return app.login(username, password) ? "Found" : "NotFound";
    }

    /**
     * The function receives username, password, full name,
     * and returns response OK if the exists in the system,
     * otherwise returns false
     * @return the list
     */
    @PostMapping(value ="/register")
    public String postUserRegister(@RequestBody String credentials) {
        JSONObject json = new JSONObject(credentials);
        String username = json.get("username").toString();
        String password = json.get("password").toString();
        String fullName = json.get("full name").toString();
        String email = json.get("email").toString();
        String occupation = json.get("occupation").toString();
        return app.createUser(username, password, fullName, email, occupation, true) ? "Created" : "Error";
    }


}
