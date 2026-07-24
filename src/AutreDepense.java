import java.time.LocalDate;

/**
 * Classe représentant une autre dépense de l'auto-école
 */
public class AutreDepense {
    private final int idDepense;
    private final LocalDate date;
    private final TypeAutreDepense typeAutreDepense;
    private final String description;
    private final double montant;

    /**
     * Constructeur de la classe AutreDepense
     * @param idDepense l'identifiant unique de la dépense
     * @param date la date de la dépense
     * @param typeAutreDepense le type de la dépense
     * @param description la description de la dépense
     * @param montant le montant de la dépense
     */
    public AutreDepense(int idDepense, LocalDate date, TypeAutreDepense typeAutreDepense,
                        String description, double montant) {
        this.idDepense = idDepense;
        this.date = date;
        this.typeAutreDepense = typeAutreDepense;
        this.description = description.replace(',', '.');
        this.montant = montant;
    }

    /**
     * Getters pour les attributs de la classe AutreDepense
     */
    public int getId() { return idDepense; }
    public LocalDate getDate() { return date; }
    public TypeAutreDepense getCategorie() { return typeAutreDepense; }
    public String getDescription() { return description; }
    public double getMontant() { return montant; }

    /**
     * Méthode toString pour afficher les informations de la dépense
     * @return une chaîne de caractères représentant la dépense
     */
    @Override
    public String toString() {
        return "AutreDepense{id=" + idDepense +
                ", date=" + date +
                ", type=" + typeAutreDepense.getLibelle() +
                ", description=" + description +
                ", montant=" + montant +
                "}";
    }
}
