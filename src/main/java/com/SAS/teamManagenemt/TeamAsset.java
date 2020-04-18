package com.SAS.teamManagenemt;

import com.SAS.team.Team;

import java.util.List;

public interface TeamAsset {

    void editDetails(List<String> details);
    void setTeam(Team team);
    void removeAssetFromTeam();

}
