package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import com.SAS.Controllers.systemController.ApplicationController;
import com.SAS.crudoperations.SystemCRUD;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The class is the rest api controller for all the system routes
 */
@RestController
public class SystemAPIController {

    private static ApplicationController applicationController = new ApplicationController();

    @Autowired
    private static SASApplication app = new SASApplication();


    /**
     * This route check if the system has been Initiated already
     * @return
     */
    @GetMapping(value="/system/system_check")
    public String isSystemInit() {
        return SystemCRUD.isSystemInitiate();
    }

    /**
     * This route returns the external system our system can connect to
     * @return
     */
    @GetMapping(value="/system/external_systems")
    public String getExternalSystems() {
        List<String> systems =  applicationController.showAvailableExternalSystem();
        String strSystems = new Gson().toJson(systems);
        return strSystems;
    }

    /**
     * This route activate the system at the end of the installation process
     * @param value
     * @return
     */
    @PostMapping(value ="/system/activate_system")
    public boolean activateSystem(@RequestBody String value) {
        JSONObject json = new JSONObject(value);
        String systemStatus = json.get("system_status").toString();
        return applicationController.activateSystem();
    }

    /**
     * This route add external system
     * @param system
     * @return
     */
    @PostMapping(value ="/system/add_system")
    public boolean addExternalSystems(@RequestBody String system) {
        JSONObject json = new JSONObject(system);
        String systemName = json.get("name").toString();
        return applicationController.addExternalSystem(systemName);
    }

    /**
     * This route is for create the first admin user during installation
     * @param adminDetails
     * @return
     */
    @PostMapping(value ="/system/add_system_admin")
    public boolean addSystemAdmin(@RequestBody String adminDetails) {
        JSONObject json = new JSONObject(adminDetails);
        String fullName = json.get("fullName").toString();
        String userName = json.get("userName").toString();
        String password = json.get("password").toString();
        String email = json.get("email").toString();
        return applicationController.createSystemAdmin(userName, password, fullName, email);
    }
}
