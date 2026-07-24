import java.time.LocalDate;

/**
 * Classe représentant un paiement effectué par un élève pour une activité.
 */
public class Paiement {
    private final String idPaiement;
    private final double montant;
    private LocalDate datePaiement;
    private StatutPaiement statutPaiement;
    private double montantRestant;
    private final Activite activite;
    private final MethodePaiement methodePaiement;
    private final Eleve eleve;
    private final TypeActivite typeActivite;

    /**
     * Constructeur de la classe Paiement.
     *
     * @param numeroSequentiel Le numéro séquentiel du paiement.
     * @param datePaiement La date du paiement.
     * @param statutPaiement Le statut du paiement.
     * @param activite L'activité associée au paiement.
     * @param methodePaiement La méthode de paiement utilisée.
     * @param eleve L'élève ayant effectué le paiement.
     */
    public Paiement(int numeroSequentiel, LocalDate datePaiement, StatutPaiement statutPaiement, Activite activite,
                    MethodePaiement methodePaiement, Eleve eleve) {
        double montant = activite.getMontant();
        double montantRestant = -1;
        if (statutPaiement.equals(StatutPaiement.P)) {
            montantRestant = 0.0;
        } else if (statutPaiement.equals(StatutPaiement.PP)) {
            montantRestant = montant;
        } else if (statutPaiement.equals(StatutPaiement.I)) {
            montantRestant = montant;
        } else {
            System.err.println("Erreur: Stuatut du paiement manquant, valeur donnée : " + statutPaiement);
        }

        this.idPaiement = "F-" + LocalDate.now().getYear() + "-" + String.format("%05d", numeroSequentiel);
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statutPaiement = statutPaiement;
        this.montantRestant = montantRestant;
        this.activite = activite;
        this.methodePaiement = methodePaiement;
        this.eleve = eleve;
        this.typeActivite = activite.getType();
    }

    /**
     * Constructeur de la classe Paiement avec un identifiant spécifique.
     *
     * @param id L'identifiant du paiement.
     * @param montant Le montant du paiement.
     * @param datePaiement La date du paiement.
     * @param statutPaiement Le statut du paiement.
     * @param montantRestant Le montant restant à payer.
     * @param activite L'activité associée au paiement.
     * @param methodePaiement La méthode de paiement utilisée.
     * @param eleve L'élève ayant effectué le paiement.
     */
    public Paiement(String id, double montant, LocalDate datePaiement,
                    StatutPaiement statutPaiement, double montantRestant,
                    Activite activite, MethodePaiement methodePaiement, Eleve eleve) {
        this.idPaiement = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statutPaiement = statutPaiement;
        this.montantRestant = montantRestant;
        this.activite = activite;
        this.methodePaiement = methodePaiement;
        this.eleve = eleve;
        this.typeActivite = activite.getType();
    }

    /** 
     * Getteurs pour les attributs de la classe Paiement.
     */
    public String getId() { return this.idPaiement; }
    public double getMontant() { return this.montant; }
    public LocalDate getDate() { return this.datePaiement; }
    public StatutPaiement getStatutPaiement() {return this.statutPaiement; }
    public double getMontantRestant() { return this.montantRestant; }
    public Activite getActivite() { return this.activite; }
    public MethodePaiement getMethodePaiement() { return this.methodePaiement; }
    public Eleve getEleve() { return this.eleve; }
    public TypeActivite getTypeActivite() { return this.typeActivite; }

    /**
     * Setteurs pour les attributs modifiables de la classe Paiement.
     */
    public void setEtat(StatutPaiement statut) { this.statutPaiement = statut; }
    public void setMontantRestant(double montantRestant) { this.montantRestant = montantRestant; }
    public void setDate(LocalDate datePaiement) { this.datePaiement = datePaiement; }

    /**
     * Retourne une représentation textuelle du paiement.
     *
     * @return Une chaîne de caractères représentant le paiement.
     */
    @Override
    public String toString() {
        return "Paiement{id=" + idPaiement +
                ", montant=" + montant +
                ", montantRestant=" + montantRestant +
                ", datePaiement=" + datePaiement +
                ", statut=" + statutPaiement +
                ", typeActivite=" + typeActivite +
                ", numSaaqEleve=" + eleve.getNumSAAQ() +
                "}";
    }
}
