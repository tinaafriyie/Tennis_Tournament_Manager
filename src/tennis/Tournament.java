import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a tennis tournament with players, referees, and spectators.
 * Allows automatic creation of participants and simulates the first round of matches.
 * @author Thomas
 * @version 1.0
 */
public class Tournament {

    private String city;
    private int year;
    private String surface;
    private ArrayList<Player> players;
    private ArrayList<Referee> referees;
    private ArrayList<String> spectators;

    /** Constructor for Tournament. */
    public Tournament(String city, int year, String surface) {
        this.city = city;
        this.year = year;
        this.surface = surface;
        this.players = new ArrayList<Player>();
        this.referees = new ArrayList<Referee>();
        this.spectators = new ArrayList<String>();
    }

    /** Creates a list of players. */
    public void createPlayers(int number) {
        for (int i = 1; i <= number; i++) {
            String name = "Player" + i;
            String gender = (i % 2 == 0) ? "Male" : "Female";
            Player p = new Player(name, name, gender,
                    java.time.LocalDate.of(1990, 1, 1), "Unknown",
                    "Country" + i, 180, 75,
                    "Right", "Sponsor" + i, "Coach" + i, "Blue");
            players.add(p);
        }
    }

    /** Creates a list of referees. */
    public void createReferees(int number) {
        for (int i = 1; i <= number; i++) {
            Referee r = new Referee("Ref" + i, "Ref" + i, "Male",
                    java.time.LocalDate.of(1980, 1, 1), "City" + i,
                    "Country" + i, 180, 70, 5);
            referees.add(r);
        }
    }

    /** Creates a list of spectators. */
    public void createSpectators(int number) {
        for (int i = 1; i <= number; i++) {
            spectators.add("Spectator" + i);
        }
    }

    /** Starts the first round of the tournament with random matches. */
    public void startFirstRound() {
        if (players.size() < 2) {
            System.out.println("Error: not enough players to start the tournament.");
            return;
        }

        System.out.println("\n=== " + city + " " + year + " - Surface: " + surface + " ===");
        System.out.println("Number of players: " + players.size());
        System.out.println("Number of referees: " + referees.size());
        System.out.println("Number of spectators: " + spectators.size());
        System.out.println("\n--- First Round Matches ---");

        Random rand = new Random();
        for (int i = 0; i < players.size() - 1; i += 2) {
            Player p1 = players.get(i);
            Player p2 = players.get(i + 1);
            Referee ref = referees.get(rand.nextInt(referees.size()));

            System.out.println("\nMatch: " + p1.getFirstName() + " vs " + p2.getFirstName());
            System.out.println("Referee: " + ref.getFirstName());
            int winner = rand.nextInt(2);
            Player winnerPlayer = (winner == 0) ? p1 : p2;
            ref.announceGameWinner(winnerPlayer);
        }
    }

    @Override
    public String toString() {
        return "Tournament in " + city + " (" + year + ") - Surface: " + surface;
    }
}
