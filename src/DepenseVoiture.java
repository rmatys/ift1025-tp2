import java.time.LocalDate;

/**
 * Classe représentant une dépense liée à une voiture de l'auto-école
 */
public class DepenseVoiture {
    private final int idDepense;
    private final String plaque;
    private final LocalDate date;
    private final TypeDepenseVoiture typeDepenseVoiture;
    private final String description;
    private final double montant;

    /**
     * Constructeur de la classe DepenseVoiture
     * @param idDepense l'identifiant unique de la dépense
     * @param plaque la plaque d'immatriculation de la voiture
     * @param date la date de la dépense
     * @param typeDepenseVoiture le type de la dépense
     * @param description la description de la dépense
     * @param montant le montant de la dépense
     */
    public DepenseVoiture(int idDepense, String plaque, LocalDate date,
                          TypeDepenseVoiture typeDepenseVoiture, String description,
                          double montant) {
        this.idDepense = idDepense;
        this.plaque = plaque;
        this.date = date;
        this.typeDepenseVoiture = typeDepenseVoiture;
        this.description = description.replace(',', '.');
        this.montant = montant;
    }

    /**
     * Getters pour les attributs de la classe DepenseVoiture
     */
    public int getId() { return idDepense; }
    public String getPlaque() { return plaque; }
    public LocalDate getDate() { return date; }
    public TypeDepenseVoiture getCategorie() { return typeDepenseVoiture; }
    public String getDescription() { return description; }
    public double getMontant() { return montant; }

    /**
     * Méthode toString pour afficher les informations de la dépense
     * @return une chaîne de caractères représentant la dépense
     */
    @Override
    public String toString() {
        return "AutreDepense{id=" + idDepense +
                ", plaque=" + plaque +
                ", date=" + date +
                ", type=" + typeDepenseVoiture.getLibelle() +
                ", description=" + description +
                ", montant=" + montant +
                "}";
    }
}
