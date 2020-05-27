/**
 * The class represents the api of the user in the sas application
 */
package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public String postUser(@RequestBody String credentials, HttpServletRequest request) {
        JSONObject json = new JSONObject(credentials);
        String username = json.get("username").toString();
        String password = json.get("password").toString();
        String clientURL = request.getRemoteAddr() + ":" + request.getRemotePort();
        String role = app.login(username, password, clientURL);
        return role.isEmpty() ? "Failed" : role;
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

    /**
     * The function receives username and password and returns response OK if the exists in the system,
     * otherwise returns false
     * @return the list
     */
    @PostMapping(value ="/exit")
    public String postExitUser(@RequestBody String credentials) {
        JSONObject json = new JSONObject(credentials);
        String username = json.get("username").toString();
        return app.exit(username) ? "OK" : "Failed";
    }

    /**
     * The function receives username and role and returns his details
     * @return JSONObject - details
     */
    @GetMapping(value ="/getUserDetails/{username}/{role}")
    public String getDetails(@PathVariable String username, @PathVariable String role) {
        JSONObject details = app.getUserDetails(username, role);
        return details != null ? details.toString() : "Failed";
    }


    /**
     * The function receives username and role and returns his details
     * @return status
     */
    @PostMapping(value ="/setUserDetails")
    public String setDetails(@RequestBody String userDetails) {
        JSONObject details = new JSONObject(userDetails);
        boolean result = app.setDetails(details);
        return result ? "OK" : "Failed";
    }


}
