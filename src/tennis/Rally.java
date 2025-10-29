/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;

/**
 *
 * @author tina-
 */
import java.util.Random;

/**
 * Represents a rally (single point exchange) in a tennis game.
 */
public class Rally {
    
    /*
     * Enum for rally outcome
     */
    public enum Outcome {
        PLAYER1_WINS,
        PLAYER2_WINS,
        ACE,
        DOUBLE_FAULT,
        FAULT
    }
    
    private Player server;
    private Player receiver;
    private int shotCount;
    private Outcome outcome;
    private boolean isAce;
    private boolean isDoubleFault;
    private boolean isFirstServe;
    private Random random;
    
    /*
     * Constructor for Rally
     */
    public Rally(Player server, Player receiver) {
        if (server == null || receiver == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        
        this.server = server;
        this.receiver = receiver;
        this.shotCount = 0;
        this.random = new Random();
        this.isFirstServe = true;
        this.isAce = false;
        this.isDoubleFault = false;
    }
    
    /**
     * Plays the rally and determines the outcome
     * @param referee the referee officiating
     * @return the outcome of the rally
     */
    public Outcome play(Referee referee) {
        // First serve attempt
        boolean firstServeIn = attemptServe(0.65);  // 65% first serve percentage
        
        if (!firstServeIn) {
            referee.announceFault("First serve");
            isFirstServe = false;
            
            // Second serve attempt
            boolean secondServeIn = attemptServe(0.90);  // 90% second serve percentage
            
            if (!secondServeIn) {
                // Double fault
                referee.announceDoubleFault(server.getFullName());
                this.isDoubleFault = true;
                this.outcome = Outcome.DOUBLE_FAULT;
                server.recordDoubleFault();
                receiver.recordPointWin();
                return Outcome.PLAYER2_WINS;
            }
        }
        
        // Serve is in, check for ace
        if (checkForAce(isFirstServe)) {
            referee.announceAce();
            this.isAce = true;
            this.outcome = Outcome.ACE;
            server.recordAce();
            receiver.recordPointLoss();
            this.shotCount = 1;
            return Outcome.PLAYER1_WINS;
        }
        
        // Rally continues
        this.shotCount = 1;
        return playRally(referee);
    }
    
    /**
     * Attempts a serve
     * @param successRate probability of serve being in
     * @return true if serve is in, false if fault
     */
    private boolean attemptServe(double successRate) {
        double serveQuality = random.nextDouble();
        
        // Check for foot fault (rare)
        if (random.nextDouble() < 0.02) {
            return false;
        }
        
        return serveQuality < successRate;
    }
    
    /**
     * Checks if the serve is an ace
     * @param isFirstServe true if first serve
     * @return true if ace
     */
    private boolean checkForAce(boolean isFirstServe) {
        // First serves have higher ace probability
        double aceChance = isFirstServe ? 0.08 : 0.03;
        return random.nextDouble() < aceChance;
    }
    
    /*
     * Simulates the rally after serve is returned
     * @param referee the referee
     * @return outcome of the rally
     */
    private Outcome playRally(Referee referee) {
        // Rally length follows realistic distribution
        int maxShots = 3 + random.nextInt(15);  // 3-17 shots
        
        for (int i = 1; i < maxShots; i++) {
            shotCount++;
            
            // Determine if shot is successful
            Player currentPlayer = (i % 2 == 0) ? server : receiver;
            double errorChance = 0.15 + (shotCount * 0.01);  // Errors increase with rally length
            
            if (random.nextDouble() < errorChance) {
                // Player makes an error
                if (currentPlayer == server) {
                    server.recordPointLoss();
                    receiver.recordPointWin();
                    return Outcome.PLAYER2_WINS;
                } else {
                    receiver.recordPointLoss();
                    server.recordPointWin();
                    return Outcome.PLAYER1_WINS;
                }
            }
            
            // Check for winner (clean shot)
            double winnerChance = 0.10 + (shotCount * 0.005);
            if (random.nextDouble() < winnerChance) {
                if (currentPlayer == server) {
                    server.recordPointWin();
                    receiver.recordPointLoss();
                    return Outcome.PLAYER1_WINS;
                } else {
                    receiver.recordPointWin();
                    server.recordPointLoss();
                    return Outcome.PLAYER2_WINS;
                }
            }
        }
        
        // Rally ended naturally, determine winner randomly
        if (random.nextBoolean()) {
            server.recordPointWin();
            receiver.recordPointLoss();
            return Outcome.PLAYER1_WINS;
        } else {
            receiver.recordPointWin();
            server.recordPointLoss();
            return Outcome.PLAYER2_WINS;
        }
    }
    
    // Getters
    public Player getServer() {
        return server;
    }
    
    public Player getReceiver() {
        return receiver;
    }
    
    public int getShotCount() {
        return shotCount;
    }
    
    public Outcome getOutcome() {
        return outcome;
    }
    
    public boolean isAce() {
        return isAce;
    }
    
    public boolean isDoubleFault() {
        return isDoubleFault;
    }
    
    public boolean isFirstServe() {
        return isFirstServe;
    }
    
    /*
     * Gets the winner of the rally
     */
    public Player getWinner() {
        if (outcome == Outcome.PLAYER1_WINS || outcome == Outcome.ACE) {
            return server;
        } else if (outcome == Outcome.PLAYER2_WINS || outcome == Outcome.DOUBLE_FAULT) {
            return receiver;
        }
        return null;
    }
    
    /*
     * Gets rally intensity for spectator reactions
     */
    public double getIntensity() {
        if (isAce) return 1.0;
        if (isDoubleFault) return 0.3;
        
        // Longer rallies are more exciting
        return Math.min(1.0, 0.3 + (shotCount * 0.05));
    }
    
    @Override
    public String toString() {
        return "Rally: " + server.getDisplayName() + " serving to " + 
               receiver.getDisplayName() + " - " + shotCount + " shots, " +
               (isAce ? "ACE" : isDoubleFault ? "DOUBLE FAULT" : "completed");
    }
}
