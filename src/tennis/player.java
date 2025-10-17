import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public final class Personne {

    public enum Genre { HOMME, FEMME, AUTRE }

    // Immutables après construction
    private final String nomNaissance;      // ex: "Dupont"
    private final Genre genre;              // ex: Genre.FEMME
    private final LocalDate dateNaissance;  // ex: LocalDate.of(1995, 3, 21)
    private final String lieuNaissance;     // ex: "Lille, France"
    private final LocalDate dateDeces;      // ex: null si vivantfefefe
    }