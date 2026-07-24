import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe représentant une auto-école, qui gère les élèves, les activités, les paiements, les voitures et les dépenses
 */
public class AutoEcole {
    private ArrayList<Eleve> eleves;
    private ArrayList<Activite> activites;
    private ArrayList<Paiement> paiements;
    private ArrayList<Voiture> voitures;
    private ArrayList<DepenseVoiture> depensesVoiture;
    private ArrayList<AutreDepense> autresDepenses;

    /**
     * Constructeur de la classe AutoEcole
     */
    public AutoEcole() {
        this.eleves = new ArrayList<>();
        this.activites = new ArrayList<>();
        this.paiements = new ArrayList<>();
        this.voitures = new ArrayList<>();
        this.depensesVoiture = new ArrayList<>();
        this.autresDepenses = new ArrayList<>();
    }


    // Méthodes de gestion des élèves

    /**
     * Ajoute un élève à la liste des élèves de l'auto-école
     * @param e l'élève à ajouter
     */
    public void ajouterEleve(Eleve eleve) {
        eleves.add(eleve);
        sauvegarderEleves();
    }

    /**
     * Supprime un élève de la liste des élèves de l'auto-école en fonction de son numéro SAAQ
     * @param numSAAQ
     */
    public void supprimerEleve(long numSAAQ) {
        eleves.removeIf(eleve -> eleve.getNumSAAQ() == numSAAQ);
        sauvegarderEleves();
    }

    /**
     * Recherche un élève dans la liste des élèves de l'auto-école en fonction de son numéro SAAQ
     * @param numSAAQ
     * @return l'élève correspondant au numéro SAAQ, ou null si aucun élève n'est trouvé
     */
    public Eleve rechercherEleve(long numSAAQ) {
        for (Eleve eleve : eleves) {
            if (eleve.getNumSAAQ() == numSAAQ) {
                return eleve;
            }
        }
        return null;
    }


    // Méthodes de gestion des activités

    /**
     * Ajoute une activité à la liste des activités de l'auto-école
     * @param a l'activité à ajouter
     */
    public void ajouterActivite(Activite activite) {
        activites.add(activite);
        sauvegarderActivites();
    }

    /**
     * Annule une activité de la liste des activités de l'auto-école en fonction de son identifiant
     * @param id l'identifiant de l'activité à annuler
     */
    public void annulerActivite(int id) {
        activites.removeIf(activite -> activite.getId() == id);
        sauvegarderActivites();
    }

    /**
     * Recherche une activité dans la liste des activités de l'auto-école en fonction de son identifiant
     * @param id l'identifiant de l'activité à rechercher
     * @return l'activité correspondant à l'identifiant, ou null si aucune activité n'est trouvée
     */
    public Activite rechercherActivite(int id) {
        for (Activite activite : activites) {
            if (activite.getId() == id) {
                return activite;
            }
        }
        return null;
    }

    /**
     * Complète une activité de la liste des activités de l'auto-école en fonction de son identifiant
     * @param id l'identifiant de l'activité à compléter
     */
    public void completerActivite(int id) {
        Activite activite = rechercherActivite(id);
        if (activite != null) {
            activite.setStatut(StatutActivite.C);

            TypeActivite type = activite.getType();
            if (type.equals(TypeActivite.EP) || type.equals(TypeActivite.EPL)) {
                activite.getEleve().setDateFin(LocalDate.now());
            }
        }
        sauvegarderActivites();
    }

    /**
     * Retourne le prochain identifiant disponible pour une nouvelle activité
     * @return le prochain identifiant disponible pour une nouvelle activité
     */
    public int prochainIdActivite() {
        int max = 0;
        for (Activite activite : activites) {
            max = Math.max(activite.getId(), max);
        }
        return max + 1;
    }


    // Méthodes de gestion des paiements

    /**
     * Ajoute un paiement à la liste des paiements de l'auto-école
     * @param paiement le paiement à ajouter
     */
    public void ajouterPaiement(Paiement paiement) {
        paiements.add(paiement);
        sauvegarderPaiements();
    }

    /**
     * Recherche un paiement dans la liste des paiements de l'auto-école en fonction de son identifiant
     * @param id l'identifiant du paiement à rechercher
     * @return le paiement correspondant à l'identifiant, ou null si aucun paiement n'est trouvé
     */
    public Paiement rechercherPaiement(String id) {
        for (Paiement paiement : paiements) {
            if (paiement.getId().equals(id)) {
                return paiement;
            }
        }
        return null;
    }

    /**
     * Retourne le prochain numéro de paiement disponible pour un nouveau paiement
     * @return le prochain numéro de paiement disponible pour un nouveau paiement
     */
    public int prochainNumeroPaiement() {
        int max = 0;
        int id;
        for (Paiement paiement : paiements) {
            id = Integer.parseInt(paiement.getId().substring(7));
            max = Math.max(id, max);
        }
        return max + 1;
    }


    // Méthodes de gestion des voitures

    /**
     * Ajoute une voiture à la liste des voitures de l'auto-école
     * @param voiture la voiture à ajouter
     */
    public void ajouterVoiture(Voiture voiture) {
        voitures.add(voiture);
        sauvegarderVoitures();
    }

