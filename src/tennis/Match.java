/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author tina-
 */




/**
 * Represents a tennis match between two players.
 * Uses Rally -> Game -> Set -> Match flow. Random can be injected for determinism.
 */
public class Match {

    public enum Category { MENS_SINGLES, WOMENS_SINGLES }

    private final Player player1;
    private final Player player2;
    private final Referee referee;
    private final Category category;
    private final String level;
    private final int atpWtaPoints;
    private Player winner;
    private Player loser;
    private boolean isComplete;
    private final List<Set> sets;
    private Player currentServer;
    private final Random random;

    public Match(Player player1, Player player2, Referee referee,
                 Category category, String level, int atpWtaPoints) {
        this(player1, player2, referee, category, level, atpWtaPoints, null);
    }

    public Match(Player player1, Player player2, Referee referee,
                 Category category, String level, int atpWtaPoints, Random rng) {
        this.player1 = Objects.requireNonNull(player1);
        this.player2 = Objects.requireNonNull(player2);
        this.referee = Objects.requireNonNull(referee);
        this.category = Objects.requireNonNull(category);
        if (level == null || level.trim().isEmpty()) throw new IllegalArgumentException("Level cannot be null or empty");
        if (atpWtaPoints < 0) throw new IllegalArgumentException("ATP/WTA points cannot be negative");

        // Gender validation
        if (category == Category.MENS_SINGLES &&
            (player1.getGender() != Player.Gender.MALE || player2.getGender() != Player.Gender.MALE)) {
            throw new IllegalArgumentException("Men's singles requires male players");
        }
        if (category == Category.WOMENS_SINGLES &&
            (player1.getGender() != Player.Gender.FEMALE || player2.getGender() != Player.Gender.FEMALE)) {
            throw new IllegalArgumentException("Women's singles requires female players");
        }

        this.level = level;
        this.atpWtaPoints = atpWtaPoints;
        this.winner = null;
        this.loser = null;
        this.isComplete = false;
        this.sets = new ArrayList<>();
        this.random = (rng == null) ? new Random() : rng;
        this.currentServer = random.nextBoolean() ? player1 : player2;
    }

    public void play() {
        referee.announceMatchStart(player1.getFullName(), player2.getFullName());

        int setsToWin = getSetsToWin();

        while (!isComplete) {
            boolean isDecidingSet = (getPlayer1Sets() == setsToWin - 1 && getPlayer2Sets() == setsToWin - 1);

            Set set = new Set(player1, player2, currentServer, referee, isDecidingSet);
            System.out.println("\n=== SET " + (sets.size() + 1) + " ===\n");
            set.play();
            sets.add(set);

            Player setWinner = set.getWinner();
            if (setWinner == null) {
                throw new IllegalStateException("Set ended without a winner in Match.play()");
            }

            System.out.println("\nCurrent Match Score: " + player1.getDisplayName() + " " +
                    getPlayer1Sets() + " - " + getPlayer2Sets() + " " + player2.getDisplayName());
            displaySetScores();

            if (getPlayer1Sets() == setsToWin) {
                winner = player1;
                loser = player2;
                isComplete = true;
            } else if (getPlayer2Sets() == setsToWin) {
                winner = player2;
                loser = player1;
                isComplete = true;
            } else {
                currentServer = (currentServer == player1) ? player2 : player1;
            }
        }

        finalizeMatch();
    }

    public void playNextSet() {
        if (isComplete) {
            System.out.println("Match is already complete!");
            return;
        }

        int setsToWin = getSetsToWin();
        boolean isDecidingSet = (getPlayer1Sets() == setsToWin - 1 && getPlayer2Sets() == setsToWin - 1);

        Set set = new Set(player1, player2, currentServer, referee, isDecidingSet);
        System.out.println("\n=== SET " + (sets.size() + 1) + " ===\n");
        set.play();
        sets.add(set);

        Player setWinner = set.getWinner();
        if (setWinner == null) {
            throw new IllegalStateException("Set ended without a winner in Match.playNextSet()");
        }

        System.out.println("\nCurrent Match Score: " + player1.getDisplayName() + " " +
                getPlayer1Sets() + " - " + getPlayer2Sets() + " " + player2.getDisplayName());
        displaySetScores();

        if (getPlayer1Sets() == setsToWin) {
            winner = player1;
            loser = player2;
            isComplete = true;
            finalizeMatch();
        } else if (getPlayer2Sets() == setsToWin) {
            winner = player2;
            loser = player1;
            isComplete = true;
            finalizeMatch();
        } else {
            currentServer = (currentServer == player1) ? player2 : player1;
        }
    }

    private void finalizeMatch() {
        if (winner == null) {
            throw new IllegalStateException("Attempt to finalize match with no winner");
        }
        winner.recordMatchWin();
        loser.recordMatchLoss();
        winner.updateRanking(true, loser.getRanking());
        loser.updateRanking(false, winner.getRanking());
        referee.recordMatchOfficiated();

        referee.announceMatchWinner(winner.getFullName());
        System.out.println("\nFinal Score: " + getMatchScoreString());
    }

    private int getSetsToWin() {
        return (category == Category.MENS_SINGLES) ? 3 : 2;
    }

    public int getPlayer1Sets() {
        int count = 0;
        for (Set s : sets) if (s.getWinner() == player1) count++;
        return count;
    }

    public int getPlayer2Sets() {
        int count = 0;
        for (Set s : sets) if (s.getWinner() == player2) count++;
        return count;
    }

    private void displaySetScores() {
        System.out.print("Set Scores: ");
        for (int i = 0; i < sets.size(); i++) {
            Set set = sets.get(i);
            System.out.print(set.getPlayer1Games() + "-" + set.getPlayer2Games());
            if (i < sets.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }

    public String getMatchScoreString() {
        StringBuilder sb = new StringBuilder();
        sb.append(player1.getDisplayName()).append(" vs ").append(player2.getDisplayName()).append("\n");
        sb.append("Sets: ").append(getPlayer1Sets()).append("-").append(getPlayer2Sets()).append("\n");

        for (int i = 0; i < sets.size(); i++) {
            Set s = sets.get(i);
            sb.append("Set ").append(i + 1).append(": ")
              .append(s.getPlayer1Games()).append("-").append(s.getPlayer2Games())
              .append("\n");
        }

        if (isComplete) {
            sb.append("Winner: ").append(winner.getFullName());
        }

        return sb.toString();
    }

    // Getters
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Referee getReferee() { return referee; }
    public Category getCategory() { return category; }
    public String getLevel() { return level; }
    public int getAtpWtaPoints() { return atpWtaPoints; }
    public Player getWinner() { return winner; }
    public Player getLoser() { return loser; }
    public boolean isComplete() { return isComplete; }
    public List<Set> getSets() { return new ArrayList<>(sets); }
    public Player getCurrentServer() { return currentServer; }

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
