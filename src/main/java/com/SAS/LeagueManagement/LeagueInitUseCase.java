package com.SAS.LeagueManagement;

import com.SAS.League.League;

import java.util.Scanner;

public class LeagueInitUseCase {
    public League initLeague(String name) {
        League league= new League(name);
        return league;
    }
}