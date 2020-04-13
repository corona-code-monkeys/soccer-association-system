package com.SAS.LeagueManagement;


import com.SAS.League.League;
import com.SAS.League.Season;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class LeagueInitTest {
    @Test
    public void initLeague() {
        String name = "Ligat Ha'al";
        LeagueInitUseCase LeagueInitUseCase = new LeagueInitUseCase();
        League league = LeagueInitUseCase.initLeague(name);
        try {
            Assertions.assertTrue(league.getName().equals(name));
        } catch (AssertionError e) {
            System.out.println("The name of the league (" + league.getName() + ") is not equal to the name inserted:(" + name + ")");
            throw e;
        }
        System.out.println("The name of the league (" + league.getName() + ") is equal to the name inserted:(" + name + ")");

        try {
            Assertions.assertTrue(league instanceof League);
        } catch (AssertionError e) {
            System.out.println("The instance created isn't from the type of league");
            throw e;
        }
        System.out.println("The instance created is from the type of league");
        try {
            Assertions.assertFalse(league.getName().equals("asdasd"));
        } catch (AssertionError e) {
            System.out.println("The name of the league (" + league.getName() + ") is equal to the wrong name inserted:(asdasd)");
            throw e;
        }
        System.out.println("The name of the league (" + league.getName() + ") is not equal to the wrong name inserted:(asdasd)");
    }

}