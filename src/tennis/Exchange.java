import java.util.Scanner;
import java.util.Random;

/**
 * Represents a rally (exchange) between two players.
 * Handles the result of serves and determines the winner of a point.
 * @author Thomas
 * @version 1.0
 */
public class Exchange {

    private Player server;
    private Player receiver;
    private Player winner;

    /**
     * Constructor of Exchange.
     * @param server The serving player
     * @param receiver The receiving player
     */
    public Exchange(Player server, Player receiver) {
        this.server = server;
        this.receiver = receiver;
    }

    /**
     * Simulates an exchange with possible outcomes entered by the user.
     */
    public void playExchange() {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("\n=== New Exchange ===");
        System.out.println(server.getFirstName() + " is serving against " + receiver.getFirstName());

        boolean firstServe = true;

        while (true) {
            try {
                System.out.print("Enter result of the serve (fault / net / correct): ");
                String input = sc.nextLine().toLowerCase();

                if (input.equals("fault")) {
                    if (firstServe) {
                        System.out.println("Fault on first serve! Second serve...");
                        firstServe = false;
                    } else {
                        System.out.println("Double fault! Point for " + receiver.getFirstName());
                        winner = receiver;
                        break;
                    }
                } else if (input.equals("net")) {
                    System.out.println("Net! Replay first serve...");
                    firstServe = true;
                } else if (input.equals("correct")) {
                    winner = (rand.nextInt(2) == 0) ? server : receiver;
                    System.out.println(winner.getFirstName() + " wins the point!");
                    break;
                } else {
                    System.out.println("Invalid input, please type fault, net, or correct.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input.");
            }
        }
    }

    /** @return The winner of the exchange */
    public Player getWinner() {
        return winner;
    }
}
