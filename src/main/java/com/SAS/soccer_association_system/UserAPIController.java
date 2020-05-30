/**
 * The class represents the api of the user in the sas application
 */
package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import com.SAS.crudoperations.UsersCRUD;
import com.SAS.systemLoggers.LoggerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/users")
@RestController
public class UserAPIController {

    @Autowired
    private static SASApplication app = new SASApplication();
    private LoggerFactory logger = LoggerFactory.getInstance();


    /**
     * The function receives username and password and returns response OK if the exists in the system,
     * otherwise returns false
     *
     * @return the list
     */
    @PostMapping(value = "/login")
    public String postUser(@RequestBody String credentials, HttpServletRequest request) {
        JSONObject json = new JSONObject(credentials);
        String username = json.get("username").toString();
        String password = json.get("password").toString();
        String clientAddress = request.getRemoteAddr();
        String role = app.login(username, password, clientAddress);
        if (!role.isEmpty()) {
            JSONObject toReturn = new JSONObject();
            toReturn.put("role", role);
            String userId = UsersCRUD.getUserIdByUserName(username) + "";
            toReturn.put("user_id", userId);
            return toReturn.toString();
        } else {

            return "Failed";
        }
    }

    /**
     * The function receives username, password, full name,
     * and returns response OK if the exists in the system,
     * otherwise returns false
     *
     * @return the list
     */
    @PostMapping(value = "/register")
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
     *
     * @return the list
     */
    @PostMapping(value = "/exit")
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

    /**
     * The function receives an address of a client and a message and sends it to the client
     * @param clientAddress
     * @param message
     */
    public void sendNotification(String clientAddress, String message) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String url = "http://" + clientAddress + ":8888/getNotification";
            HttpPost request = new HttpPost(url);
            JSONObject json = new JSONObject();
            json.put("message", message);

            //create the request
            StringEntity stringEntity = new StringEntity(json.toString());
            request.getRequestLine();
            request.setEntity(stringEntity);
            request.addHeader("Content-Type", "application/json");

            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    if (result.equals("sent")) {
                        logger.logEvent("Notification sent to user with the message: " + message);
                    }

                    else {
                        logger.logError("Notification didn't send, there was an error");
                    }
                }

            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
