/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author tina-
 */

/*
 * Represents a set in a tennis match.
 * A set is won when a player wins 6+ games with at least 2-game advantage,
 * or wins a tiebreak at 6-6.
 */
public class Set {
    
    private Player player1;
    private Player player2;
    private int player1Games;
    private int player2Games;
    private Player winner;
    private boolean isComplete;
    private boolean isDecidingSet;  // Final set of the match
    private Referee referee;
    private Player currentServer;
    private List<Game> games;
    
    /*
     * Constructor for Set
     */
    public Set(Player player1, Player player2, Player initialServer, 
               Referee referee, boolean isDecidingSet) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        if (initialServer == null) {
            throw new IllegalArgumentException("Initial server cannot be null");
        }
        if (initialServer != player1 && initialServer != player2) {
            throw new IllegalArgumentException("Initial server must be one of the two players");
        }
        if (referee == null) {
            throw new IllegalArgumentException("Referee cannot be null");
        }
        
        this.player1 = player1;
        this.player2 = player2;
        this.currentServer = initialServer;
        this.referee = referee;
        this.isDecidingSet = isDecidingSet;
        this.player1Games = 0;
        this.player2Games = 0;
        this.winner = null;
        this.isComplete = false;
        this.games = new ArrayList<>();
    }
    
    /*
     * Plays the set until completion
     */
    public void play() {
        while (!isComplete) {
            // Create and play a game
            Game game;
            
            // Check for tiebreak (at 6-6, unless it's deciding set)
            if (player1Games == 6 && player2Games == 6 && !isDecidingSet) {
                game = new Game(player1, player2, currentServer, referee, true);
            } else {
                game = new Game(player1, player2, currentServer, referee);
            }
            
            game.play();
            games.add(game);
            
            // Update game scores
            Player gameWinner = game.getWinner();
            if (gameWinner == player1) {
                player1Games++;
            } else {
                player2Games++;
            }
            
            // Check for set winner
            checkSetWinner();
            
            // Alternate server for next game
            if (!isComplete) {
                currentServer = (currentServer == player1) ? player2 : player1;
            }
        }
        
        // Record set statistics
        if (winner == player1) {
            player1.recordSetWin();
            player2.recordSetLoss();
        } else {
            player2.recordSetWin();
            player1.recordSetLoss();
        }
        
        referee.announceSetWinner(winner.getFullName(), player1Games, player2Games);
    }
    
    /**
     * Checks if a player has won the set
     */
    private void checkSetWinner() {
        // Win by reaching 6 games with 2-game advantage
        if (player1Games >= 6 || player2Games >= 6) {
            int diff = Math.abs(player1Games - player2Games);
            
            // For deciding set, may need to win by 2 games beyond 6-6
            // For other sets, tiebreak at 6-6
            if (diff >= 2) {
                if (player1Games > player2Games) {
                    winner = player1;
                } else {
                    winner = player2;
                }
                isComplete = true;
            } else if (player1Games == 7 || player2Games == 7) {
                // One player reached 7 (won tiebreak or won 7-5)
                if (player1Games > player2Games) {
                    winner = player1;
                } else {
                    winner = player2;
                }
                isComplete = true;
            }
        }
    }
    
    // Getters
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
    
    public int getPlayer1Games() {
        return player1Games;
    }
    
    public int getPlayer2Games() {
        return player2Games;
    }
    
    public Player getWinner() {
        return winner;
    }
    
    public boolean isComplete() {
        return isComplete;
    }
    
    public boolean isDecidingSet() {
        return isDecidingSet;
    }
    
    public Player getCurrentServer() {
        return currentServer;
    }
    
    public List<Game> getGames() {
        return new ArrayList<>(games);  // Return copy for encapsulation
    }
    
    /*
     * Gets the score string for display
     */
    public String getScoreString() {
        return player1Games + "-" + player2Games;
    }
    
    /*
     * Gets total games played in the set
     */
    public int getTotalGames() {
        return player1Games + player2Games;
    }
    
    @Override
    public String toString() {
        return "Set: " + player1.getDisplayName() + " " + player1Games + 
               " - " + player2Games + " " + player2.getDisplayName() +
               (isComplete ? " (Winner: " + winner.getDisplayName() + ")" : " (In Progress)") +
               (isDecidingSet ? " [DECIDING SET]" : "");
    }
}
