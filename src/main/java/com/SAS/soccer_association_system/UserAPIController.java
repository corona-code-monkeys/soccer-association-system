/**
 * The class represents the api of the sas application
 */
package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value ="/users")
@RestController
public class UserAPIController {

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
        return app.login(username, password) ? "OK" : "Failed";
    }

}
