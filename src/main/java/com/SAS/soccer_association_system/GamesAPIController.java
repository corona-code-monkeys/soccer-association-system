/**
 * The class represents the api of game in the sas application
 */
package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import com.SAS.crudoperations.GameCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = "/games")
@RestController
public class GamesAPIController {

    @Autowired
    private static SASApplication app = new SASApplication();

    @GetMapping(value = "/get")
    public String getAllGames() {
        return GameCRUD.getAllGames().toString();
    }
}
