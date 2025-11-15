/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tennis;
import java.time.LocalDate;
import java.time.Period;

/**
 * Base class representing a person in the tennis tournament system.
 * Contains common attributes for all people (players, referees, spectators).
 */
public class Person {
    // Immutable attributes 
    private final String birthName;
    private final String firstName;
    private final LocalDate birthDate;
    private final String placeOfBirth;
    private final String nationality;
    
    // Mutable attributes
    private String commonName;  // For married women taking husband's name
    private String nickname;
    private LocalDate dateOfDeath;
    private double height;  // in cm
    private double weight;  // in kg
    
    /**
     * Constructor for a living person
     * @param birthName the person's birth name (immutable)
     * @param firstName the person's first name (immutable)
     * @param birthDate the person's date of birth (immutable)
     * @param placeOfBirth the person's place of birth (immutable)
     * @param nationality the person's nationality (immutable)
     * @param height the person's height in cm
     * @param weight the person's weight in kg
     */
    public Person(String birthName, String firstName, LocalDate birthDate, 
                  String placeOfBirth, String nationality, double height, double weight) {
        if (birthName == null || birthName.trim().isEmpty()) {
            throw new IllegalArgumentException("Birth name cannot be null or empty");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        
        this.birthName = birthName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.placeOfBirth = placeOfBirth;
        this.nationality = nationality;
        this.height = height;
        this.weight = weight;
        this.commonName = null;  // Not married by default
        this.dateOfDeath = null;  // Alive by default
    }
    
    /**
     * Constructor for a deceased person
     * @param birthName the person's birth name (immutable)
     * @param firstName the person's first name (immutable)
     * @param birthDate the person's date of birth (immutable)
     * @param placeOfBirth the person's place of birth (immutable)
     * @param nationality the person's nationality (immutable)
     * @param height the person's height in cm
     * @param weight the person's weight in kg
     * @param dateOfDeath the person's date of death
     */
    public Person(String birthName, String firstName, LocalDate birthDate, 
                  String placeOfBirth, String nationality, double height, 
                  double weight, LocalDate dateOfDeath) {
        this(birthName, firstName, birthDate, placeOfBirth, nationality, height, weight);
        
        if (dateOfDeath != null && dateOfDeath.isBefore(birthDate)) {
            throw new IllegalArgumentException("Date of death cannot be before birth date");
        }
        this.dateOfDeath = dateOfDeath;
    }
    
    // Getters for immutable attributes (no setters)
    public String getBirthName() {
        return birthName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }
    
    public String getNationality() {
        return nationality;
    }
    
    // Getters and setters for mutable attributes
    public String getCommonName() {
        return commonName;
    }
    
    /**
     * Sets the common name (for married women only)
     * @param commonName husband's name
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }
    
    public void setDateOfDeath(LocalDate dateOfDeath) {
        if (dateOfDeath != null && dateOfDeath.isBefore(this.birthDate)) {
            throw new IllegalArgumentException("Date of death cannot be before birth date");
        }
        if (dateOfDeath != null && dateOfDeath.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of death cannot be in the future");
        }
        this.dateOfDeath = dateOfDeath;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }
        this.height = height;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weight = weight;
    }
    
    /**
     * Calculates the age of the person
     * @return age in years (current age if alive, age at death if deceased)
     */
    public int getAge() {
        LocalDate endDate = (dateOfDeath != null) ? dateOfDeath : LocalDate.now();
        return Period.between(birthDate, endDate).getYears();
    }
    
    /**
     * Checks if the person is alive
     * @return true if alive, false if deceased
     */
    public boolean isAlive() {
        return dateOfDeath == null;
    }
    
    /**
     * Gets the display name (common name if set, otherwise birth name)
     * @return the name to display
     */
    public String getDisplayName() {
        return (commonName != null && !commonName.trim().isEmpty()) 
               ? commonName : birthName;
    }
    
    /**
     * Gets the full display name with first name
     * @return full name
     */
    public String getFullName() {
        return firstName + " " + getDisplayName();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFullName());
        if (nickname != null && !nickname.trim().isEmpty()) {
            sb.append(" \"").append(nickname).append("\"");
        }
        sb.append(" (").append(nationality).append(", Age: ").append(getAge()).append(")");
        return sb.toString();
    }
}