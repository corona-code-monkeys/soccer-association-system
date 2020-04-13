package com.SAS.LeagueManagement;

import com.SAS.League.League;

import java.util.Scanner;

public class LeagueInitModel {
    public League initLeague() {
        System.out.println("Enter league name:");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String name = myObj.nextLine();
        return new League(name);
    }
}