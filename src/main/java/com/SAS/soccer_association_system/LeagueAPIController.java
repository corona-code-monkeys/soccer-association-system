/**
 * The class represents the api of team in the sas application
 */
package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value ="/league")
@RestController
public class LeagueAPIController {

    @Autowired
    private static SASApplication app = new SASApplication();

    /**
     * The function receives league name and returns response success if the league created,
     * otherwise returns false
     * @return String - success or fail
     */
    @PostMapping(value ="/createLeague")
    public String postLeague(@RequestBody String details) {
        JSONObject json = new JSONObject(details);
        String leagueName = json.get("leagueName").toString();
        String userCreated = json.get("user").toString();
        return app.createLeague(leagueName, userCreated) ? "success" : "fail";
    }

    /**
     * The function receives league name and the year of the season returns response success if the league attached to season,
     *      otherwise returns false
     * @param details
     * @return String - success or fail
     */
    @PostMapping(value ="/addSeasonToLeague")
    public String postSeasonToLeague(@RequestBody String details) {
        JSONObject json = new JSONObject(details);
        String leagueName = json.get("leagueName").toString();
        int seasonYear = (Integer)json.get("seasonName");
        return app.addSeasonToLeague(leagueName, seasonYear) ? "success" : "fail";
    }

    /**
     * gets the league, season and referee id and assign him to the league in the specific season
     * @param details
     * @return
     */
    @PostMapping(value ="/assignRefereeToLeague")
    public String postAssignRefereeToLeague(@RequestBody String details) {
        JSONObject json = new JSONObject(details);
        String leagueName = json.get("leagueName").toString();
        String seasonYear = json.get("seasonName").toString();
        String userId = json.get("userId").toString();
        return app.assignRefereeToLeague(leagueName, seasonYear, userId) ? "success" : "fail";
    }
    /**
     *gets the id of the wanted referee and remove it
     * @param details
     * @return
     */
    @PostMapping(value ="/removeReferee")
    public String postRemoveReferee(@RequestBody String details) {
        JSONObject json = new JSONObject(details);
        String userId = json.get("userId").toString();
        return app.deleteUser(userId) ? "success" : "fail";
    }
    /**
     *gets the league, season and policies and the user who wants to assign them and assign the policies to the league in a specific season
     * @param details
     * @return
     */
    @PostMapping(value ="/definePolicies")
    public String postDefinePolicies(@RequestBody String details) {
        JSONObject json = new JSONObject(details);
        String leagueName = json.get("leagueName").toString();
        int seasonYear = (Integer)json.get("seasonName");
        String gamePolicy = json.get("gamePolicy").toString();
        String rankPolicy = json.get("leagueRankPolicy").toString();
        String pointsPolicy = json.get("pointsPolicy").toString();
        String username= json.get("username").toString();
        return app.setPolicies(leagueName,seasonYear, rankPolicy,pointsPolicy,gamePolicy,username) ? "success" : "fail";
    }

    /**
     * The function returns all the teams
     * @return
     */
    @GetMapping(value = "/getLeagues")
    public JSONArray getLeagues() {
        return app.getLeagues();
    }
}
