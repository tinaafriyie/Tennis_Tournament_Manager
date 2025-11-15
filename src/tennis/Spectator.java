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
import java.util.Random;

/**
 * Represents a spectator attending a tennis match.
 * Extends Person with spectator-specific attributes and behaviors.
 */
public class Spectator extends Person {
    
    /*
     * Enum for spectator gender
     */
    public enum Gender {
        MALE, FEMALE
    }
    
    private int seatNumber;
    private double ticketPrice;
    private String tournamentRound;  // Which round they're watching
    private Gender gender;
    
    // Distinctive features
    private String shirtColor;  // For men
    private boolean wearsGlasses;  // For women
    private boolean wearsHat;  // Not distinctive, but tracked
    
    private Random random;
    private double excitementLevel;  // 0.0 to 1.0, affects behavior
    
    /*
     * Constructor for Spectator
     */
    public Spectator(String birthName, String firstName, LocalDate birthDate,
                    String placeOfBirth, String nationality, double height,
                    double weight, Gender gender, int seatNumber, 
                    double ticketPrice, String tournamentRound) {
        super(birthName, firstName, birthDate, placeOfBirth, nationality, height, weight);
        
        if (seatNumber <= 0) {
            throw new IllegalArgumentException("Seat number must be positive");
        }
        if (ticketPrice < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative");
        }
        if (tournamentRound == null || tournamentRound.trim().isEmpty()) {
            throw new IllegalArgumentException("Tournament round cannot be null or empty");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        
        this.gender = gender;
        this.seatNumber = seatNumber;
        this.ticketPrice = ticketPrice;
        this.tournamentRound = tournamentRound;
        this.random = new Random();
        this.excitementLevel = 0.5 + (random.nextDouble() * 0.5);  // 0.5 to 1.0
        
        // Set distinctive features based on gender
        if (gender == Gender.MALE) {
            this.shirtColor = generateRandomColor();
            this.wearsGlasses = false;  // Not distinctive for men
        } else {
            this.shirtColor = null;  // Not tracked for women
            this.wearsGlasses = random.nextBoolean();
        }
        
        this.wearsHat = random.nextBoolean();
    }
    
    /**
     * Generates a random shirt color
     */
    private String generateRandomColor() {
        String[] colors = {"red", "blue", "green", "yellow", "white", 
                          "black", "orange", "purple", "pink", "gray"};
        return colors[random.nextInt(colors.length)];
    }
    
    // Getters and setters
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public double getTicketPrice() {
        return ticketPrice;
    }
    
    public String getTournamentRound() {
        return tournamentRound;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public String getShirtColor() {
        return shirtColor;
    }
    
    /*
     * Changes shirt color (for men only)
     */
    public void changeShirtColor(String newColor) {
        if (gender != Gender.MALE) {
            throw new IllegalStateException("Only male spectators change shirt colors as distinctive feature");
        }
        if (newColor == null || newColor.trim().isEmpty()) {
            throw new IllegalArgumentException("Color cannot be null or empty");
        }
        this.shirtColor = newColor;
    }
    
    public boolean wearsGlasses() {
        return wearsGlasses;
    }
    
    /**
     * Changes glasses status (for women only)
     */
    public void setWearsGlasses(boolean wearsGlasses) {
        if (gender != Gender.FEMALE) {
            throw new IllegalStateException("Only female spectators have glasses as distinctive feature");
        }
        this.wearsGlasses = wearsGlasses;
    }
    
    public boolean wearsHat() {
        return wearsHat;
    }
    
    public void setWearsHat(boolean wearsHat) {
        this.wearsHat = wearsHat;
    }
    
    public double getExcitementLevel() {
        return excitementLevel;
    }
    
    /**
     * Updates excitement level based on match progress
     */
    public void updateExcitementLevel(double change) {
        this.excitementLevel = Math.max(0.0, Math.min(1.0, this.excitementLevel + change));
    }
    
    /**
     * Spectator applauds
     */
    public void applaud() {
        if (random.nextDouble() < excitementLevel) {
            System.out.println("[Spectator " + seatNumber + "] *applauds*");
        }
    }
    
    /**
     * Spectator cheers enthusiastically
     */
    public void cheer() {
        if (random.nextDouble() < excitementLevel * 0.8) {
            String[] cheers = {"Yes!", "Bravo!", "Incredible!", "Amazing!", "Come on!"};
            String cheer = cheers[random.nextInt(cheers.length)];
            System.out.println("[Spectator " + seatNumber + "] " + cheer);
        }
    }
    
    /**
     * Spectator boos (rare, only when upset)
     */
    public void boo() {
        if (random.nextDouble() < 0.1 && excitementLevel < 0.3) {
            System.out.println("[Spectator " + seatNumber + "] *boos*");
        }
    }
    
    /*
     * Spectator falls asleep (very rare, only when bored)
     */
    public void sleep() {
        if (random.nextDouble() < 0.05 && excitementLevel < 0.2) {
            System.out.println("[Spectator " + seatNumber + "] *falls asleep* Zzz...");
        }
    }
    
    /**
     * Spectator reacts to match action
     * @param actionIntensity how exciting the action was (0.0 to 1.0)
     */
    public void reactToAction(double actionIntensity) {
        updateExcitementLevel((actionIntensity - 0.5) * 0.1);
        
        if (actionIntensity > 0.8) {
            cheer();
        } else if (actionIntensity > 0.5) {
            applaud();
        } else if (actionIntensity < 0.2) {
            sleep();
        } else if (actionIntensity < 0.3 && random.nextDouble() < 0.1) {
            boo();
        }
    }
    
    /*
     * Gets spectator's distinctive description
     */
    public String getDistinctiveDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append("Seat ").append(seatNumber).append(": ");
        desc.append(getFullName()).append(" (").append(gender).append(")");
        
        if (gender == Gender.MALE) {
            desc.append(" - wearing ").append(shirtColor).append(" shirt");
        } else {
            desc.append(" - ").append(wearsGlasses ? "wearing glasses" : "no glasses");
        }
        
        if (wearsHat) {
            desc.append(", has a hat");
        }
        
        return desc.toString();
    }
    
    /*
     * Calculate ticket price based on tournament round
     */
    public static double calculateTicketPrice(String round) {
        return switch (round.toLowerCase()) {
            case "first round" -> 50.0;
            case "second round" -> 75.0;
            case "third round" -> 100.0;
            case "round of 16" -> 150.0;
            case "quarterfinals" -> 250.0;
            case "semifinals" -> 400.0;
            case "finals" -> 600.0;
            default -> 50.0;
        };
    }
    
    @Override
    public String toString() {
        return super.toString() + " [Spectator, Seat: " + seatNumber + 
               ", Round: " + tournamentRound + ", Ticket: $" + 
               String.format("%.2f", ticketPrice) + "]";
    }
}