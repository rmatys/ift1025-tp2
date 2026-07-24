/**
 * Enumération représentant les différents types d'activités.
 */
public enum TypeActivite {
    LPA("Leçon Pratique sur Autoroute"),
    LPZ("Leçon Pratique dans Zone résidentielle"),
    LPS("Leçon Pratique de Stationnement"),
    LT("Leçon Théorique"),
    ET("Examen Théorique"),
    EP("Examen Pratique"),
    EPL("Examen Pratique avec location");

    private final String libelle;

    /**
     * Constructeur de l'énumération.
     *
     * @param libelle Le libellé associé au type d'activité.
     */
    TypeActivite(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Retourne le libellé associé au type d'activité.
     *
     * @return Le libellé du type d'activité.
     */
    public String getLibelle() { return this.libelle; }
}
