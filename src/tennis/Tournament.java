/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;

/**
 *
 * @author tina-
 */


import java.time.LocalDate;
import java.util.*;

/**
 * Represents a Grand Slam tennis tournament.
 * Manages 128 players per category through 7 rounds of matches.
 * Uses Random injection in methods where needed for determinism in tests.
 */
public class Tournament {

    public enum GrandSlam {
        AUSTRALIAN_OPEN("Melbourne", "Plexicushion", 1, 2),
        ROLAND_GARROS("Paris", "Clay", 5, 6),
        WIMBLEDON("London", "Grass", 6, 7),
        US_OPEN("New York", "Decoturf", 8, 9);

        private final String city;
        private final String surface;
        private final int startMonth;
        private final int endMonth;

        GrandSlam(String city, String surface, int startMonth, int endMonth) {
            this.city = city;
            this.surface = surface;
            this.startMonth = startMonth;
            this.endMonth = endMonth;
        }

        public String getCity() { return city; }
        public String getSurface() { return surface; }
        public int getStartMonth() { return startMonth; }
        public int getEndMonth() { return endMonth; }
    }

    private final GrandSlam grandSlam;
    private final int year;
    private final List<Player> mensPlayers;
    private final List<Player> womensPlayers;
    private final List<Referee> referees;
    private final List<Spectator> spectators;
    private final Map<String, List<Match>> matchesByRound;
    private String currentRound;
    private final Random random;

    // Tournament statistics
    private int totalSpectators;
    private int totalBallsUsed;
    private int glassesSold;
    private int hatsSold;

    private static final String[] ROUND_NAMES = {
        "First Round", "Second Round", "Third Round", "Round of 16",
        "Quarterfinals", "Semifinals", "Finals"
    };

    private static final int[] POINTS_PER_ROUND = {
        10, 45, 90, 180, 360, 720, 1200
    };

    /**
     * Constructor with optional Random injection for deterministic testing.
     */
    public Tournament(GrandSlam grandSlam, int year) {
        this(grandSlam, year, null);
    }

    public Tournament(GrandSlam grandSlam, int year, Random rng) {
        if (grandSlam == null) throw new IllegalArgumentException("Grand Slam cannot be null");
        if (year < 1900 || year > 2100) throw new IllegalArgumentException("Invalid year");

        this.grandSlam = grandSlam;
        this.year = year;
        this.mensPlayers = new ArrayList<>();
        this.womensPlayers = new ArrayList<>();
        this.referees = new ArrayList<>();
        this.spectators = new ArrayList<>();
        this.matchesByRound = new LinkedHashMap<>();
        this.random = (rng == null) ? new Random() : rng;
        this.currentRound = ROUND_NAMES[0];

        for (String round : ROUND_NAMES) matchesByRound.put(round, new ArrayList<>());

        this.totalSpectators = 0;
        this.totalBallsUsed = 0;
        this.glassesSold = 0;
        this.hatsSold = 0;
    }

    // Player generation: keeps logic but uses injected Random
    public void generatePlayers() {
        generateMensPlayers(128);
        generateWomensPlayers(128);
        System.out.println("Generated 128 men's players and 128 women's players");
    }

    private void generateMensPlayers(int count) {
        String[] firstNames = {"Roger", "Rafael", "Novak", "Andy", "Stan", "Dominic",
                               "Alexander", "Stefanos", "Daniil", "Carlos"};
        String[] lastNames = {"Federer", "Nadal", "Djokovic", "Murray", "Wawrinka",
                              "Thiem", "Zverev", "Tsitsipas", "Medvedev", "Alcaraz"};

        for (int i = 0; i < count; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)] + (i / 10);
            String lastName = lastNames[random.nextInt(lastNames.length)] + (i % 10);

            LocalDate birthDate = LocalDate.of(1985 + random.nextInt(15),
                                              1 + random.nextInt(12),
                                              1 + random.nextInt(28));

            Player.Hand hand = random.nextDouble() < 0.15 ? Player.Hand.LEFT : Player.Hand.RIGHT;
            double height = 175 + random.nextInt(20);
            double weight = 70 + random.nextInt(20);

            Player player = new Player(lastName, firstName, birthDate, "Various",
                                      "Various", height, weight, hand,
                                      Player.Gender.MALE, i + 1);

