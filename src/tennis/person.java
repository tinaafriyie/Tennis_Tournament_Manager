package classes;

import java.time.LocalDate;
// import java.time.Period; // inutile si on ne calcule pas l'âge

public class Person {

    public enum Genre { HOMME, FEMME, AUTRE }

    private String nomNaissance;
    private Genre genre;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private LocalDate dateDeces;

    private String prenom;
    private String surnom;
    private String nationalite;
    private int taille;      // en cm
    private double poids;    // en kg

    private boolean estMarie;
    private String nomCourant;

    // Constructeur simple
    public Person(String nomNaissance, Genre genre, LocalDate dateNaissance, String lieuNaissance) {
        this.nomNaissance = nomNaissance;
        this.genre = genre;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.dateDeces = null;
        this.estMarie = false;
        this.nomCourant = null;
    }

    // Constructeur étendu
    public Person(String nomNaissance, Genre genre, LocalDate dateNaissance, String lieuNaissance,
                  String prenom, String surnom, String nationalite, int taille, double poids) {
        this(nomNaissance, genre, dateNaissance, lieuNaissance);
        this.prenom = prenom;
        this.surnom = surnom;
        this.nationalite = nationalite;
        this.taille = taille;
        this.poids = poids;
    }

    // Getters
    public String getNomNaissance() { return nomNaissance; }
    public Genre getGenre() { return genre; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public String getLieuNaissance() { return lieuNaissance; }
