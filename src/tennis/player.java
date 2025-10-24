public class player extends Personne {

    public enum Main { DROITIER, GAUCHER }

    private Main main;           // Main de jeu
    private String sponsor;      // Nom du sponsor
    private int classement;      // Classement du joueur
    private String entraineur;   // Nom de l'entraîneur

    // Pour différencier les tenues
    private String couleurTenue; // couleur du short ou de la jupe
    private String typeTenue;    // "short" ou "jupe"

    // Variable statique pour compter les joueurs créés
    private static int compteurJoueurs = 0;

    // Constructeur simple
    public player(String nomNaissance, Genre genre, java.time.LocalDate dateNaissance, String lieuNaissance,
                  Main main, String sponsor, String entraineur, String couleurTenue) {

        // On appelle le constructeur de Personne
        super(nomNaissance, genre, dateNaissance, lieuNaissance);

        this.main = main;
        this.sponsor = sponsor;
        this.entraineur = entraineur;
        this.couleurTenue = couleurTenue;

        // Détermination du type de tenue selon le genre
        if (genre == Genre.FEMME) {
            this.typeTenue = "jupe";
        } else {
            this.typeTenue = "short";
        }

        // Classement basé sur l'ordre de création
        compteurJoueurs++;
        this.classement = compteurJoueurs;
    }

    // Getters et setters
    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public String getSponsor() { return sponsor; }
    public void setSponsor(String sponsor) { this.sponsor = sponsor; }

    public int getClassement() { return classement; }
    public void setClassement(int classement) { this.classement = classement; }

    public String getEntraineur() { return entraineur; }
    public void setEntraineur(String entraineur) { this.entraineur = entraineur; }

    public String getCouleurTenue() { return couleurTenue; }
    public void setCouleurTenue(String couleurTenue) {
        // Quand la couleur change, on affiche un message
        if (!couleurTenue.equals(this.couleurTenue)) {
            System.out.println(getPrenom() + " " + getNomNaissance() +
                " dit : 'J’ai changé la couleur de ma " + typeTenue + " en " + couleurTenue + " !'");
        }
        this.couleurTenue = couleurTenue;
    }

    public String getTypeTenue() { return typeTenue; }

    // Méthode pour afficher les infos du joueur
    public String toString() {
        return "Joueur : " + (getPrenom() != null ? getPrenom() + " " : "") + getNomNaissance() +
               " (" + getGenre() + "), " +
               "main = " + main +
               ", sponsor = " + sponsor +
               ", classement = " + classement +
               ", entraîneur = " + entraineur +
               ", " + typeTenue + " " + couleurTenue;
    }
}
