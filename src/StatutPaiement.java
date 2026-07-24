/**
 * Enumération représentant le statut d'un paiement.
 */
public enum StatutPaiement {
    P("Payé"),
    I("Impayé"),
    PP("Partiellement payé");

    private final String libelle;

    /**
     * Constructeur de l'énumération.
     *
     * @param libelle Le libellé associé au statut du paiement.
     */
    StatutPaiement(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Retourne le libellé associé au statut du paiement.
     *
     * @return Le libellé du statut du paiement.
     */
    public String getLibelle() { return this.libelle; }
}
