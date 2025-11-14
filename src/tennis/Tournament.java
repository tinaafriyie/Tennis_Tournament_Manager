/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;


import java.time.LocalDate;
import java.util.*;

/**
 * Represents a Grand Slam tennis tournament.
 * Manages 128 players per category through 7 rounds of matches.
 */
public class Tournament {
    
    /**
     * Enum for Grand Slam tournaments
     */
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
    
    private GrandSlam grandSlam;
    private int year;
    private List<Player> mensPlayers;
    private List<Player> womensPlayers;
    private List<Referee> referees;
    private List<Spectator> spectators;
    private Map<String, List<Match>> matchesByRound;  // Round name -> List of matches
    private String currentRound;
    private Random random;
    
    // Tournament statistics
    private int totalSpectators;
    private int totalBallsUsed;
    private int glassesSold;
    private int hatsSold;
    
    // Round names
    private static final String[] ROUND_NAMES = {
        "First Round", "Second Round", "Third Round", "Round of 16",
        "Quarterfinals", "Semifinals", "Finals"
    };
    
    // ATP/WTA points per round
    private static final int[] POINTS_PER_ROUND = {
        10, 45, 90, 180, 360, 720, 1200
    };
    
    /**
     * Constructor for Tournament
     * @param grandSlam the Grand Slam tournament type
     * @param year the year of the tournament
     */
    public Tournament(GrandSlam grandSlam, int year) {
        if (grandSlam == null) {
            throw new IllegalArgumentException("Grand Slam cannot be null");
        }
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Invalid year");
        }
        
        this.grandSlam = grandSlam;
        this.year = year;
        this.mensPlayers = new ArrayList<>();
        this.womensPlayers = new ArrayList<>();
        this.referees = new ArrayList<>();
        this.spectators = new ArrayList<>();
        this.matchesByRound = new LinkedHashMap<>();
        this.random = new Random();
        this.currentRound = ROUND_NAMES[0];
        
        // Initialize rounds
        for (String round : ROUND_NAMES) {
            matchesByRound.put(round, new ArrayList<>());
        }
        
