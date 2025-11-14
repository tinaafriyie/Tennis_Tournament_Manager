/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;


/**
 * Represents a game in a tennis set.
 * A game is won when a player reaches 4 points with at least 2 points advantage.
 * Implements Playable interface for polymorphic behavior.
 */
public class Game implements Playable {
    
    private Player player1;
    private Player player2;
    private Player server;
    private int player1Points;
    private int player2Points;
    private Player winner;
    private boolean isComplete;
    private boolean isTiebreak;
    private Referee referee;
    
    /**
     * Constructor for regular game
     * @param player1 the first player
     * @param player2 the second player
     * @param server the player serving this game
     * @param referee the referee officiating
     */
    public Game(Player player1, Player player2, Player server, Referee referee) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        if (server == null) {
            throw new IllegalArgumentException("Server cannot be null");
        }
        if (server != player1 && server != player2) {
            throw new IllegalArgumentException("Server must be one of the two players");
        }
        if (referee == null) {
            throw new IllegalArgumentException("Referee cannot be null");
        }
        
        this.player1 = player1;
        this.player2 = player2;
        this.server = server;
        this.referee = referee;
        this.player1Points = 0;
        this.player2Points = 0;
        this.winner = null;
        this.isComplete = false;
        this.isTiebreak = false;
    }
    
    /**
     * Constructor for tiebreak game
     * @param player1 the first player
     * @param player2 the second player
     * @param server the player serving first
     * @param referee the referee officiating
     * @param isTiebreak true if this is a tiebreak game
     */
    public Game(Player player1, Player player2, Player server, Referee referee, boolean isTiebreak) {
        this(player1, player2, server, referee);
        this.isTiebreak = isTiebreak;
    }
    
    /**
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
    
    /**
     * Plays a regular game
     */
    private void playRegularGame() {
        Player receiver = (server == player1) ? player2 : player1;
        
        while (!isComplete) {
            // Play a rally
            Rally rally = new Rally(server, receiver);
            Rally.Outcome outcome = rally.play(referee);
            
            // Update points
            if (outcome == Rally.Outcome.PLAYER1_WINS || outcome == Rally.Outcome.ACE) {
                if (server == player1) {
                    player1Points++;
                } else {
                    player2Points++;
                }
            } else {
                if (server == player1) {
                    player2Points++;
                } else {
                    player1Points++;
                }
            }
            
            // Announce score
            int p1Score = (server == player1) ? player1Points : player2Points;
            int p2Score = (server == player1) ? player2Points : player1Points;
            referee.announceScore(server.getFullName(), p1Score, 
                                 receiver.getFullName(), p2Score);
            
            // Check for game winner
            checkGameWinner();
        }
    }
    
    /**
     * Plays a tiebreak game (first to 7 points with 2-point margin)
     */
    private void playTiebreak() {
        System.out.println("*** TIEBREAK ***");
        Player currentServer = server;
        Player receiver = (server == player1) ? player2 : player1;
        int pointsPlayed = 0;
        
        while (!isComplete) {
            // Play a rally
            Rally rally = new Rally(currentServer, receiver);
            Rally.Outcome outcome = rally.play(referee);
            
            // Update points
            if (outcome == Rally.Outcome.PLAYER1_WINS || outcome == Rally.Outcome.ACE) {
                if (currentServer == player1) {
                    player1Points++;
                } else {
                    player2Points++;
                }
            } else {
                if (currentServer == player1) {
                    player2Points++;
                } else {
                    player1Points++;
                }
            }
            
            pointsPlayed++;
            
            // Announce score
            System.out.println(player1.getDisplayName() + " " + player1Points + 
                             " - " + player2Points + " " + player2.getDisplayName());
            
            // Switch server after first point, then every 2 points
            if (pointsPlayed == 1 || (pointsPlayed > 1 && (pointsPlayed - 1) % 2 == 0)) {
                Player temp = currentServer;
                currentServer = receiver;
                receiver = temp;
                referee.announceServer(currentServer.getFullName());
            }
            
            // Check for tiebreak winner (first to 7 with 2-point margin)
            if (player1Points >= 7 || player2Points >= 7) {
                if (Math.abs(player1Points - player2Points) >= 2) {
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
                }
            }
        }
        
        referee.announceGameWinner(winner.getFullName());
    }
    
    /**
     * Checks if a player has won the game
     */
    private void checkGameWinner() {
        // Regular game: win with 4+ points and 2-point advantage
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
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
    
    public Player getServer() {
        return server;
    }
    
    public int getPlayer1Points() {
        return player1Points;
    }
    
    public int getPlayer2Points() {
        return player2Points;
    }
    
    public Player getWinner() {
        return winner;
    }
    
    public boolean isComplete() {
        return isComplete;
    }
    
    public boolean isTiebreak() {
        return isTiebreak;
    }
    
    /**
     * Gets the score string for display
     */
    public String getScoreString() {
        if (isTiebreak) {
            return player1Points + "-" + player2Points;
        }
        
        // Convert to tennis scoring
        String score1 = convertToTennisScore(player1Points, player2Points);
        String score2 = convertToTennisScore(player2Points, player1Points);
        return score1 + " - " + score2;
    }
    
    /**
     * Converts numeric score to tennis scoring
     */
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
        return "Game: " + player1.getDisplayName() + " vs " + player2.getDisplayName() +
               " (Server: " + server.getDisplayName() + ") - " + getScoreString() +
               (isComplete ? " - Winner: " + winner.getDisplayName() : "");
    }
}