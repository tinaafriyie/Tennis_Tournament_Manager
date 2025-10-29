import java.time.LocalDate;

/**
 * Main class used to test all parts of the Tennis project.
 * Demonstrates the creation of Person, Player, Exchange, Referee, and Tournament.
 * @author Thomas
 * @version 1.0
 */
public class main {

    /**
     * The main method that runs all the tests for the project.
     * It simulates player creation, matches, referee actions, and a tournament.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {

        // =====================================================
        // 1) TEST PERSON
        // =====================================================
        System.out.println("=== TEST PERSON ===");
        Person person1 = new Person("Smith", "Anna", "Female",
                LocalDate.of(1995, 3, 14), "London",
                "British", 170, 60);

        System.out.println(person1);
        person1.marry("Brown");
        System.out.println("After marriage: " + person1.getCurrentName());
        System.out.println("Age: " + person1.getAge());
        System.out.println();

        // =====================================================
        // 2) TEST PLAYER
        // =====================================================
        System.out.println("=== TEST PLAYER ===");
        Player player1 = new Player("Williams", "Serena", "Female",
                LocalDate.of(1981, 9, 26), "Saginaw",
                "American", 175, 70,
                "Right", "Nike", "Patrick", "Pink");

        Player player2 = new Player("Nadal", "Rafael", "Male",
                LocalDate.of(1986, 6, 3), "Manacor",
                "Spanish", 185, 80,
                "Left", "Babolat", "Carlos", "Blue");

        System.out.println(player1);
        System.out.println(player2);

        player1.encourage();
        player2.changeOutfitColor("Red");
        System.out.println();

        // =====================================================
        // 2.5) TEST PLAYER AS SPECTATOR
        // =====================================================
        System.out.println("=== TEST PLAYER AS SPECTATOR ===");
        player1.watchMatch("Rafael vs Novak");
        player1.reactToPoint("Rafael");
        player1.applaud();
        System.out.println();

        // =====================================================
        // 3) TEST EXCHANGE
        // =====================================================
        System.out.println("=== TEST EXCHANGE ===");
        Exchange exchange1 = new Exchange(player1, player2);
        exchange1.playExchange();
        System.out.println("Winner of the point: " + exchange1.getWinner().getFirstName());
        System.out.println();

        // =====================================================
        // 4) TEST REFEREE
        // =====================================================
        System.out.println("=== TEST REFEREE ===");
        Referee referee = new Referee("Dupont", "Lucas", "Male",
                LocalDate.of(1980, 1, 12), "Paris",
                "French", 178, 70, 6);

        System.out.println(referee);
        referee.announceGameScore(player1, 30, player2, 15);
        referee.handleDispute(player1, "Ball was on the line");
        System.out.println();

        // =====================================================
        // 5) TEST TOURNAMENT
        // =====================================================
        System.out.println("=== TEST TOURNAMENT ===");
        Tournament tournament = new Tournament("Paris", 2025, "Clay");

        tournament.createPlayers(8);      // small number for demo
        tournament.createReferees(3);
        tournament.createSpectators(100);

        System.out.println(tournament);
        tournament.startFirstRound();
        System.out.println();

        // =====================================================
        // END OF TESTS
        // =====================================================
        System.out.println("All tests completed successfully.");
    }
}
