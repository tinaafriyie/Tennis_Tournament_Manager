/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;

import java.util.Objects;
import java.util.Random;

/**
 *
 * @author tina-
 */



/**
 * Represents a rally (single point exchange) in a tennis game.
 * play(...) now returns the Player winner for unambiguous attribution.
 */
public class Rally {

    private Player server;
    private Player receiver;
    private int shotCount;
    private boolean isAce;
    private boolean isDoubleFault;
    private boolean isFirstServe;
    private final Random random;

    /**
     * Constructor with optional Random injection for determinism/testing.
     */
    public Rally(Player server, Player receiver) {
        this(server, receiver, null);
    }

    public Rally(Player server, Player receiver, Random rng) {
        this.server = Objects.requireNonNull(server);
        this.receiver = Objects.requireNonNull(receiver);
        this.shotCount = 0;
        this.random = (rng == null) ? new Random() : rng;
        this.isFirstServe = true;
        this.isAce = false;
        this.isDoubleFault = false;
    }

    /**
     * Plays the rally and returns the Player who won the point.
     * Side effects: updates point-level statistics on Players and calls referee announcements.
     */
    public Player play(Referee referee) {
        // First serve attempt
        boolean firstServeIn = attemptServe(0.65);

        if (!firstServeIn) {
            referee.announceFault("First serve");
            isFirstServe = false;

            // Second serve attempt
            boolean secondServeIn = attemptServe(0.90);

            if (!secondServeIn) {
                // Double fault -> receiver wins point
                referee.announceDoubleFault(server.getFullName());
                this.isDoubleFault = true;
                server.recordDoubleFault();
                receiver.recordPointWin();
                this.shotCount = 0;
                return receiver;
            }
        }

        // Serve in, check for ace
        if (checkForAce(isFirstServe)) {
            referee.announceAce(server.getFullName());
            this.isAce = true;
            server.recordAce();
            receiver.recordPointLoss();
            this.shotCount = 1;
            return server;
        }

        // Rally continues
        this.shotCount = 1;
        return playRally(referee);
    }

    private boolean attemptServe(double successRate) {
        double serveQuality = random.nextDouble();

        // Foot fault check (rare)
        if (random.nextDouble() < 0.02) {
            return false;
        }

        return serveQuality < successRate;
    }

    private boolean checkForAce(boolean isFirstServe) {
        double aceChance = isFirstServe ? 0.08 : 0.03;
        return random.nextDouble() < aceChance;
    }

    /**
     * Simulates the rally after the serve is returned and returns the Player winner.
     */
    private Player playRally(Referee referee) {
        int maxShots = 3 + random.nextInt(15); // 3-17 shots

        for (int i = 1; i <= maxShots; i++) {
            shotCount++;

            // Current player on this stroke (odd strokes: receiver, even: server) is simplified
            Player currentPlayer = (i % 2 == 0) ? server : receiver;

            double errorChance = 0.15 + (shotCount * 0.01);
            if (random.nextDouble() < errorChance) {
                // currentPlayer makes an unforced error
                if (currentPlayer == server) {
                    server.recordPointLoss();
                    receiver.recordPointWin();
                    return receiver;
                } else {
                    receiver.recordPointLoss();
                    server.recordPointWin();
                    return server;
                }
            }

            double winnerChance = 0.10 + (shotCount * 0.005);
            if (random.nextDouble() < winnerChance) {
                if (currentPlayer == server) {
                    server.recordPointWin();
                    receiver.recordPointLoss();
                    return server;
                } else {
                    receiver.recordPointWin();
                    server.recordPointLoss();
                    return receiver;
                }
            }
        }

        // Natural end: random winner
        if (random.nextBoolean()) {
            server.recordPointWin();
            receiver.recordPointLoss();
            return server;
        } else {
            receiver.recordPointWin();
            server.recordPointLoss();
            return receiver;
        }
    }

    // Getters
    public Player getServer() { return server; }
    public Player getReceiver() { return receiver; }
    public int getShotCount() { return shotCount; }
    public boolean isAce() { return isAce; }
    public boolean isDoubleFault() { return isDoubleFault; }
    public boolean isFirstServe() { return isFirstServe; }

    /*
     * Gets rally intensity for spectator reactions
     */
    public double getIntensity() {
        if (isAce) return 1.0;
        if (isDoubleFault) return 0.3;
        return Math.min(1.0, 0.3 + (shotCount * 0.05));
    }

    @Override
    public String toString() {
        return "Rally: " + server.getDisplayName() + " serving to " +
               receiver.getDisplayName() + " - " + shotCount + " shots, " +
               (isAce ? "ACE" : isDoubleFault ? "DOUBLE FAULT" : "completed");
    }
}
