/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;


/**
 *
 * @author tina-
 */



/**
 * Represents a tennis referee who officiates matches.
 * Extends Person with referee-specific behaviors.
 */
public class Referee extends Person {

    private int experienceYears;
    private double reputation;  // 0.0 to 1.0, affects dispute resolution
    private int matchesOfficiated;
    private final Random random;
    private final PrintStream out;

    /*
     * Constructor for Referee with injected Random and PrintStream for testability.
     */
    public Referee(String birthName, String firstName, LocalDate birthDate,
                   String placeOfBirth, String nationality, double height,
                   double weight, int experienceYears) {
        this(birthName, firstName, birthDate, placeOfBirth, nationality, height, weight, experienceYears, null, null);
    }

    public Referee(String birthName, String firstName, LocalDate birthDate,
                   String placeOfBirth, String nationality, double height,
                   double weight, int experienceYears, Random rng, PrintStream out) {
        super(birthName, firstName, birthDate, placeOfBirth, nationality, height, weight);
        Objects.requireNonNull(birthName);
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Experience years cannot be negative");
        }
        this.experienceYears = experienceYears;
        this.reputation = Math.min(1.0, 0.5 + (experienceYears * 0.05));
        this.matchesOfficiated = 0;
        this.random = (rng == null) ? new Random() : rng;
        this.out = (out == null) ? System.out : out;
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
        this.reputation = Math.min(1.0, this.reputation + 0.001);
    }

    /*
     * Announces the score of a game using numeric points (0..n).
     * Accepts raw numeric point counts where 0=0,1=15,2=30,3=40, >=4 used for advantage logic.
     */
    public void announceScore(String player1Name, int player1Score,
                              String player2Name, int player2Score) {
        String score1 = convertScore(player1Score);
        String score2 = convertScore(player2Score);

        if (player1Score >= 3 && player2Score >= 3) {
            if (player1Score == player2Score) {
                out.println("Deuce!");
                return;
            } else if (player1Score > player2Score) {
                out.println("Advantage " + player1Name);
                return;
            } else {
                out.println("Advantage " + player2Name);
                return;
            }
        }

        out.println(player1Name + ": " + score1 + " - " + player2Name + ": " + score2);
    }

    /*
     * Announces a tennis-formatted score string (e.g., "40-30" or "AD - 40")
     */
    public void announceScore(String serverName, String receiverName, String tennisScore) {
        out.println(serverName + " vs " + receiverName + " -> " + tennisScore);
    }

    /*
     * Converts numeric score to tennis scoring system
     */
    private String convertScore(int score) {
        switch (score) {
            case 0: return "0";
            case 1: return "15";
            case 2: return "30";
            case 3: return "40";
            default: return "40";
        }
    }

    /*
     * Announces a game winner
     */
    public void announceGameWinner(String playerName) {
        out.println("Game, " + playerName + "!");
    }

    /*
     * Announces a set winner
     */
    public void announceSetWinner(String playerName, int player1Games, int player2Games) {
        out.println("Set, " + playerName + "! (" + player1Games + "-" + player2Games + ")");
    }

    /*
     * Announces a match winner
     */
    public void announceMatchWinner(String playerName) {
        out.println("\n*** MATCH WON BY " + playerName.toUpperCase() + " ***\n");
    }

    /*
     * Announces a fault
     */
    public void announceFault(String faultType) {
        out.println("Fault! (" + faultType + ")");
    }

    /*
     * Announces a double fault
     */
    public void announceDoubleFault(String playerName) {
        out.println("Double Fault by " + playerName + "! Point to opponent.");
    }

    /*
     * Announces an ace
     */
    public void announceAce(String playerName) {
        out.println("Ace by " + playerName + "!");
    }

    /*
     * Announces foot fault
     */
    public void announceFootFault() {
        announceFault("Foot Fault");
    }

    /*
     * Announces net fault
     */
    public void announceNetFault() {
        announceFault("Net");
    }

    /*
     * Announces out of bounds
     */
    public void announceOut() {
        out.println("Out!");
    }

    /*
     * Announces ball is in
     */
    public void announceIn() {
        out.println("In!");
    }

    /*
     * Resolves a dispute based on player reputation and referee judgment
     */
    public boolean resolveDispute(String playerName, double playerReputation) {
        double acceptanceThreshold = 0.3 + (this.reputation * 0.3) + (playerReputation * 0.2);
        double decision = random.nextDouble();

        boolean accepted = decision < acceptanceThreshold;

        if (accepted) {
            out.println("After review, the call is overturned. Point to " + playerName + ".");
        } else {
            out.println("The call stands. Play continues.");
        }

        return accepted;
    }

    /*
     * Issues a warning to a player
     */
    public void issueWarning(String playerName, String reason) {
        out.println("WARNING to " + playerName + ": " + reason);
    }

    /*
     * Issues a point penalty
     */
    public void issuePointPenalty(String playerName, String reason) {
        out.println("POINT PENALTY to " + playerName + ": " + reason);
    }

    /*
     * Issues a game penalty
     */
    public void issueGamePenalty(String playerName, String reason) {
        out.println("GAME PENALTY to " + playerName + ": " + reason);
    }

    /*
     * Calls for silence from the crowd
     */
    public void callForSilence() {
        out.println("Quiet please!");
    }

    /*
     * Announces the start of a match
     */
    public void announceMatchStart(String player1, String player2) {
        out.println("\n========================================");
        out.println("Match: " + player1 + " vs " + player2);
        out.println("Referee: " + getFullName());
        out.println("========================================\n");
    }

    /*
     * Announces the server
     */
    public void announceServer(String playerName) {
        out.println(playerName + " to serve.");
    }

    /*
     * General announcement
     */
    public void announce(String message) {
        out.println("[Referee] " + message);
    }

    @Override
    public String toString() {
        return super.toString() + " [Referee, Experience: " + experienceYears +
               " years, Matches: " + matchesOfficiated + "]";
    }
}
