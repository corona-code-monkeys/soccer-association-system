package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeagueManagementControllerTest extends LeagueManagementController {

    @Test
    void initLeague() {
        {
            String name = "Ligat Ha'al";
            LeagueManagementController LeagueInitUseCase = new LeagueManagementController();
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

//    @Test
//    void addSeasonToALeague() {
//        {
//            String name = "Ligat Ha'al";
//            int year = 2020;
//            LeagueManagementController DefineSeasonForLeagueUseCase = new LeagueManagementController();
//            int [] seasonAndLeague= DefineSeasonForLeagueUseCase.addSeasonToALeague(name, year);
//            int season=seasonAndLeague[0];
//            int league=seasonAndLeague[1];
//            try {
//                Assertions.assertTrue(isLeagueScoreExist(league,season));
//            } catch (AssertionError e) {
//                System.out.println("There is no season exists in this league");
//                throw e;
//            }
//            System.out.println("There is at least one season in this league");
//
//            boolean isSeasonExist = false;
//            for (Season s : league.getSeasonList()) {
//                if (s.getYear() == year) {
//                    isSeasonExist = true;
//                }
//            }
//            try {
//                Assertions.assertTrue(isSeasonExist);
//            } catch (AssertionError e) {
//                System.out.println("The season of the year " + year + " is not exist in the league " + league.getName());
//                throw e;
//            }
//            System.out.println("The season of the year" + year + " exist in the league " + league.getName());
//
//        }
//    }
//
//    @Test
//    void assignAndRemoveRefereesFromLeague() {
//
//    }
}
