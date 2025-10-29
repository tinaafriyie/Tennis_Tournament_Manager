/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;

/**
 *
 * @author tina-
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a tennis match between two players.
 * Men's matches are best of 5 sets, women's are best of 3 sets.
 */
public class Match {
    
    /*
     * Enum for match category
     */
    public enum Category {
        MENS_SINGLES, WOMENS_SINGLES
    }
    
    private Player player1;
    private Player player2;
    private Referee referee;
    private Category category;
    private String level;  // e.g., "First Round", "Quarterfinals", etc.
    private int atpWtaPoints;  // Ranking points for this round
    private Player winner;
    private Player loser;
    private boolean isComplete;
    private List<Set> sets;
    private Player currentServer;
    private Random random;
    
    /*
     * Constructor for Match
     */
    public Match(Player player1, Player player2, Referee referee, 
                Category category, String level, int atpWtaPoints) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        if (referee == null) {
            throw new IllegalArgumentException("Referee cannot be null");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (level == null || level.trim().isEmpty()) {
            throw new IllegalArgumentException("Level cannot be null or empty");
        }
        if (atpWtaPoints < 0) {
            throw new IllegalArgumentException("ATP/WTA points cannot be negative");
        }
        
        // Validate gender matches category
        if (category == Category.MENS_SINGLES && 
            (player1.getGender() != Player.Gender.MALE || player2.getGender() != Player.Gender.MALE)) {
            throw new IllegalArgumentException("Men's singles requires male players");
        }
        if (category == Category.WOMENS_SINGLES && 
            (player1.getGender() != Player.Gender.FEMALE || player2.getGender() != Player.Gender.FEMALE)) {
            throw new IllegalArgumentException("Women's singles requires female players");
        }
        
        this.player1 = player1;
        this.player2 = player2;
        this.referee = referee;
        this.category = category;
        this.level = level;
        this.atpWtaPoints = atpWtaPoints;
        this.winner = null;
        this.loser = null;
        this.isComplete = false;
        this.sets = new ArrayList<>();
        this.random = new Random();
        
        // Randomly choose initial server
        this.currentServer = random.nextBoolean() ? player1 : player2;
    }
    
    /*
     * Plays the entire match
     */
    public void play() {
        referee.announceMatchStart(player1.getFullName(), player2.getFullName());
        
        int setsToWin = getSetsToWin();
        int player1Sets = 0;
        int player2Sets = 0;
        
        while (!isComplete) {
            // Determine if this is the deciding set
            boolean isDecidingSet = (player1Sets == setsToWin - 1 && player2Sets == setsToWin - 1);
            
            // Create and play a set
            Set set = new Set(player1, player2, currentServer, referee, isDecidingSet);
            System.out.println("\n=== SET " + (sets.size() + 1) + " ===\n");
            set.play();
            sets.add(set);
            
            // Update set scores
            Player setWinner = set.getWinner();
            if (setWinner == player1) {
                player1Sets++;
            } else {
                player2Sets++;
            }
            
            System.out.println("\nCurrent Match Score: " + player1.getDisplayName() + " " + 
                             player1Sets + " - " + player2Sets + " " + player2.getDisplayName());
            displaySetScores();
            
            // Check for match winner
            if (player1Sets == setsToWin) {
                winner = player1;
                loser = player2;
                isComplete = true;
            } else if (player2Sets == setsToWin) {
                winner = player2;
                loser = player1;
                isComplete = true;
            }
            
            // Update server for next set (alternate from last set's starting server)
            if (!isComplete) {
                currentServer = (currentServer == player1) ? player2 : player1;
            }
        }
        
        // Update match statistics and rankings
        winner.recordMatchWin();
        loser.recordMatchLoss();
        winner.updateRanking(true, loser.getRanking());
        loser.updateRanking(false, winner.getRanking());
        referee.recordMatchOfficiated();
        
        referee.announceMatchWinner(winner.getFullName());
        System.out.println("\nFinal Score: " + getMatchScoreString());
    }
    
