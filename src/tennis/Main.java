/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;

/**
 *
 * @author tina-
 */

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Main class for managing the tennis tournament system.
 * Provides console-based user interface for creating and running tournaments.
 */
public class TournamentManager {
    
    private Tournament tournament;
    private Scanner scanner;
    
    public TournamentManager() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Main menu
     */
    public void run() {
        System.out.println("=".repeat(60));
        System.out.println("GRAND SLAM TENNIS TOURNAMENT MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Create New Tournament");
            System.out.println("2. Run Tournament");
            System.out.println("3. View Tournament Details");
            System.out.println("4. View Player Statistics");
            System.out.println("5. View Match Details");
            System.out.println("6. Test Individual Match");
            System.out.println("7. Exit");
            System.out.print("\nEnter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        createTournament();
                        break;
                    case 2:
                        runTournament();
                        break;
                    case 3:
                        viewTournamentDetails();
                        break;
                    case 4:
                        viewPlayerStatistics();
                        break;
                    case 5:
                        viewMatchDetails();
                        break;
                    case 6:
                        testIndividualMatch();
                        break;
                    case 7:
                        System.out.println("\nThank you for using the Tournament Management System!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Creates a new tournament
     */
    private void createTournament() {
        System.out.println("\n=== CREATE NEW TOURNAMENT ===");
        System.out.println("Select Grand Slam:");
        System.out.println("1. Australian Open (Melbourne, Plexicushion)");
        System.out.println("2. Roland Garros (Paris, Clay)");
        System.out.println("3. Wimbledon (London, Grass)");
        System.out.println("4. US Open (New York, Decoturf)");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            Tournament.GrandSlam grandSlam;
            
            switch (choice) {
                case 1:
                    grandSlam = Tournament.GrandSlam.AUSTRALIAN_OPEN;
                    break;
                case 2:
                    grandSlam = Tournament.GrandSlam.ROLAND_GARROS;
                    break;
                case 3:
                    grandSlam = Tournament.GrandSlam.WIMBLEDON;
                    break;
                case 4:
                    grandSlam = Tournament.GrandSlam.US_OPEN;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            System.out.print("Enter year (e.g., 2024): ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            
            tournament = new Tournament(grandSlam, year);
            
            System.out.println("\nGenerating players...");
            tournament.generatePlayers();
            
            System.out.print("How many referees? (recommended: 10-20): ");
            int refCount = Integer.parseInt(scanner.nextLine().trim());
            tournament.generateReferees(refCount);
            
            System.out.println("\nInitializing first round...");
            tournament.initializeFirstRound();
            
            System.out.println("\n✓ Tournament created successfully!");
            System.out.println(tournament);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error creating tournament: " + e.getMessage());
        }
    }
    
    /**
     * Runs the tournament
     */
    private void runTournament() {
        if (tournament == null) {
            System.out.println("\nNo tournament created. Please create a tournament first.");
            return;
        }
        
        System.out.println("\n=== RUN TOURNAMENT ===");
        System.out.println("1. Play All Rounds Automatically");
        System.out.println("2. Play Round by Round");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    playAllRounds();
                    break;
                case 2:
                    playRoundByRound();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    
    /**
     * Plays all tournament rounds automatically
     */
    private void playAllRounds() {
        System.out.println("\nPlaying all rounds...\n");
        
        String[] rounds = {"First Round", "Second Round", "Third Round", "Round of 16",
                          "Quarterfinals", "Semifinals", "Finals"};
        
        for (String round : rounds) {
            if (tournament.getCurrentRound().equals(round)) {
                System.out.println("\nPress Enter to play " + round + "...");
                scanner.nextLine();
                tournament.playCurrentRound();
            }
        }
    }
    
    /**
     * Plays tournament round by round with user control
     */
    private void playRoundByRound() {
        while (!tournament.getCurrentRound().equals("Finals") || 
               !tournament.getMatchesForRound("Finals").isEmpty()) {
            
            System.out.println("\nCurrent Round: " + tournament.getCurrentRound());
            System.out.println("1. Play Current Round");
            System.out.println("2. View Current Round Matches");
            System.out.println("3. Return to Main Menu");
            System.out.print("Enter choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1 -> tournament.playCurrentRound();
                    case 2 -> viewCurrentRoundMatches();
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }
    
    /**
     * Views current round matches
     */
    private void viewCurrentRoundMatches() {
        var matches = tournament.getMatchesForRound(tournament.getCurrentRound());
        
        if (matches.isEmpty()) {
            System.out.println("\nNo matches in current round.");
            return;
        }
        
        System.out.println("\n=== " + tournament.getCurrentRound() + " Matches ===");
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            System.out.println((i + 1) + ". " + match);
        }
    }
    
    /**
     * Views tournament details
     */
    private void viewTournamentDetails() {
        if (tournament == null) {
            System.out.println("\nNo tournament created.");
            return;
        }
        
        System.out.println("\n=== TOURNAMENT DETAILS ===");
        System.out.println(tournament);
        System.out.println("Current Round: " + tournament.getCurrentRound());
        System.out.println("Total Men's Players: " + tournament.getMensPlayers().size());
        System.out.println("Total Women's Players: " + tournament.getWomensPlayers().size());
        System.out.println("Total Referees: " + tournament.getReferees().size());
        
        System.out.println("\n--- Matches by Round ---");
        String[] rounds = {"First Round", "Second Round", "Third Round", "Round of 16",
                          "Quarterfinals", "Semifinals", "Finals"};
        
        for (String round : rounds) {
            var matches = tournament.getMatchesForRound(round);
            System.out.println(round + ": " + matches.size() + " matches");
        }
    }
    
    /**
     * Views player statistics
     */
    private void viewPlayerStatistics() {
        if (tournament == null) {
            System.out.println("\nNo tournament created.");
            return;
        }
        
        System.out.println("\n=== PLAYER STATISTICS ===");
        System.out.println("1. View Men's Player Stats");
        System.out.println("2. View Women's Player Stats");
        System.out.println("3. Search Player by Name");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1 -> viewMensPlayerStats();
                case 2 -> viewWomensPlayerStats();
                case 3 -> searchPlayerByName();
                default -> System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    
    /**
     * Views men's player statistics
     */
    private void viewMensPlayerStats() {
        var players = tournament.getMensPlayers();
        
        System.out.println("\n=== TOP 10 MEN'S PLAYERS (by ranking) ===");
        players.sort((p1, p2) -> Integer.compare(p1.getRanking(), p2.getRanking()));
        
        for (int i = 0; i < Math.min(10, players.size()); i++) {
            Player p = players.get(i);
            System.out.println("\n" + (i + 1) + ". " + p.getFullName());
            System.out.println("   Ranking: " + p.getRanking());
            System.out.println("   Record: " + p.getMatchesWon() + "W - " + p.getMatchesLost() + "L");
            System.out.println("   Aces: " + p.getAces() + ", Double Faults: " + p.getDoubleFaults());
        }
        
        System.out.print("\nEnter player number for detailed stats (or 0 to return): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice > 0 && choice <= Math.min(10, players.size())) {
                System.out.println(players.get(choice - 1).getStatsSummary());
            }
        } catch (NumberFormatException e) {
            // Return to menu
        }
    }
    
    /**
     * Views women's player statistics
     */
    private void viewWomensPlayerStats() {
        var players = tournament.getWomensPlayers();
        
        System.out.println("\n=== TOP 10 WOMEN'S PLAYERS (by ranking) ===");
        players.sort((p1, p2) -> Integer.compare(p1.getRanking(), p2.getRanking()));
        
        for (int i = 0; i < Math.min(10, players.size()); i++) {
            Player p = players.get(i);
            System.out.println("\n" + (i + 1) + ". " + p.getFullName());
            System.out.println("   Ranking: " + p.getRanking());
            System.out.println("   Record: " + p.getMatchesWon() + "W - " + p.getMatchesLost() + "L");
            System.out.println("   Aces: " + p.getAces() + ", Double Faults: " + p.getDoubleFaults());
        }
        
        System.out.print("\nEnter player number for detailed stats (or 0 to return): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice > 0 && choice <= Math.min(10, players.size())) {
                System.out.println(players.get(choice - 1).getStatsSummary());
            }
        } catch (NumberFormatException e) {
            // Return to menu
        }
    }
    
    /**
     * Searches for a player by name
     */
    private void searchPlayerByName() {
        System.out.print("\nEnter player name (first or last): ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();
        
        var allPlayers = tournament.getMensPlayers();
        allPlayers.addAll(tournament.getWomensPlayers());
        
        System.out.println("\n=== SEARCH RESULTS ===");
        boolean found = false;
        
        for (Player p : allPlayers) {
            if (p.getFirstName().toLowerCase().contains(searchTerm) ||
                p.getDisplayName().toLowerCase().contains(searchTerm)) {
                System.out.println(p);
                System.out.println(p.getStatsSummary());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No players found matching '" + searchTerm + "'");
        }
    }
    
    /**
     * Views match details
     */
    private void viewMatchDetails() {
        if (tournament == null) {
            System.out.println("\nNo tournament created.");
            return;
        }
        
        System.out.println("\n=== VIEW MATCH DETAILS ===");
        System.out.print("Enter round name (e.g., 'Finals', 'Semifinals'): ");
        String round = scanner.nextLine().trim();
        
        var matches = tournament.getMatchesForRound(round);
        
        if (matches.isEmpty()) {
            System.out.println("No matches found for round: " + round);
            return;
        }
        
        System.out.println("\n=== " + round + " Matches ===");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i));
        }
        
        System.out.print("\nEnter match number for details (or 0 to return): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice > 0 && choice <= matches.size()) {
                Match match = matches.get(choice - 1);
                System.out.println(match.getMatchSummary());
            }
        } catch (NumberFormatException e) {
            // Return to menu
        }
    }
    
    /**
     * Tests an individual match with custom players
     */
    private void testIndividualMatch() {
        System.out.println("\n=== TEST INDIVIDUAL MATCH ===");
        
        try {
            // Create two test players
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
                                         "Oliveira de Azeméis", "Portugal",
                                         188, 80, 20);
            
            System.out.println("\nTest Match: " + player1.getFullName() + 
                             " vs " + player2.getFullName());
            
            Match match = new Match(player1, player2, referee,
                                  Match.Category.MENS_SINGLES,
                                  "Exhibition", 0);
            
            System.out.println("\nPress Enter to start the match...");
            scanner.nextLine();
            
            match.play();
            
            System.out.println("\n" + match.getMatchSummary());
            System.out.println(player1.getStatsSummary());
            System.out.println(player2.getStatsSummary());
            
        } catch (Exception e) {
            System.out.println("Error creating test match: " + e.getMessage());
        }
    }
    
    /*
     * Main method
     */
    public static void main(String[] args) {
        TournamentManager manager = new TournamentManager();
        manager.run();
    }
}