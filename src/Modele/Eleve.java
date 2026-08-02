package Modele;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Représente un élève inscrit au service.
 * Cette classe contient directement les informations personnelles
 * qui étaient auparavant dans Personne.
 */
public class Eleve {
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private final long numSAAQ;
    private final LocalDate dateDebut;
    private LocalDate dateFin;

    /**
     * Constructeur de la classe Eleve
     * @param numSAAQ le numéro SAAQ de l'élève
     * @param prenom le prénom de l'élève
     * @param nom le nom de l'élève
     * @param adresse l'adresse de l'élève
     * @param telephone le numéro de téléphone de l'élève
     * @param dateDebut la date de début de l'inscription de l'élève
     */
    public Eleve(long numSAAQ, String prenom, String nom, String adresse, String telephone, LocalDate dateDebut) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.numSAAQ = numSAAQ;
        this.dateDebut = dateDebut;
        this.dateFin = null;
    }

    /**
     * Constructeur de la classe Eleve
     * @param numSAAQ le numéro SAAQ de l'élève
     * @param prenom le prénom de l'élève
     * @param nom le nom de l'élève
     * @param adresse l'adresse de l'élève
     * @param telephone le numéro de téléphone de l'élève
     * @param dateDebut la date de début de l'inscription de l'élève
     * @param dateFin la date de fin de l'inscription de l'élève
     */
    public Eleve(long numSAAQ, String prenom, String nom, String adresse, String telephone, LocalDate dateDebut, LocalDate dateFin) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.numSAAQ = numSAAQ;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    /**
     * Getters pour les attributs de la classe Eleve
     */
    public String getPrenom() {
        return prenom;
    }
    public String getNom() {
        return nom;
    }
    public String getAdresse() { return this.adresse; }
    public String getTelephone() {
        return telephone;
    }
    public long getNumSAAQ() {
        return this.numSAAQ;
    }
    public LocalDate getDateDebut() {
        return this.dateDebut;
    }
    public LocalDate getDateFin() {
        return this.dateFin;
    }

    /**
     * Définit la date de fin de l'inscription de l'élève
     * @param dateFin la date de fin à définir
     */
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Définit le nom de l'élève
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Définit le prénom de l'élève
     * @param prenom le prénom à définir
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Définit l'adresse de l'élève
     * @param adresse l'adresse à définir
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Définit le numéro de téléphone de l'élève
     * @param telephone le numéro de téléphone à définir
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Vérifie si deux objets Eleve sont égaux
     * @param o l'objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Eleve eleve)) return false;
        return Objects.equals(this.numSAAQ, eleve.numSAAQ);
    }

    /**
     * Retourne le code de hachage de l'élève basé sur son numéro SAAQ
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(numSAAQ);
    }

    /**
     * Retourne les informations détaillées de l'élève
     * @return chaîne descriptive
     */
    @Override
    public String toString() {
        return "Eleve{numSAAQ=" + numSAAQ +
                ", nom=" + nom +
                ", prenom=" + prenom +
                ", telephone=" + telephone +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                "}";
    }
}