/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;

import java.time.LocalDate;

/**
 * Represents a tennis player in the tournament.
 * Extends Person with player-specific attributes and behaviors.
 */
public class Player extends Person {
    
    /**
     * Enumerator for playing hand
     */
    public enum Hand {
        RIGHT, LEFT, AMBIDEXTROUS
    }
    
    /**
     * Enumerator for player gender/category
     */
    public enum Gender {
        MALE, FEMALE
    }
    
    // Player-specific attributes
    private Hand playingHand;
    private String sponsor;
    private int ranking;
    private String coach;
    private Gender gender;
    private String attireColor;  // Color of shorts (men) or skirt (women)
    
    // Statistics
    private int matchesWon;
    private int matchesLost;
    private int setsWon;
    private int setsLost;
    private int gamesWon;
    private int gamesLost;
    private int pointsWon;
    private int pointsLost;
    private int aces;
    private int doubleFaults;
    private int breakPointsConverted;
    private int breakPointsTotal;
    
    /**
     * Constructor for Player
     * @param birthName the player's birth name (immutable)
     * @param firstName the player's first name (immutable)
     * @param birthDate the player's date of birth (immutable)
     * @param placeOfBirth the player's place of birth (immutable)
     * @param nationality the player's nationality (immutable)
     * @param height the player's height in cm
     * @param weight the player's weight in kg
     * @param playingHand the hand the player uses (RIGHT, LEFT, AMBIDEXTROUS)
     * @param gender the player's gender (MALE, FEMALE)
     * @param ranking the player's initial ranking (positive integer)
     */
    public Player(String birthName, String firstName, LocalDate birthDate,
                  String placeOfBirth, String nationality, double height,
                  double weight, Hand playingHand, Gender gender, int ranking) {
        super(birthName, firstName, birthDate, placeOfBirth, nationality, height, weight);
        
        if (playingHand == null) {
            throw new IllegalArgumentException("Playing hand cannot be null");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        if (ranking <= 0) {
            throw new IllegalArgumentException("Ranking must be positive");
        }
        
        this.playingHand = playingHand;
        this.gender = gender;
        this.ranking = ranking;
        this.attireColor = (gender == Gender.MALE) ? "white" : "white";  // Default white
        
        // Initialize statistics
        this.matchesWon = 0;
        this.matchesLost = 0;
        this.setsWon = 0;
        this.setsLost = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.pointsWon = 0;
        this.pointsLost = 0;
        this.aces = 0;
        this.doubleFaults = 0;
        this.breakPointsConverted = 0;
        this.breakPointsTotal = 0;
    }
    
    // Getters and setters
    public Hand getPlayingHand() {
        return playingHand;
    }
    
    public void setPlayingHand(Hand playingHand) {
        if (playingHand == null) {
            throw new IllegalArgumentException("Playing hand cannot be null");
        }
        this.playingHand = playingHand;
    }
    
    public String getSponsor() {
        return sponsor;
    }
    
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
    
    public int getRanking() {
        return ranking;
    }
    
    public void setRanking(int ranking) {
        if (ranking <= 0) {
            throw new IllegalArgumentException("Ranking must be positive");
        }
        this.ranking = ranking;
    }
    
    public String getCoach() {
        return coach;
    }
    
    public void setCoach(String coach) {
        this.coach = coach;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public String getAttireColor() {
        return attireColor;
    }
    
    /**
     * Changes the player's attire color and announces it
     * @param newColor the new attire color
     */
    public void changeAttireColor(String newColor) {
        if (newColor == null || newColor.trim().isEmpty()) {
            throw new IllegalArgumentException("Attire color cannot be null or empty");
        }
        this.attireColor = newColor;
        
        String attireType = (gender == Gender.MALE) ? "shorts" : "skirt";
        System.out.println(getFullName() + " is now wearing " + newColor + " " + attireType);
    }
    
    // Statistics getters
    public int getMatchesWon() {
        return matchesWon;
    }
    
    public int getMatchesLost() {
        return matchesLost;
    }
    
    public int getSetsWon() {
        return setsWon;
    }
    
    public int getSetsLost() {
        return setsLost;
    }
    
    public int getGamesWon() {
        return gamesWon;
    }
    
    public int getGamesLost() {
        return gamesLost;
    }
    
    public int getPointsWon() {
        return pointsWon;
    }
    
    public int getPointsLost() {
        return pointsLost;
    }
    
    public int getAces() {
        return aces;
    }
    
    public int getDoubleFaults() {
        return doubleFaults;
    }
    
    public int getBreakPointsConverted() {
        return breakPointsConverted;
    }
    
    public int getBreakPointsTotal() {
        return breakPointsTotal;
    }
    
    // Methods to update statistics
    public void recordMatchWin() {
        this.matchesWon++;
    }
    
    public void recordMatchLoss() {
        this.matchesLost++;
    }
    
    public void recordSetWin() {
        this.setsWon++;
    }
    
    public void recordSetLoss() {
        this.setsLost++;
    }
    
    public void recordGameWin() {
        this.gamesWon++;
    }
    
    public void recordGameLoss() {
        this.gamesLost++;
    }
    
    public void recordPointWin() {
        this.pointsWon++;
    }
    
    public void recordPointLoss() {
        this.pointsLost++;
    }
    
    public void recordAce() {
        this.aces++;
        this.pointsWon++;
    }
    
    public void recordDoubleFault() {
        this.doubleFaults++;
        this.pointsLost++;
    }
    
    public void recordBreakPoint(boolean converted) {
        this.breakPointsTotal++;
        if (converted) {
            this.breakPointsConverted++;
        }
    }
    
    /**
     * Updates ranking after a match result
     * @param won true if player won the match
     * @param opponentRanking opponent's ranking
     */
    public void updateRanking(boolean won, int opponentRanking) {
        if (won) {
            // Win against higher-ranked player improves ranking more
            if (opponentRanking < this.ranking) {
                int improvement = (this.ranking - opponentRanking) / 10 + 1;
                this.ranking = Math.max(1, this.ranking - improvement);
            } else {
                this.ranking = Math.max(1, this.ranking - 1);
            }
        } else {
            // Loss increases ranking number (worse position)
            this.ranking++;
        }
    }
    
    /**
     * Gets the number of sets needed to win a match
     * @return 3 for men, 2 for women
     */
    public int getSetsToWin() {
        return (gender == Gender.MALE) ? 3 : 2;
    }
    
    /**
     * Player encourages themselves
     */
    public void encourage() {
        String[] encouragements = {
            "Come on!", "Let's go!", "Allez!", "Vamos!", "Yes!"
        };
        int index = (int) (Math.random() * encouragements.length);
        System.out.println(getFullName() + ": " + encouragements[index]);
    }
    
    /**
     * Player disputes a call
     */
    public void disputeCall(String reason) {
        System.out.println(getFullName() + " disputes the call: " + reason);
    }
    
    /**
     * Gets player statistics summary
     */
    public String getStatsSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Statistics for ").append(getFullName()).append(" ===\n");
        sb.append("Ranking: ").append(ranking).append("\n");
        sb.append("Matches: ").append(matchesWon).append("W - ").append(matchesLost).append("L\n");
        sb.append("Sets: ").append(setsWon).append("W - ").append(setsLost).append("L\n");
        sb.append("Games: ").append(gamesWon).append("W - ").append(gamesLost).append("L\n");
        sb.append("Points: ").append(pointsWon).append("W - ").append(pointsLost).append("L\n");
        sb.append("Aces: ").append(aces).append("\n");
        sb.append("Double Faults: ").append(doubleFaults).append("\n");
        if (breakPointsTotal > 0) {
            sb.append("Break Points: ").append(breakPointsConverted)
              .append("/").append(breakPointsTotal)
              .append(" (").append(String.format("%.1f", 100.0 * breakPointsConverted / breakPointsTotal))
              .append("%)\n");
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return super.toString() + " [Ranking: " + ranking + ", " + playingHand + "-handed, " + gender + "]";
    }
}