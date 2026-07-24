import java.util.ArrayList;

/**
 * Classe représentant une voiture.
 */
public class Voiture {
    private final String plaqueImmatriculation;
    private final String marque;
    private final int anneeFabrication;
    private final double prixAchat;
    private final int kilometrageAchat;
    private StatutVoiture statutVoiture;
    private final int kilometrageActuel;
    private final ArrayList<DepenseVoiture> depensesVoiture;

    /**
     * Constructeur de la classe Voiture.
     *
     * @param plaqueImmatriculation La plaque d'immatriculation de la voiture.
     * @param marque La marque de la voiture.
     * @param anneeFabrication L'année de fabrication de la voiture.
     * @param prixAchat Le prix d'achat de la voiture.
     * @param kilometrageAchat Le kilométrage lors de l'achat de la voiture.
     * @param statutVoiture Le statut actuel de la voiture.
     * @param kilometrageActuel Le kilométrage actuel de la voiture.
     */
    public Voiture(String plaqueImmatriculation, String marque, int anneeFabrication,
                   double prixAchat, int kilometrageAchat, StatutVoiture statutVoiture,
                   int kilometrageActuel) {
        this.plaqueImmatriculation = plaqueImmatriculation;
        this.marque = marque;
        this.anneeFabrication = anneeFabrication;
        this.prixAchat = prixAchat;
        this.kilometrageAchat = kilometrageAchat;
        this.statutVoiture = statutVoiture;
        this.kilometrageActuel = kilometrageActuel;
        this.depensesVoiture = new ArrayList<>();
    }

    /**
     * Constructeur de la classe Voiture avec une liste de dépenses.
     *
     * @param plaqueImmatriculation La plaque d'immatriculation de la voiture.
     * @param marque La marque de la voiture.
     * @param anneeFabrication L'année de fabrication de la voiture.
     * @param prixAchat Le prix d'achat de la voiture.
     * @param kilometrageAchat Le kilométrage lors de l'achat de la voiture.
     * @param statutVoiture Le statut actuel de la voiture.
     * @param kilometrageActuel Le kilométrage actuel de la voiture.
     * @param depensesVoiture La liste des dépenses liées à la voiture.
     */
    public Voiture(String plaqueImmatriculation, String marque, int anneeFabrication,
                   double prixAchat, int kilometrageAchat, StatutVoiture statutVoiture,
                   int kilometrageActuel, ArrayList<DepenseVoiture> depensesVoiture) {
        this.plaqueImmatriculation = plaqueImmatriculation;
        this.marque = marque;
        this.anneeFabrication = anneeFabrication;
        this.prixAchat = prixAchat;
        this.kilometrageAchat = kilometrageAchat;
        this.statutVoiture = statutVoiture;
        this.kilometrageActuel = kilometrageActuel;
        this.depensesVoiture = depensesVoiture;
    }

    /**
     * Vérifie si la voiture est disponible.
     *
     * @return true si la voiture est disponible, false sinon.
     */
    public boolean estDisponible() { return this.statutVoiture == StatutVoiture.D; }

    /**
     * Met à jour la liste des dépenses liées à la voiture.
     *
     * @param depenses La liste des dépenses à mettre à jour.
     */
    public void updateDepenses(ArrayList<DepenseVoiture> depenses) {
        this.depensesVoiture.clear();

        for (DepenseVoiture depense : depenses) {
            if (depense.getPlaque().equals(this.plaqueImmatriculation)) this.depensesVoiture.add(depense);
        }
    }

    /**
     * Getters pour les attributs de la classe Voiture.
     */
    public String getPlaque() { return plaqueImmatriculation; }
    public String getMarque() { return marque; }
    public int getAnnee() { return anneeFabrication; }
    public double getPrix() { return prixAchat; }
    public int getKmAchat() { return kilometrageAchat; }
    public int getKm() { return kilometrageActuel; }
    public ArrayList<DepenseVoiture> getDepensesVoiture() { return depensesVoiture; }
    public StatutVoiture getEtat() { return statutVoiture; }

    /**
     * Setters pour les attributs de la classe Voiture.
     */
    public void setEtat(StatutVoiture etat) { this.statutVoiture = etat; }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la voiture.
     *
     * @return Une chaîne de caractères représentant la voiture.
     */
    @Override
    public String toString() {
        return "Voiture{plaque=" + plaqueImmatriculation +
                ", marque=" + marque +
                ", anneeFabrication=" + anneeFabrication +
                ", prixAchat=" + prixAchat +
                ", kilometrageAchat=" + kilometrageAchat +
                ", statutVoiture=" + statutVoiture.getLibelle() +
                ", kilometrageActuel=" + kilometrageActuel +
                "}";
    }
}
