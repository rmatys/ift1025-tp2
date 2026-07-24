/**
 * Enumération représentant les différentes méthodes de paiement.
 */
public enum MethodePaiement {
    E("Espèces"),
    C("Carte"),
    V("Virement");

    private final String libelle;

    /**
     * Constructeur de l'énumération.
     *
     * @param libelle Le libellé associé à la méthode de paiement.
     */
    MethodePaiement(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Retourne le libellé associé à la méthode de paiement.
     *
     * @return Le libellé de la méthode de paiement.
     */
    public String getLibelle() { return this.libelle; }
}
