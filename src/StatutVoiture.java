/**
 * Enumération représentant le statut d'une voiture.
 */
public enum StatutVoiture {
    R("Réparation"),
    V("Vendu"),
    D("Disponible");

    private final String libelle;

    /**
     * Constructeur de l'énumération.
     *
     * @param libelle Le libellé associé au statut de la voiture.
     */
    StatutVoiture(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Retourne le libellé associé au statut de la voiture.
     *
     * @return Le libellé du statut de la voiture.
     */
    public String getLibelle() { return this.libelle; }
}
