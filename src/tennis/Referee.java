import java.util.Random;
import java.time.LocalDate;

/**
 * Represents a tennis referee.
 * The referee can announce scores and resolve disputes between players.
 * @author Thomas
 * @version 1.0
 */
public class Referee extends Person {

    private int mood; // referee fairness level from 1 to 10

    /**
     * Constructor for Referee.
     */
    public Referee(String birthName, String firstName, String gender,
                   LocalDate birthDate, String birthPlace,
                   String nationality, double height, double weight, int mood) {
        super(birthName, firstName, gender, birthDate, birthPlace, nationality, height, weight);
        this.mood = mood;
    }

    /** Announces the current game score. */
    public void announceGameScore(Player server, int serverPoints, Player receiver, int receiverPoints) {
        System.out.println("\nReferee " + getFirstName() + " says:");
        System.out.println(server.getFirstName() + ": " + serverPoints + " - " + receiver.getFirstName() + ": " + receiverPoints);
    }

    /** Announces the winner of a game. */
    public void announceGameWinner(Player winner) {
        System.out.println("Game won by " + winner.getFirstName() + "!");
    }

    /** Handles a dispute randomly depending on referee mood. */
    public void handleDispute(Player player, String reason) {
        System.out.println("\n" + player.getFirstName() + " calls the referee for: " + reason);
        System.out.println(getFirstName() + " is thinking...");
        Random random = new Random();
        int chance = random.nextInt(10) + 1;

        if (chance <= mood) {
            System.out.println("Decision: " + player.getFirstName() + " was right. Point given!");
        } else {
            System.out.println("Decision: " + player.getFirstName() + " was wrong. Point denied!");
        }
    }

    @Override
    public String toString() {
        return "Referee: " + getFirstName() + " (" + getNationality() + "), mood level: " + mood;
    }
}
