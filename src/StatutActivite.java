/**
 * Enumération représentant le statut d'une activité.
 */
public enum StatutActivite {
    C("complétée"),
    NC("non complétée");

    private final String libelle;

    /**
     * Constructeur de l'énumération.
     *
     * @param libelle Le libellé associé au statut de l'activité.
     */
    StatutActivite(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Retourne le libellé associé au statut de l'activité.
     *
     * @return Le libellé du statut de l'activité.
     */
    public String getLibelle() { return libelle; }
}
