package com.SAS.teamManagenemt;

import com.SAS.team.Team;
import org.json.JSONObject;

import java.util.List;

public interface TeamAsset {

    boolean editDetails(JSONObject details);
    boolean setTeam(Team team);
    void removeAssetFromTeam();
    String toString();
    String type();

}