    /**
     * Recherche une voiture dans la liste des voitures de l'auto-école en fonction de sa plaque
     * @param plaque la plaque de la voiture à rechercher
     * @return la voiture correspondant à la plaque, ou null si aucune voiture n'est trouvée
     */
    public Voiture rechercherVoiture(String plaque) {
        for (Voiture voiture : voitures) {
            if (voiture.getPlaque().equals(plaque)) {
                return voiture;
            }
        }
        return null;
    }


    // Méthodes de gestion des dépenses

    /**
     * Ajoute une dépense de voiture à la liste des dépenses de l'auto-école
     * @param depense la dépense de voiture à ajouter
     */
    public void ajouterDepenseVoiture(DepenseVoiture depense) {
        depensesVoiture.add(depense);
        sauvegarderDepensesVoiture();
    }

    /**
     * Ajoute une autre dépense à la liste des autres dépenses de l'auto-école
     * @param depense l'autre dépense à ajouter
     */
    public void ajouterAutreDepense(AutreDepense depense) {
        autresDepenses.add(depense);
        sauvegarderAutresDepenses();
    }

    /**
     * Recherche les dépenses de voiture dans la liste des dépenses de l'auto-école en fonction de la plaque du véhicule
     * @param plaque la plaque du véhicule à rechercher
     * @return la liste des dépenses de voiture correspondant à la plaque, ou une liste vide si aucune dépense n'est trouvée
     */
    public ArrayList<DepenseVoiture> trouverDepensesVoitureSelonPlaque(String plaque) {
        ArrayList<DepenseVoiture> depensesVoiturePlaque = new ArrayList<>();

        for (DepenseVoiture depense : this.depensesVoiture) {
            if (depense.getPlaque().equals(plaque)) depensesVoiturePlaque.add(depense);
        }

        return depensesVoiturePlaque;
    }

    /**
     * Retourne le prochain identifiant disponible pour une nouvelle dépense de voiture
     * @return le prochain identifiant disponible pour une nouvelle dépense de voiture
     */
    public int prochainIdDepenseVoiture() {
        int max = 0;
        for (DepenseVoiture depense : depensesVoiture) {
            max = Math.max(depense.getId(), max);
        }
        return max + 1;
    }

    /**
     * Retourne le prochain identifiant disponible pour une nouvelle autre dépense
     * @return le prochain identifiant disponible pour une nouvelle autre dépense
     */
    public int prochainIdAutreDepense() {
        int max = 0;
        for (AutreDepense depense : autresDepenses) {
            max = Math.max(depense.getId(), max);
        }
        return max + 1;
    }


    // Lecture des fichiers CSV

    /**
     * Charge les données des élèves depuis le fichier CSV
     */
    public void chargerEleves() { eleves = CSV.lireEleves(); }
    /**
     * Charge les données des voitures depuis le fichier CSV
     */
    public void chargerVoitures() { voitures = CSV.lireVoitures(depensesVoiture); }
    /**
     * Charge les données des activités depuis le fichier CSV
     */
    public void chargerPaiements() { paiements = CSV.lirePaiements(activites, eleves); }
    /**
     * Charge les données des activités depuis le fichier CSV
     */
    public void chargerActivites() { activites = CSV.lireActivites(eleves); }
    /**
     * Charge les données des dépenses depuis le fichier CSV
     */
    public void chargerDepenses() {
        depensesVoiture = CSV.lireDepensesVoiture();
        autresDepenses = CSV.lireAutresDepenses();
    }

    // Sauvegarde

    /**
     * Sauvegarde les données des élèves dans le fichier CSV
     */
    public void sauvegarderEleves() { CSV.ecrireEleves(eleves); }
    /**
     * Sauvegarde les données des activités dans le fichier CSV
     */
    public void sauvegarderActivites() { CSV.ecrireActivites(activites); }
    /**
     * Sauvegarde les données des paiements dans le fichier CSV
     */
    public void sauvegarderPaiements() { CSV.ecrirePaiements(paiements);}
    /**
     * Sauvegarde les données des dépenses de voiture dans le fichier CSV
     */
    public void sauvegarderDepensesVoiture() { CSV.ecrireDepensesVoiture(depensesVoiture); }
    /**
     * Sauvegarde les données des autres dépenses dans le fichier CSV
     */
    public void sauvegarderAutresDepenses() { CSV.ecrireAutresDepenses(autresDepenses); }
    /**
     * Sauvegarde les données des voitures dans le fichier CSV
     */
    public void sauvegarderVoitures() {
        CSV.ecrireVoitures(voitures);
    }


    // Méthodes access
    public ArrayList<Eleve> getEleves() { return eleves; }
    public ArrayList<Activite> getActivites() { return activites; }
    public ArrayList<Paiement> getPaiements() { return paiements; }
    public ArrayList<Voiture> getVoitures() { return voitures; }
    public ArrayList<DepenseVoiture> getDepensesVoiture() { return depensesVoiture; }
    public ArrayList<AutreDepense> getAutresDepenses() { return autresDepenses; }
}