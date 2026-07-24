/**
 * Enumération représentant les différents types d'autres dépenses.
 */
public enum TypeAutreDepense {
    P("Publicité"),
    B("Bureau"),
    T("Téléphone"),
    I("Internet"),
    A("Autre");

    private final String libelle;

    /**
     * Constructeur de l'énumération.
     *
     * @param libelle Le libellé associé au type d'autre dépense.
     */
    TypeAutreDepense(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Retourne le libellé associé au type d'autre dépense.
     *
     * @return Le libellé du type d'autre dépense.
     */
    public String getLibelle() { return this.libelle; }
}
