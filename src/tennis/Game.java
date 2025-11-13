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
 * Represents a game in a tennis set.
 * Game.play() uses Rally.play(referee) which returns the Player winner.
 */
public class Game {

    private final Player player1;
    private final Player player2;
    private final Referee referee;
    private Player server;
    private int player1Points;
    private int player2Points;
    private Player winner;
    private boolean isComplete;
    private final boolean isTiebreak;
    private final Random random;

    /*
     * Regular game constructor
     */
    public Game(Player player1, Player player2, Player server, Referee referee) {
        this(player1, player2, server, referee, false, null);
    }

    /*
     * Tiebreak constructor with Random injection
     */
    public Game(Player player1, Player player2, Player server, Referee referee, boolean isTiebreak) {
        this(player1, player2, server, referee, isTiebreak, null);
    }

    public Game(Player player1, Player player2, Player server, Referee referee, boolean isTiebreak, Random rng) {
        this.player1 = Objects.requireNonNull(player1);
        this.player2 = Objects.requireNonNull(player2);
        this.server = Objects.requireNonNull(server);
        this.referee = Objects.requireNonNull(referee);
        this.player1Points = 0;
        this.player2Points = 0;
        this.winner = null;
        this.isComplete = false;
        this.isTiebreak = isTiebreak;
        this.random = (rng == null) ? new Random() : rng;
    }

    /*
     * Plays the game until completion
     */
    public void play() {
        referee.announceServer(server.getFullName());

        if (isTiebreak) {
            playTiebreak();
        } else {
            playRegularGame();
        }
    }

    private void playRegularGame() {
        Player receiver = (server == player1) ? player2 : player1;

        while (!isComplete) {
            Rally rally = new Rally(server, receiver, random);
            Player rallyWinner = rally.play(referee);

            // Update points according to rally winner
            if (rallyWinner == player1) {
                player1Points++;
            } else {
                player2Points++;
            }

            // Announce tennis-style score (use server/receiver context)
            String tennisScore = getScoreString();
            referee.announceScore(server.getFullName(), receiver.getFullName(), tennisScore);

            checkGameWinner();
        }
    }

    private void playTiebreak() {
        referee.announce("[TIEBREAK]");
        Player currentServer = server;
        Player receiver = (server == player1) ? player2 : player1;
        int pointsPlayed = 0;

        while (!isComplete) {
            Rally rally = new Rally(currentServer, receiver, random);
            Player rallyWinner = rally.play(referee);

            // Update points for tiebreak
            if (rallyWinner == player1) player1Points++; else player2Points++;
            pointsPlayed++;

            // Announce tiebreak numeric score
            referee.announceScore(player1.getDisplayName(), player1Points, player2.getDisplayName(), player2Points);

            // Switch server after first point, then every 2 points
            if (pointsPlayed == 1 || (pointsPlayed > 1 && (pointsPlayed - 1) % 2 == 0)) {
                Player temp = currentServer;
                currentServer = receiver;
                receiver = temp;
                referee.announceServer(currentServer.getFullName());
            }

            // Check for tiebreak winner (first to 7 points with 2-point margin)
            if ((player1Points >= 7 || player2Points >= 7) &&
                Math.abs(player1Points - player2Points) >= 2) {
                if (player1Points > player2Points) {
                    winner = player1;
                    player1.recordGameWin();
                    player2.recordGameLoss();
                } else {
                    winner = player2;
                    player2.recordGameWin();
                    player1.recordGameLoss();
                }
                isComplete = true;
                referee.announceGameWinner(winner.getFullName());
            }
        }
    }

    private void checkGameWinner() {
        if (player1Points >= 4 || player2Points >= 4) {
            int diff = Math.abs(player1Points - player2Points);
            if (diff >= 2) {
                if (player1Points > player2Points) {
                    winner = player1;
                    player1.recordGameWin();
                    player2.recordGameLoss();
                } else {
                    winner = player2;
                    player2.recordGameWin();
                    player1.recordGameLoss();
                }
                isComplete = true;
                referee.announceGameWinner(winner.getFullName());
            }
        }
    }

    // Getters
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Player getServer() { return server; }
    public int getPlayer1Points() { return player1Points; }
    public int getPlayer2Points() { return player2Points; }
    public Player getWinner() { return winner; }
    public boolean isComplete() { return isComplete; }
    public boolean isTiebreak() { return isTiebreak; }

    /*
     * Converts numeric score to tennis scoring (server context)
     */
    public String getScoreString() {
        if (isTiebreak) {
            return player1Points + "-" + player2Points;
        }

        String score1 = convertToTennisScore(player1Points, player2Points);
        String score2 = convertToTennisScore(player2Points, player1Points);
        return score1 + " - " + score2;
    }

    private String convertToTennisScore(int points, int opponentPoints) {
        if (points >= 3 && opponentPoints >= 3) {
            if (points == opponentPoints) return "40";
            if (points > opponentPoints) return "AD";
            return "40";
        }
        switch (points) {
            case 0: return "0";
            case 1: return "15";
            case 2: return "30";
            case 3: return "40";
            default: return "40";
        }
    }

    @Override
    public String toString() {
        String status = isComplete ? " - Winner: " + (winner != null ? winner.getDisplayName() : "Unknown") : "";
        return "Game: " + player1.getDisplayName() + " vs " + player2.getDisplayName() +
               " (Server: " + server.getDisplayName() + ") - " + getScoreString() + status;
    }
}