            mensPlayers.add(player);
        }
    }

    private void generateWomensPlayers(int count) {
        String[] firstNames = {"Serena", "Venus", "Maria", "Simona", "Naomi",
                               "Ashleigh", "Aryna", "Iga", "Coco", "Emma"};
        String[] lastNames = {"Williams", "Sharapova", "Halep", "Osaka", "Barty",
                              "Sabalenka", "Swiatek", "Gauff", "Raducanu", "Smith"};

        for (int i = 0; i < count; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)] + (i / 10);
            String lastName = lastNames[random.nextInt(lastNames.length)] + (i % 10);

            LocalDate birthDate = LocalDate.of(1985 + random.nextInt(15),
                                              1 + random.nextInt(12),
                                              1 + random.nextInt(28));

            Player.Hand hand = random.nextDouble() < 0.12 ? Player.Hand.LEFT : Player.Hand.RIGHT;
            double height = 165 + random.nextInt(20);
            double weight = 55 + random.nextInt(20);

            Player player = new Player(lastName, firstName, birthDate, "Various",
                                      "Various", height, weight, hand,
                                      Player.Gender.FEMALE, i + 1);

            womensPlayers.add(player);
        }
    }

    public void generateReferees(int count) {
        String[] firstNames = {"Carlos", "Mohamed", "Alison", "James", "Eva"};
        String[] lastNames = {"Ramos", "Lahyani", "Hughes", "Keothavong", "Asderaki"};

        for (int i = 0; i < count; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)] + (i / 5);
            String lastName = lastNames[random.nextInt(lastNames.length)] + (i % 5);

            LocalDate birthDate = LocalDate.of(1960 + random.nextInt(30),
                                              1 + random.nextInt(12),
                                              1 + random.nextInt(28));

            int experience = 5 + random.nextInt(20);
            double height = 170 + random.nextInt(20);
            double weight = 70 + random.nextInt(20);

            Referee referee = new Referee(lastName, firstName, birthDate, "Various",
                                         "Various", height, weight, experience, random, System.out);

            referees.add(referee);
        }

        System.out.println("Generated " + count + " referees");
    }

    public void generateSpectators(String round, int count) {
        String[] firstNames = {"John", "Emma", "Michael", "Sarah", "David", "Sophie"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Wilson", "Taylor", "Anderson"};

        double ticketPrice = Spectator.calculateTicketPrice(round);

        for (int i = 0; i < count; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];

            LocalDate birthDate = LocalDate.of(1950 + random.nextInt(60),
                                              1 + random.nextInt(12),
                                              1 + random.nextInt(28));

            Spectator.Gender gender = random.nextBoolean() ?
                                     Spectator.Gender.MALE : Spectator.Gender.FEMALE;

            double height = 160 + random.nextInt(30);
            double weight = 60 + random.nextInt(30);

            Spectator spectator = new Spectator(lastName, firstName, birthDate, "Various",
                                                "Various", height, weight, gender,
                                                i + 1, ticketPrice, round);

            spectators.add(spectator);
            totalSpectators++;

            if (gender == Spectator.Gender.FEMALE && spectator.wearsGlasses()) {
                glassesSold++;
            }
            if (spectator.wearsHat()) {
                hatsSold++;
            }
        }
    }

    /**
     * Initializes first round matches. Shuffles players before pairing.
     */
    public void initializeFirstRound() {
        List<Player> shuffledMens = new ArrayList<>(mensPlayers);
        List<Player> shuffledWomens = new ArrayList<>(womensPlayers);
        Collections.shuffle(shuffledMens, random);
        Collections.shuffle(shuffledWomens, random);

        // Clear any existing first-round matches before creating
        matchesByRound.put(ROUND_NAMES[0], new ArrayList<>());

        createMatchesForRound(shuffledMens, Match.Category.MENS_SINGLES,
                             ROUND_NAMES[0], POINTS_PER_ROUND[0]);
        createMatchesForRound(shuffledWomens, Match.Category.WOMENS_SINGLES,
                             ROUND_NAMES[0], POINTS_PER_ROUND[0]);

        System.out.println("First round initialized: " + (shuffledMens.size()/2) + " men's matches + " + (shuffledWomens.size()/2) + " women's matches");
    }

    private void createMatchesForRound(List<Player> players, Match.Category category,
                                       String round, int points) {
        Objects.requireNonNull(players);
        if (players.size() % 2 != 0) {
            throw new IllegalArgumentException("Players list must contain an even number of players for pairing");
        }
        List<Match> matches = matchesByRound.get(round);
        if (matches == null) {
            matches = new ArrayList<>();
            matchesByRound.put(round, matches);
        }

        for (int i = 0; i < players.size(); i += 2) {
            Player player1 = players.get(i);
            Player player2 = players.get(i + 1);
            if (referees.isEmpty()) {
                throw new IllegalStateException("No referees available; generate referees before creating matches");
            }
            Referee referee = referees.get(random.nextInt(referees.size()));
            Match match = new Match(player1, player2, referee, category, round, points, random);
            matches.add(match);
        }
    }

    /**
     * Plays all matches in the current round.
     */
    public void playCurrentRound() {
        List<Match> matches = matchesByRound.getOrDefault(currentRound, Collections.emptyList());

        if (matches.isEmpty()) {
            System.out.println("No matches to play in " + currentRound);
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println(grandSlam + " " + year + " - " + currentRound);
        System.out.println("=".repeat(50) + "\n");

        int spectatorsPerMatch = 1000 + random.nextInt(5000);
        generateSpectators(currentRound, matches.size() * spectatorsPerMatch);

        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            System.out.println("\n--- Match " + (i + 1) + " of " + matches.size() + " ---");
            match.play();
            totalBallsUsed += 6 + random.nextInt(4);
        }

        if (!currentRound.equals("Finals")) {
            advanceToNextRound();
        } else {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("TOURNAMENT COMPLETE!");
            System.out.println("=".repeat(50));
            printTournamentSummary();
        }
    }

    /**
     * Advances winners to the next round and creates fresh match lists for the next round.
     */
    private void advanceToNextRound() {
        int currentRoundIndex = Arrays.asList(ROUND_NAMES).indexOf(currentRound);
        if (currentRoundIndex < 0 || currentRoundIndex >= ROUND_NAMES.length - 1) {
            return;
        }

        String nextRound = ROUND_NAMES[currentRoundIndex + 1];
        int nextPoints = POINTS_PER_ROUND[currentRoundIndex + 1];

        List<Match> currentMatches = new ArrayList<>(matchesByRound.getOrDefault(currentRound, Collections.emptyList()));
        List<Player> mensWinners = new ArrayList<>();
        List<Player> womensWinners = new ArrayList<>();

        for (Match match : currentMatches) {
            if (match.isComplete()) {
                if (match.getCategory() == Match.Category.MENS_SINGLES) {
                    mensWinners.add(match.getWinner());
                } else {
                    womensWinners.add(match.getWinner());
                }
            }
        }

        // Prepare a fresh list for next round to avoid accidental appends
        matchesByRound.put(nextRound, new ArrayList<>());

        if (!mensWinners.isEmpty()) createMatchesForRound(mensWinners, Match.Category.MENS_SINGLES, nextRound, nextPoints);
        if (!womensWinners.isEmpty()) createMatchesForRound(womensWinners, Match.Category.WOMENS_SINGLES, nextRound, nextPoints);

        currentRound = nextRound;
        System.out.println("\nAdvancing to " + nextRound + "...");
    }

    public void printTournamentSummary() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TOURNAMENT SUMMARY: " + grandSlam + " " + year);
        System.out.println("=".repeat(60));
        System.out.println("Location: " + grandSlam.getCity());
        System.out.println("Surface: " + grandSlam.getSurface());
        System.out.println();

        List<Match> finals = matchesByRound.getOrDefault("Finals", Collections.emptyList());
        Player mensChampion = null;
        Player womensChampion = null;

        for (Match match : finals) {
            if (match.getCategory() == Match.Category.MENS_SINGLES) mensChampion = match.getWinner();
            else womensChampion = match.getWinner();
        }

        System.out.println("CHAMPIONS:");
        System.out.println("Men's Singles: " + (mensChampion != null ? mensChampion.getFullName() : "N/A"));
        System.out.println("Women's Singles: " + (womensChampion != null ? womensChampion.getFullName() : "N/A"));
        System.out.println();

        System.out.println("STATISTICS:");
        System.out.println("Total Spectators: " + totalSpectators);
        System.out.println("Average Spectators per Match: " + (getTotalMatches() > 0 ? (totalSpectators / getTotalMatches()) : 0));
        System.out.println("Total Balls Used: " + totalBallsUsed);
        System.out.println("Glasses Sold: " + glassesSold);
        System.out.println("Hats Sold: " + hatsSold);
        System.out.println("Total Matches Played: " + getTotalMatches());
        System.out.println("Total Referees: " + referees.size());
        System.out.println("=".repeat(60));
    }

    private int getTotalMatches() {
        int total = 0;
        for (List<Match> matches : matchesByRound.values()) total += matches.size();
        return total;
    }

    // Getters (defensive copies)
    public GrandSlam getGrandSlam() { return grandSlam; }
    public int getYear() { return year; }
    public List<Player> getMensPlayers() { return new ArrayList<>(mensPlayers); }
    public List<Player> getWomensPlayers() { return new ArrayList<>(womensPlayers); }
    public List<Referee> getReferees() { return new ArrayList<>(referees); }
    public String getCurrentRound() { return currentRound; }
    public List<Match> getMatchesForRound(String round) { return new ArrayList<>(matchesByRound.getOrDefault(round, Collections.emptyList())); }

    @Override
    public String toString() {
        return grandSlam + " " + year + " (" + grandSlam.getCity() + ", " + grandSlam.getSurface() + ")";
    }
}