    /**
     * Plays the match step by step (for interactive mode)
     */
    public void playNextSet() {
        if (isComplete) {
            System.out.println("Match is already complete!");
            return;
        }
        
        int setsToWin = getSetsToWin();
        int player1Sets = getPlayer1Sets();
        int player2Sets = getPlayer2Sets();
        
        boolean isDecidingSet = (player1Sets == setsToWin - 1 && player2Sets == setsToWin - 1);
        
        Set set = new Set(player1, player2, currentServer, referee, isDecidingSet);
        System.out.println("\n=== SET " + (sets.size() + 1) + " ===\n");
        set.play();
        sets.add(set);
        
        Player setWinner = set.getWinner();
        if (setWinner == player1) {
            player1Sets++;
        } else {
            player2Sets++;
        }
        
        System.out.println("\nCurrent Match Score: " + player1.getDisplayName() + " " + 
                         player1Sets + " - " + player2Sets + " " + player2.getDisplayName());
        displaySetScores();
        
        // Check for match winner
        if (player1Sets == setsToWin) {
            winner = player1;
            loser = player2;
            isComplete = true;
            finalizeMatch();
        } else if (player2Sets == setsToWin) {
            winner = player2;
            loser = player1;
            isComplete = true;
            finalizeMatch();
        } else {
            currentServer = (currentServer == player1) ? player2 : player1;
        }
    }
    
    /**
     * Finalizes match statistics
     */
    private void finalizeMatch() {
        winner.recordMatchWin();
        loser.recordMatchLoss();
        winner.updateRanking(true, loser.getRanking());
        loser.updateRanking(false, winner.getRanking());
        referee.recordMatchOfficiated();
        
        referee.announceMatchWinner(winner.getFullName());
        System.out.println("\nFinal Score: " + getMatchScoreString());
    }
    
    /**
     * Gets number of sets needed to win
     */
    private int getSetsToWin() {
        return (category == Category.MENS_SINGLES) ? 3 : 2;
    }
    
    /*
     * Gets number of sets won by player 1
     */
    public int getPlayer1Sets() {
        int count = 0;
        for (Set set : sets) {
            if (set.getWinner() == player1) {
                count++;
            }
        }
        return count;
    }
    
    /*
     * Gets number of sets won by player 2
     */
    public int getPlayer2Sets() {
        int count = 0;
        for (Set set : sets) {
            if (set.getWinner() == player2) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Displays set scores
     */
    private void displaySetScores() {
        System.out.print("Set Scores: ");
        for (int i = 0; i < sets.size(); i++) {
            Set set = sets.get(i);
            System.out.print(set.getPlayer1Games() + "-" + set.getPlayer2Games());
            if (i < sets.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    
    /*
     * Gets full match score as string
     */
    public String getMatchScoreString() {
        StringBuilder sb = new StringBuilder();
        sb.append(player1.getDisplayName()).append(" vs ").append(player2.getDisplayName()).append("\n");
        sb.append("Sets: ").append(getPlayer1Sets()).append("-").append(getPlayer2Sets()).append("\n");
        
        for (int i = 0; i < sets.size(); i++) {
            Set set = sets.get(i);
            sb.append("Set ").append(i + 1).append(": ")
              .append(set.getPlayer1Games()).append("-").append(set.getPlayer2Games())
              .append("\n");
        }
        
        if (isComplete) {
            sb.append("Winner: ").append(winner.getFullName());
        }
        
        return sb.toString();
    }
    
    // Getters
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
    
    public Referee getReferee() {
        return referee;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public String getLevel() {
        return level;
    }
    
    public int getAtpWtaPoints() {
        return atpWtaPoints;
    }
    
    public Player getWinner() {
        return winner;
    }
    
    public Player getLoser() {
        return loser;
    }
    
    public boolean isComplete() {
        return isComplete;
    }
    
    public List<Set> getSets() {
        return new ArrayList<>(sets);  // Return copy for encapsulation
    }
    
    public Player getCurrentServer() {
        return currentServer;
    }
    
    /*
     * Gets match summary for display
     */
    public String getMatchSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== MATCH SUMMARY ==========\n");
        sb.append("Level: ").append(level).append("\n");
        sb.append("Category: ").append(category).append("\n");
        sb.append("Points: ").append(atpWtaPoints).append(" ATP/WTA points\n\n");
        sb.append(getMatchScoreString()).append("\n");
        sb.append("Referee: ").append(referee.getFullName()).append("\n");
        sb.append("===================================\n");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "Match [" + level + ", " + category + "]: " + 
               player1.getDisplayName() + " vs " + player2.getDisplayName() +
               (isComplete ? " - Winner: " + winner.getDisplayName() : " (In Progress)");
    }
}