        // Initialize statistics
        this.totalSpectators = 0;
        this.totalBallsUsed = 0;
        this.glassesSold = 0;
        this.hatsSold = 0;
    }
    
    /**
     * Generates players for the tournament
     */
    public void generatePlayers() {
        generateMensPlayers(128);
        generateWomensPlayers(128);
        System.out.println("Generated 128 men's players and 128 women's players");
    }
    
    /**
     * Generates men's players
     */
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
    
    /**
     * Generates women's players
     */
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
    
    /**
     * Generates referees for the tournament
     */
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
                                         "Various", height, weight, experience);
            
            referees.add(referee);
        }
        
        System.out.println("Generated " + count + " referees");
    }
    
    /**
     * Generates spectators for a specific round
     */
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
     * Initializes first round matches
     */
    public void initializeFirstRound() {
        // Shuffle players for random matchups
        List<Player> shuffledMens = new ArrayList<>(mensPlayers);
        List<Player> shuffledWomens = new ArrayList<>(womensPlayers);
        Collections.shuffle(shuffledMens);
        Collections.shuffle(shuffledWomens);
        
        // Create men's matches (64 matches)
        createMatchesForRound(shuffledMens, Match.Category.MENS_SINGLES, 
                            ROUND_NAMES[0], POINTS_PER_ROUND[0]);
        
        // Create women's matches (64 matches)
        createMatchesForRound(shuffledWomens, Match.Category.WOMENS_SINGLES, 
                            ROUND_NAMES[0], POINTS_PER_ROUND[0]);
        
        System.out.println("First round initialized: 64 men's matches + 64 women's matches");
    }
    
    /**
     * Creates matches for a specific round
     */
    private void createMatchesForRound(List<Player> players, Match.Category category,
                                      String round, int points) {
        List<Match> matches = matchesByRound.get(round);
        
        for (int i = 0; i < players.size(); i += 2) {
            Player player1 = players.get(i);
            Player player2 = players.get(i + 1);
            Referee referee = referees.get(random.nextInt(referees.size()));
            
            Match match = new Match(player1, player2, referee, category, round, points);
            matches.add(match);
        }
    }
    
    /**
     * Plays all matches in the current round
     */
    public void playCurrentRound() {
        List<Match> matches = matchesByRound.get(currentRound);
        
        if (matches.isEmpty()) {
            System.out.println("No matches to play in " + currentRound);
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println(grandSlam + " " + year + " - " + currentRound);
        System.out.println("=".repeat(50) + "\n");
        
        // Generate spectators for this round
        int spectatorsPerMatch = 1000 + random.nextInt(5000);
        generateSpectators(currentRound, matches.size() * spectatorsPerMatch);
        
        // Play each match
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            System.out.println("\n--- Match " + (i + 1) + " of " + matches.size() + " ---");
            match.play();
            
            // Estimate balls used (approximately 6-9 balls per match)
            totalBallsUsed += 6 + random.nextInt(4);
        }
        
        // Advance to next round if not finals
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
     * Advances winners to the next round
     */
    private void advanceToNextRound() {
        int currentRoundIndex = Arrays.asList(ROUND_NAMES).indexOf(currentRound);
        if (currentRoundIndex >= ROUND_NAMES.length - 1) {
            return;  // Already at finals
        }
        
        String nextRound = ROUND_NAMES[currentRoundIndex + 1];
        int nextPoints = POINTS_PER_ROUND[currentRoundIndex + 1];
        
        List<Match> currentMatches = matchesByRound.get(currentRound);
        List<Player> mensWinners = new ArrayList<>();
        List<Player> womensWinners = new ArrayList<>();
        
        // Collect winners
        for (Match match : currentMatches) {
            if (match.isComplete()) {
                if (match.getCategory() == Match.Category.MENS_SINGLES) {
                    mensWinners.add(match.getWinner());
                } else {
                    womensWinners.add(match.getWinner());
                }
            }
        }
        
        // Create next round matches
        if (!mensWinners.isEmpty()) {
            createMatchesForRound(mensWinners, Match.Category.MENS_SINGLES, 
                                nextRound, nextPoints);
        }
        if (!womensWinners.isEmpty()) {
            createMatchesForRound(womensWinners, Match.Category.WOMENS_SINGLES, 
                                nextRound, nextPoints);
        }
        
        currentRound = nextRound;
        System.out.println("\nAdvancing to " + nextRound + "...");
    }
    
    /**
     * Prints tournament summary
     */
    public void printTournamentSummary() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TOURNAMENT SUMMARY: " + grandSlam + " " + year);
        System.out.println("=".repeat(60));
        System.out.println("Location: " + grandSlam.getCity());
        System.out.println("Surface: " + grandSlam.getSurface());
        System.out.println();
        
        // Find champions
        List<Match> finals = matchesByRound.get("Finals");
        Player mensChampion = null;
        Player womensChampion = null;
        
        for (Match match : finals) {
            if (match.getCategory() == Match.Category.MENS_SINGLES) {
                mensChampion = match.getWinner();
            } else {
                womensChampion = match.getWinner();
            }
        }
        
        System.out.println("CHAMPIONS:");
        System.out.println("Men's Singles: " + (mensChampion != null ? mensChampion.getFullName() : "N/A"));
        System.out.println("Women's Singles: " + (womensChampion != null ? womensChampion.getFullName() : "N/A"));
        System.out.println();
        
        System.out.println("STATISTICS:");
        System.out.println("Total Spectators: " + totalSpectators);
        System.out.println("Average Spectators per Match: " + (totalSpectators / getTotalMatches()));
        System.out.println("Total Balls Used: " + totalBallsUsed);
        System.out.println("Glasses Sold: " + glassesSold);
        System.out.println("Hats Sold: " + hatsSold);
        System.out.println("Total Matches Played: " + getTotalMatches());
        System.out.println("Total Referees: " + referees.size());
        System.out.println("=".repeat(60));
    }
    
    /**
     * Gets total number of matches played
     */
    private int getTotalMatches() {
        int total = 0;
        for (List<Match> matches : matchesByRound.values()) {
            total += matches.size();
        }
        return total;
    }
    
    // Getters
    public GrandSlam getGrandSlam() {
        return grandSlam;
    }
    
    public int getYear() {
        return year;
    }
    
    public List<Player> getMensPlayers() {
        return new ArrayList<>(mensPlayers);
    }
    
    public List<Player> getWomensPlayers() {
        return new ArrayList<>(womensPlayers);
    }
    
    public List<Referee> getReferees() {
        return new ArrayList<>(referees);
    }
    
    public String getCurrentRound() {
        return currentRound;
    }
    
    public List<Match> getMatchesForRound(String round) {
        return new ArrayList<>(matchesByRound.getOrDefault(round, new ArrayList<>()));
    }
    
    @Override
    public String toString() {
        return grandSlam + " " + year + " (" + grandSlam.getCity() + ", " + 
               grandSlam.getSurface() + ")";
    }
}