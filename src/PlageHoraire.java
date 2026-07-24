import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe représentant une plage horaire pour une activité.
 */
public class PlageHoraire {
    private final LocalDate date;
    private final LocalTime heureDebut;
    private final int duree;

    /**
     * Constructeur de la classe PlageHoraire.
     *
     * @param date La date de la plage horaire.
     * @param heureDebut L'heure de début de la plage horaire.
     * @param duree La durée de la plage horaire en minutes.
     */
    public PlageHoraire(LocalDate date, LocalTime heureDebut, int duree) {
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
    }

    /**
     * Constructeur de la classe PlageHoraire à partir de chaînes de caractères.
     *
     * @param date La date de la plage horaire au format "dd-MM-yyyy".
     * @param heureDebut L'heure de début de la plage horaire au format "H:mm".
     * @param duree La durée de la plage horaire en minutes.
     */
    public PlageHoraire(String date, String heureDebut, int duree) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.heureDebut = LocalTime.parse(heureDebut, DateTimeFormatter.ofPattern("H:mm"));
        this.duree = duree;
    }

    /**
     * Vérifie si la plage horaire est en conflit avec une liste d'autres plages horaires.
     *
     * @param horaires La liste des plages horaires à vérifier.
     * @return true si la plage horaire est en conflit avec au moins une autre plage, false sinon.
     */
    public boolean estEnConflitHoraire(ArrayList<PlageHoraire> horaires) {
        for (PlageHoraire horaire : horaires) {
            if (this.intersect(horaire)) return true;
        }
        return false;
    }

    /**
     * Vérifie si la plage horaire intersecte avec une autre plage horaire.
     *
     * @param horaire La plage horaire à vérifier.
     * @return true si les plages horaires s'intersectent, false sinon.
     */
    public boolean intersect(PlageHoraire horaire) {
        if (datesDifferente(this, horaire)) return false;
        return this.heureDebut.isBefore(horaire.getHeureFin())
                && this.getHeureFin().isAfter(horaire.getHeureDebut());
    }

    /**
     * Vérifie si deux plages horaires ont des dates différentes.
     *
     * @param horaire1 La première plage horaire.
     * @param horaire2 La deuxième plage horaire.
     * @return true si les dates sont différentes, false sinon.
     */
    public static boolean datesDifferente(PlageHoraire horaire1, PlageHoraire horaire2) {
        return !horaire1.date.isEqual(horaire2.getDate());
    }

    /**
     * Getteurs pour les attributs de la classe PlageHoraire.
     */
    public LocalDate getDate() { return this.date; }
    public LocalTime getHeureDebut() { return this.heureDebut; }
    public LocalTime getHeureFin() { return this.heureDebut.plusMinutes(duree); }
    public int getDuree() { return duree; }
    public double getNombreHeure() { return (double) duree / 60; }

    /**
     * Retourne une représentation textuelle de la plage horaire.
     *
     * @return Une chaîne de caractères représentant la plage horaire.
     */
    @Override
    public String toString() {
        return date + " " + heureDebut + "-" + this.getHeureFin();
    }
}
