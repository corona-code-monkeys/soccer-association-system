package com.SAS.domain.teamManagenemt;

import com.SAS.domain.team.Team;

import java.util.List;

public interface TeamAsset {

    boolean editDetails(List<String> details);
    boolean setTeam(Team team);
    void removeAssetFromTeam();
    String toString();

}
