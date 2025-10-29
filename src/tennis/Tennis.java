/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tennis;
import java.time.LocalDate;

/**
 *
 * @author tina-
 */
public class Tennis {

    /**
     * @param args the command line arguments
     * 
     */
    
    

/**
 * Test examples demonstrating the tennis tournament system functionality.
 * Contains various test scenarios for individual components and full tournament simulation.
 */
public class TestExamples {
    
    /**
     * Test 1: Create and display person objects
     */
    public static void testPersonCreation() {
        System.out.println("\n=== TEST 1: Person Creation ===");
        
        try {
            Person person1 = new Person("Smith", "John", 
                                       LocalDate.of(1990, 5, 15),
                                       "London", "UK", 180, 75);
            
            System.out.println(person1);
            System.out.println("Age: " + person1.getAge());
            System.out.println("Is Alive: " + person1.isAlive());
            
            person1.setNickname("Johnny");
            System.out.println("With nickname: " + person1);
            
            System.out.println("✓ Person creation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test 2: Create players with different attributes
     */
    public static void testPlayerCreation() {
        System.out.println("\n=== TEST 2: Player Creation ===");
        
        try {
            Player menPlayer = new Player("Federer", "Roger",
                                         LocalDate.of(1981, 8, 8),
                                         "Basel", "Switzerland", 185, 85,
                                         Player.Hand.RIGHT, Player.Gender.MALE, 1);
            
            Player womenPlayer = new Player("Williams", "Serena",
                                           LocalDate.of(1981, 9, 26),
                                           "Saginaw", "USA", 175, 70,
                                           Player.Hand.RIGHT, Player.Gender.FEMALE, 1);
            
            System.out.println("Men's Player: " + menPlayer);
            System.out.println("Sets to win: " + menPlayer.getSetsToWin());
            
            System.out.println("\nWomen's Player: " + womenPlayer);
            System.out.println("Sets to win: " + womenPlayer.getSetsToWin());
            
            // Test attire change
            menPlayer.changeAttireColor("blue");
            womenPlayer.changeAttireColor("pink");
            
            System.out.println("✓ Player creation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test 3: Create referee and test announcements
     */
    public static void testRefereeCreation() {
        System.out.println("\n=== TEST 3: Referee Creation ===");
        
        try {
            Referee referee = new Referee("Ramos", "Carlos",
                                         LocalDate.of(1971, 1, 21),
                                         "Portugal", "Portuguese", 188, 80, 20);
            
            System.out.println(referee);
            System.out.println("Reputation: " + referee.getReputation());
            
            // Test announcements
            referee.announceMatchStart("Player A", "Player B");
            referee.announceScore("Player A", 2, "Player B", 1);
            referee.announceAce();
            referee.announceFault("Net");
            
            System.out.println("✓ Referee creation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /*
     * Test 4: Create spectators with different features
     */
    public static void testSpectatorCreation() {
        System.out.println("\n=== TEST 4: Spectator Creation ===");
        
        try {
            Spectator maleSpectator = new Spectator("Johnson", "Mike",
                                                    LocalDate.of(1985, 3, 10),
                                                    "New York", "USA", 175, 80,
                                                    Spectator.Gender.MALE, 101,
                                                    150.0, "Semifinals");
            
            Spectator femaleSpectator = new Spectator("Taylor", "Emma",
                                                      LocalDate.of(1990, 7, 20),
                                                      "Paris", "France", 165, 60,
                                                      Spectator.Gender.FEMALE, 102,
                                                      150.0, "Semifinals");
            
            System.out.println(maleSpectator.getDistinctiveDescription());
            System.out.println(femaleSpectator.getDistinctiveDescription());
            
            // Test reactions
            maleSpectator.reactToAction(0.9);  // Exciting action
            femaleSpectator.reactToAction(0.9);
            
            System.out.println("✓ Spectator creation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /*
     * Test 5: Simulate a single rally
     */
    public static void testRally() {
        System.out.println("\n=== TEST 5: Rally Simulation ===");
        
        try {
            Player server = new Player("Nadal", "Rafael",
                                      LocalDate.of(1986, 6, 3),
                                      "Manacor", "Spain", 185, 85,
                                      Player.Hand.LEFT, Player.Gender.MALE, 2);
            
            Player receiver = new Player("Djokovic", "Novak",
                                        LocalDate.of(1987, 5, 22),
                                        "Belgrade", "Serbia", 188, 80,
                                        Player.Hand.RIGHT, Player.Gender.MALE, 1);
            
            Referee referee = new Referee("Hughes", "Alison",
                                         LocalDate.of(1975, 5, 10),
                                         "UK", "British", 170, 65, 15);
            
            Rally rally = new Rally(server, receiver);
            Rally.Outcome outcome = rally.play(referee);
            
            System.out.println(rally);
            System.out.println("Outcome: " + outcome);
            System.out.println("Winner: " + rally.getWinner().getFullName());
            
            System.out.println("✓ Rally simulation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /*
     * Test 6: Simulate a complete game
     */
    public static void testGame() {
        System.out.println("\n=== TEST 6: Game Simulation ===");
        
        try {
            Player player1 = new Player("Murray", "Andy",
                                       LocalDate.of(1987, 5, 15),
                                       "Glasgow", "UK", 190, 84,
                                       Player.Hand.RIGHT, Player.Gender.MALE, 3);
            
            Player player2 = new Player("Wawrinka", "Stan",
                                       LocalDate.of(1985, 3, 28),
                                       "Lausanne", "Switzerland", 183, 81,
                                       Player.Hand.RIGHT, Player.Gender.MALE, 4);
            
            Referee referee = new Referee("Lahyani", "Mohamed",
                                         LocalDate.of(1966, 6, 27),
                                         "Sweden", "Swedish", 175, 75, 25);
            
            Game game = new Game(player1, player2, player1, referee);
            game.play();
            
            System.out.println("\nGame Result: " + game);
            System.out.println("Winner: " + game.getWinner().getFullName());
            
            System.out.println("✓ Game simulation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /*
     * Test 7: Simulate a complete set
     */
    public static void testSet() {
        System.out.println("\n=== TEST 7: Set Simulation ===");
        
        try {
            Player player1 = new Player("Thiem", "Dominic",
                                       LocalDate.of(1993, 9, 3),
                                       "Wiener Neustadt", "Austria", 185, 79,
                                       Player.Hand.RIGHT, Player.Gender.MALE, 5);
            
            Player player2 = new Player("Zverev", "Alexander",
                                       LocalDate.of(1997, 4, 20),
                                       "Hamburg", "Germany", 198, 90,
                                       Player.Hand.RIGHT, Player.Gender.MALE, 6);
            
            Referee referee = new Referee("Keothavong", "James",
                                         LocalDate.of(1978, 8, 15),
                                         "UK", "British", 180, 78, 12);
            
            Set set = new Set(player1, player2, player1, referee, false);
            set.play();
            
            System.out.println("\nSet Result: " + set);
            System.out.println("Score: " + set.getScoreString());
            System.out.println("Winner: " + set.getWinner().getFullName());
            
            System.out.println("✓ Set simulation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test 8: Simulate a complete match
     */
    public static void testMatch() {
        System.out.println("\n=== TEST 8: Match Simulation ===");
        
        try {
            Player player1 = new Player("Federer", "Roger",
                                       LocalDate.of(1981, 8, 8),
                                       "Basel", "Switzerland", 185, 85,
                                       Player.Hand.RIGHT, Player.Gender.MALE, 1);
            
            Player player2 = new Player("Nadal", "Rafael",
                                       LocalDate.of(1986, 6, 3),
                                       "Manacor", "Spain", 185, 85,
                                       Player.Hand.LEFT, Player.Gender.MALE, 2);
            
            Referee referee = new Referee("Ramos", "Carlos",
                                         LocalDate.of(1971, 1, 21),
                                         "Portugal", "Portuguese", 188, 80, 20);
            
            Match match = new Match(player1, player2, referee,
                                   Match.Category.MENS_SINGLES,
                                   "Final", 2000);
            
            match.play();
            
            System.out.println("\n" + match.getMatchSummary());
            System.out.println(player1.getStatsSummary());
            System.out.println(player2.getStatsSummary());
            
            System.out.println("✓ Match simulation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test 9: Create and initialize a tournament
     */
    public static void testTournamentCreation() {
        System.out.println("\n=== TEST 9: Tournament Creation ===");
        
        try {
            Tournament tournament = new Tournament(
                Tournament.GrandSlam.WIMBLEDON, 2024);
            
            System.out.println("Tournament: " + tournament);
            
            tournament.generatePlayers();
            tournament.generateReferees(15);
            tournament.initializeFirstRound();
            
            System.out.println("Men's Players: " + tournament.getMensPlayers().size());
            System.out.println("Women's Players: " + tournament.getWomensPlayers().size());
            System.out.println("Referees: " + tournament.getReferees().size());
            
            var firstRoundMatches = tournament.getMatchesForRound("First Round");
            System.out.println("First Round Matches: " + firstRoundMatches.size());
            
            System.out.println("✓ Tournament creation test passed");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test 10: Exception handling
     */
    public static void testExceptionHandling() {
        System.out.println("\n=== TEST 10: Exception Handling ===");
        
        int passedTests = 0;
        int totalTests = 6;
        
        // Test 1: Invalid birth name
        try {
            new Person("", "John", LocalDate.of(1990, 1, 1),
                      "City", "Country", 180, 75);
            System.out.println("✗ Should have thrown exception for empty birth name");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly caught empty birth name");
            passedTests++;
        }
        
        // Test 2: Invalid height
        try {
            new Person("Smith", "John", LocalDate.of(1990, 1, 1),
                      "City", "Country", -180, 75);
            System.out.println("✗ Should have thrown exception for negative height");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly caught negative height");
            passedTests++;
        }
        
        // Test 3: Invalid ranking
        try {
            new Player("Smith", "John", LocalDate.of(1990, 1, 1),
                      "City", "Country", 180, 75,
                      Player.Hand.RIGHT, Player.Gender.MALE, -1);
            System.out.println("✗ Should have thrown exception for negative ranking");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly caught negative ranking");
            passedTests++;
        }
        
        // Test 4: Invalid seat number
        try {
            new Spectator("Smith", "John", LocalDate.of(1990, 1, 1),
                         "City", "Country", 180, 75,
                         Spectator.Gender.MALE, 0, 100.0, "Finals");
            System.out.println("✗ Should have thrown exception for zero seat number");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly caught invalid seat number");
            passedTests++;
        }
        
        // Test 5: Gender mismatch in match
        try {
            Player male = new Player("Smith", "John", LocalDate.of(1990, 1, 1),
                                    "City", "Country", 180, 75,
                                    Player.Hand.RIGHT, Player.Gender.MALE, 1);
            Player female = new Player("Taylor", "Emma", LocalDate.of(1990, 1, 1),
                                      "City", "Country", 165, 60,
                                      Player.Hand.RIGHT, Player.Gender.FEMALE, 1);
            Referee ref = new Referee("Doe", "John", LocalDate.of(1970, 1, 1),
                                     "City", "Country", 180, 75, 10);
            
            new Match(male, female, ref, Match.Category.MENS_SINGLES, "Test", 0);
            System.out.println("✗ Should have thrown exception for gender mismatch");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly caught gender mismatch in match");
            passedTests++;
        }
        
        // Test 6: Future birth date
        try {
            new Person("Smith", "John", LocalDate.now().plusDays(1),
                      "City", "Country", 180, 75);
            System.out.println("✗ Should have thrown exception for future birth date");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly caught future birth date");
            passedTests++;
        }
        
        System.out.println("\n✓ Exception handling test: " + passedTests + 
                         "/" + totalTests + " tests passed");
    }
    
    /**
     * Run all tests
     */
    public static void runAllTests() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║  TENNIS TOURNAMENT SYSTEM - TEST SUITE   ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        
        testPersonCreation();
        testPlayerCreation();
        testRefereeCreation();
        testSpectatorCreation();
        testRally();
        testGame();
        testSet();
        testMatch();
        testTournamentCreation();
        testExceptionHandling();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ALL TESTS COMPLETED");
        System.out.println("=".repeat(50));
    }
    
    /**
     * Main method to run tests
     */
    public static void main(String[] args) {
        runAllTests();
    }
}
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
