package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import com.SAS.crudoperations.SystemCRUD;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SystemAPIController {

    @Autowired
    private static SASApplication app = new SASApplication();

    @GetMapping(value="/system/system_check")
    public String isSystemInit() {
        return SystemCRUD.isSystemInitiate();
    }

    @GetMapping(value="/system/external_systems")
    public String getExternalSystems() {
        List<String> systems =  SystemCRUD.getAllExternalSystems();
        String strSystems = new Gson().toJson(systems);
        return strSystems;
    }

    @PostMapping(value ="/system/add_system")
    public boolean postUser(@RequestBody String system) {
        JSONObject json = new JSONObject(system);
        String systemName = json.get("name").toString();
        return SystemCRUD.connectToSystem(systemName);
    }
}
