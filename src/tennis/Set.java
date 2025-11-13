/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a set in a tennis match.
 * A set is won when a player wins 6+ games with at least a 2-game advantage,
 * or wins a tiebreak at 6-6 for non-deciding sets.
 */
public class Set {

    private final Player player1;
    private final Player player2;
    private int player1Games;
    private int player2Games;
    private Player winner;
    private boolean isComplete;
    private final boolean isDecidingSet;
    private final Referee referee;
    private Player currentServer;
    private final List<Game> games;
    private final Random random;

    /**
     * Constructs a Set with optional Random injection for determinism.
     *
     * @param player1 initial player1
     * @param player2 initial player2
     * @param initialServer who serves first this set (must be one of the players)
     * @param referee referee (non-null)
     * @param isDecidingSet true when this is the final deciding set (no automatic tiebreak)
     */
    public Set(Player player1, Player player2, Player initialServer,
               Referee referee, boolean isDecidingSet) {
        this(player1, player2, initialServer, referee, isDecidingSet, null);
    }

    /**
     * Full constructor with Random injection.
     */
    public Set(Player player1, Player player2, Player initialServer,
               Referee referee, boolean isDecidingSet, Random rng) {
        this.player1 = Objects.requireNonNull(player1);
        this.player2 = Objects.requireNonNull(player2);
        if (initialServer == null || (initialServer != player1 && initialServer != player2)) {
            throw new IllegalArgumentException("Initial server must be one of the two players and non-null");
        }
        this.currentServer = initialServer;
        this.referee = Objects.requireNonNull(referee);
        this.isDecidingSet = isDecidingSet;
        this.player1Games = 0;
        this.player2Games = 0;
        this.winner = null;
        this.isComplete = false;
        this.games = new ArrayList<>();
        this.random = (rng == null) ? new Random() : rng;
    }

    /**
     * Plays the set until completion.
     * Responsibilities:
     *  - Uses Game to decide each game;
     *  - Updates game counts and, when set finishes, records set-level statistics on players;
     *  - Announces set winner via referee.
     */
    public void play() {
        while (!isComplete) {
            // Decide whether to create a tiebreak game
            boolean createTiebreak = (player1Games == 6 && player2Games == 6 && !isDecidingSet);

            Game game = new Game(player1, player2, currentServer, referee, createTiebreak, random);
            game.play();
            games.add(game);

            Player gameWinner = game.getWinner();
            if (gameWinner == null) {
                throw new IllegalStateException("Game ended without a winner inside Set.play()");
            }

            if (gameWinner == player1) {
                player1Games++;
            } else {
                player2Games++;
            }

            checkSetWinner();

            if (!isComplete) {
                // Alternate server for next game
                currentServer = (currentServer == player1) ? player2 : player1;
            }
        }

        // Record set-level statistics once
        if (winner == player1) {
            player1.recordSetWin();
            player2.recordSetLoss();
        } else {
            player2.recordSetWin();
            player1.recordSetLoss();
        }

        referee.announceSetWinner(winner != null ? winner.getFullName() : "Unknown", player1Games, player2Games);
    }

    /**
     * Determines whether the set has been won and sets fields accordingly.
     * Rules:
     *  - Non-deciding set: tiebreak at 6-6 (winner can be 7-6);
     *  - Deciding set: no automatic tiebreak (must win by 2 games after 5-5).
     */
    private void checkSetWinner() {
        int maxGames = Math.max(player1Games, player2Games);
        int diff = Math.abs(player1Games - player2Games);

        if (!isDecidingSet) {
            // If a tiebreak occurred and one player has 7-6, accept it
            if (player1Games == 7 && player2Games == 6) {
                winner = player1;
                isComplete = true;
                return;
            }
            if (player2Games == 7 && player1Games == 6) {
                winner = player2;
                isComplete = true;
                return;
            }
        }

        // Win by two games once at least six games reached
        if (maxGames >= 6 && diff >= 2) {
            winner = (player1Games > player2Games) ? player1 : player2;
            isComplete = true;
        }
    }

    // Getters (defensive copies where appropriate)

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public int getPlayer1Games() { return player1Games; }
    public int getPlayer2Games() { return player2Games; }
    public Player getWinner() { return winner; }
    public boolean isComplete() { return isComplete; }
    public boolean isDecidingSet() { return isDecidingSet; }
    public Player getCurrentServer() { return currentServer; }

    /**
     * Returns an unmodifiable snapshot of games played in this set.
     */
    public List<Game> getGames() {
        return Collections.unmodifiableList(new ArrayList<>(games));
    }

    /**
     * Returns a simple score string like "6-3" or "7-6".
     */
    public String getScoreString() {
        return player1Games + "-" + player2Games;
    }

    /**
     * Total games played in this set.
     */
    public int getTotalGames() {
        return player1Games + player2Games;
    }

    @Override
    public String toString() {
        String winPart = isComplete ? " (Winner: " + (winner != null ? winner.getDisplayName() : "Unknown") + ")" : " (In Progress)";
        String deciding = isDecidingSet ? " [DECIDING SET]" : "";
        return "Set: " + player1.getDisplayName() + " " + player1Games + " - " + player2Games + " " + player2.getDisplayName() + winPart + deciding;
    }
}
