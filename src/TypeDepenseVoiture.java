/**
 * Enumération représentant les différents types de dépenses liées à une voiture.
 */
public enum TypeDepenseVoiture {
    R("Réparation"),
    E("Entretien"),
    C("Carburant");

    private final String libelle;

    /**
     * Constructeur de l'énumération.
     *
     * @param libelle Le libellé associé au type de dépense liée à une voiture.
     */
    TypeDepenseVoiture(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Retourne le libellé associé au type de dépense liée à une voiture.
     *
     * @return Le libellé du type de dépense liée à une voiture.
     */
    public String getLibelle() { return this.libelle; }
}
