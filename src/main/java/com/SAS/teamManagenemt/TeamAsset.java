package com.SAS.teamManagenemt;

import com.SAS.team.Team;

import java.util.List;

public interface TeamAsset {

    boolean editDetails(List<String> details);
    boolean setTeam(Team team);
    void removeAssetFromTeam();
    String toString();

}
