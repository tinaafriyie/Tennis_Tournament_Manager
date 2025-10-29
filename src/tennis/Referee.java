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
import java.util.Random;

/**
 * Represents a tennis referee who officiates matches.
 * Extends Person with referee-specific behaviors.
 */
public class Referee extends Person {
    
    private int experienceYears;
    private double reputation;  // 0.0 to 1.0, affects dispute resolution
    private int matchesOfficiated;
    private Random random;
    
    /*
     * Constructor for Referee
     */
    public Referee(String birthName, String firstName, LocalDate birthDate,
                   String placeOfBirth, String nationality, double height,
                   double weight, int experienceYears) {
        super(birthName, firstName, birthDate, placeOfBirth, nationality, height, weight);
        
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Experience years cannot be negative");
        }
        
        this.experienceYears = experienceYears;
        // Reputation increases with experience, capped at 1.0
        this.reputation = Math.min(1.0, 0.5 + (experienceYears * 0.05));
        this.matchesOfficiated = 0;
        this.random = new Random();
    }
    
    // Getters and setters
    public int getExperienceYears() {
        return experienceYears;
    }
    
    public void setExperienceYears(int experienceYears) {
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Experience years cannot be negative");
        }
        this.experienceYears = experienceYears;
        // Update reputation based on experience
        this.reputation = Math.min(1.0, 0.5 + (experienceYears * 0.05));
    }
    
    public double getReputation() {
        return reputation;
    }
    
    public int getMatchesOfficiated() {
        return matchesOfficiated;
    }
    
    /**
     * Increments the number of matches officiated
     */
    public void recordMatchOfficiated() {
        this.matchesOfficiated++;
        // Slightly improve reputation with each match
        this.reputation = Math.min(1.0, this.reputation + 0.001);
    }
    
    /*
     * Announces the score of a game
     */
    public void announceScore(String player1Name, int player1Score, 
                             String player2Name, int player2Score) {
        String score1 = convertScore(player1Score);
        String score2 = convertScore(player2Score);
        
        // Handle deuce and advantage
        if (player1Score >= 3 && player2Score >= 3) {
            if (player1Score == player2Score) {
                System.out.println("Deuce!");
                return;
            } else if (player1Score > player2Score) {
                System.out.println("Advantage " + player1Name);
                return;
            } else {
                System.out.println("Advantage " + player2Name);
                return;
            }
        }
        
        System.out.println(score1 + " - " + score2);
    }
    
    /*
     * Converts numeric score to tennis scoring system
     */
    private String convertScore(int score) {
        return switch (score) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            default -> "40";
        };
    }
    
    /*
     * Announces a game winner
     */
    public void announceGameWinner(String playerName) {
        System.out.println("Game, " + playerName + "!");
    }
    
    /*
     * Announces a set winner
     */
    public void announceSetWinner(String playerName, int player1Games, int player2Games) {
        System.out.println("Set, " + playerName + "! (" + player1Games + "-" + player2Games + ")");
    }
    
    /*
     * Announces a match winner
     */
    public void announceMatchWinner(String playerName) {
        System.out.println("\n*** MATCH WON BY " + playerName.toUpperCase() + " ***\n");
    }
    
    /*
     * Announces a fault
     */
    public void announceFault(String faultType) {
        System.out.println("Fault! (" + faultType + ")");
    }
    
    /*
     * Announces a double fault
     */
    public void announceDoubleFault(String playerName) {
        System.out.println("Double Fault! Point to opponent.");
    }
    
    /*
     * Announces an ace
     */
    public void announceAce() {
        System.out.println("Ace!");
    }
    
    /*
     * Announces a foot fault
     */
    public void announceFootFault() {
        announceFault("Foot Fault");
    }
    
    /*
     * Announces a net fault
     */
    public void announceNetFault() {
        announceFault("Net");
    }
    
    /*
     * Announces out of bounds
     */
    public void announceOut() {
        System.out.println("Out!");
    }
    
    /*
     * Announces ball is in
     */
    public void announceIn() {
        System.out.println("In!");
    }
    
    /*
     * Resolves a dispute based on player reputation and referee judgment
     * @param playerName the player disputing
     * @param playerReputation player's reputation (can be based on ranking)
     * @return true if dispute is accepted, false otherwise
     */
    public boolean resolveDispute(String playerName, double playerReputation) {
        // Decision based on referee reputation, player reputation, and randomness
        double acceptanceThreshold = 0.3 + (this.reputation * 0.3) + (playerReputation * 0.2);
        double decision = random.nextDouble();
        
        boolean accepted = decision < acceptanceThreshold;
        
        if (accepted) {
            System.out.println("After review, the call is overturned. Point to " + playerName + ".");
        } else {
            System.out.println("The call stands. Play continues.");
        }
        
        return accepted;
    }
    
    /*
     * Issues a warning to a player
     */
    public void issueWarning(String playerName, String reason) {
        System.out.println("WARNING to " + playerName + ": " + reason);
    }
    
    /*
     * Issues a point penalty
     */
    public void issuePointPenalty(String playerName, String reason) {
        System.out.println("POINT PENALTY to " + playerName + ": " + reason);
    }
    
    /*
     * Issues a game penalty
     */
    public void issueGamePenalty(String playerName, String reason) {
        System.out.println("GAME PENALTY to " + playerName + ": " + reason);
    }
    
    /*
     * Calls for silence from the crowd
     */
    public void callForSilence() {
        System.out.println("Quiet please!");
    }
    
    /*
     * Announces the start of a match
     */
    public void announceMatchStart(String player1, String player2) {
        System.out.println("\n========================================");
        System.out.println("Match: " + player1 + " vs " + player2);
        System.out.println("Referee: " + getFullName());
        System.out.println("========================================\n");
    }
    
    /*
     * Announces the server
     */
    public void announceServer(String playerName) {
        System.out.println(playerName + " to serve.");
    }
    
    /*
     * General announcement
     */
    public void announce(String message) {
        System.out.println("[Referee] " + message);
    }
    
    @Override
    public String toString() {
        return super.toString() + " [Referee, Experience: " + experienceYears + 
               " years, Matches: " + matchesOfficiated + "]";
    }
}
