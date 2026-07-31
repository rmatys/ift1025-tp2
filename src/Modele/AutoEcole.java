package Modele;

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
     * @param eleve l'élève à ajouter
     */
    public void ajouterEleve(Eleve eleve) {
        eleves.add(eleve);
        sauvegarderEleves();
    }

    /**
     * Supprime un élève de la liste des élèves de l'auto-école en fonction de son numéro SAAQ
     * @param numSAAQ numero Saaq de l'eleve
     */
    public void supprimerEleve(long numSAAQ) {
        eleves.removeIf(eleve -> eleve.getNumSAAQ() == numSAAQ);
        sauvegarderEleves();
    }

    /**
     * Recherche un élève dans la liste des élèves de l'auto-école en fonction de son numéro SAAQ
     * @param numSAAQ numero Saaq de l'eleve
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

    /**
     * Valide puis remplace les informations d'un élève existant de l'auto-école,
     * en conservant sa date de début et sa date de fin
     * @param numSAAQ le numéro SAAQ de l'élève à modifier
     * @throws IllegalArgumentException si aucun élève ne correspond au numéro SAAQ donné
     */
    public Eleve modifierEleve(long numSAAQ, String prenom, String nom, String adresse, String telephone) {
        int index = -1;
        for (int i = 0; i < eleves.size(); i++) {
            if (eleves.get(i).getNumSAAQ() == numSAAQ) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException("aucun élève trouvé avec le numéro SAAQ " + numSAAQ);
        }

        Eleve ancien = eleves.get(index);
        Eleve nouveau = new Eleve(numSAAQ, prenom, nom, adresse, telephone, ancien.getDateDebut(), ancien.getDateFin());
        eleves.set(index, nouveau);
        sauvegarderEleves();
        return nouveau;
    }


    // Méthodes de gestion des activités

    /**
     * Ajoute une activité à la liste des activités de l'auto-école
     * @param activite l'activité à ajouter
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
     * Retourne les activités associées à un élève
     * @param eleve l'élève dont on cherche les activités
     * @return la liste des activités de l'élève
     */
    public ArrayList<Activite> getActivitesEleve(Eleve eleve) {
        ArrayList<Activite> resultat = new ArrayList<>();
        for (Activite activite : activites) {
            if (activite.getEleve().equals(eleve)) resultat.add(activite);
        }
        return resultat;
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

    /**
     * Valide puis crée une nouvelle activité pour un élève existant
     * @throws OperationInvalideException si l'élève est introuvable, si l'horaire est en conflit
     *         avec une autre activité, ou si la voiture demandée n'est pas disponible
     */
    public Activite creerActivite(PlageHoraire horaire, long numSAAQ, String plaque,
                                   TypeActivite type, StatutActivite statut) throws OperationInvalideException {
        Eleve eleve = rechercherEleve(numSAAQ);
        if (eleve == null) {
            throw new OperationInvalideException("aucun élève trouvé avec le NumSAAQ " + numSAAQ);
        }

        ArrayList<PlageHoraire> horaires = new ArrayList<>();
        for (Activite activite : activites) horaires.add(activite.getPlageHoraire());
        if (horaire.estEnConflitHoraire(horaires)) {
            throw new OperationInvalideException("conflit d'horaire à " + horaire);
        }

        Voiture voiture = rechercherVoiture(plaque);
        if (voiture != null && !voiture.estDisponible()) {
            throw new OperationInvalideException("la voiture de l'école n'est pas disponible");
        }

        if (statut.equals(StatutActivite.C)) {
            eleve.setDateFin(LocalDate.now());
            sauvegarderEleves();
        }

        Activite activite = new Activite(prochainIdActivite(), horaire, eleve, plaque, type, statut);
        ajouterActivite(activite);
        return activite;
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
     * Change le statut d'un paiement, valide le montant restant si applicable, et sauvegarde
     * @param id l'identifiant du paiement à modifier
     * @param nouvelEtat le nouveau statut du paiement
     * @param montantRestant le nouveau montant restant, utilisé seulement si nouvelEtat est PP
     * @throws OperationInvalideException si le montant restant demandé est supérieur à l'ancien
     */
    public void changerEtatPaiement(String id, StatutPaiement nouvelEtat, double montantRestant) throws OperationInvalideException {
        Paiement paiement = rechercherPaiement(id);
        if (paiement == null) return;

        if (nouvelEtat.equals(StatutPaiement.P)) {
            paiement.setMontantRestant(0);
        } else if (nouvelEtat.equals(StatutPaiement.PP)) {
            if (montantRestant > paiement.getMontantRestant()) {
                throw new OperationInvalideException("le montant restant doit être plus petit que ce qu'il y avait avant");
            }
            paiement.setMontantRestant(montantRestant);
        }

        paiement.setDate(LocalDate.now());
        paiement.setEtat(nouvelEtat);
        sauvegarderPaiements();
    }

    /**
     * Retourne les paiements associés à un élève
     * @param eleve l'élève dont on cherche les paiements
     * @return la liste des paiements de l'élève
     */
    public ArrayList<Paiement> getPaiementsEleve(Eleve eleve) {
        ArrayList<Paiement> resultat = new ArrayList<>();
        for (Paiement paiement : paiements) {
            if (paiement.getEleve().equals(eleve)) resultat.add(paiement);
        }
        return resultat;
    }

    /**
     * Retourne les paiements impayés
     * @return la liste des paiements dont le statut est impayé
     */
    public ArrayList<Paiement> getPaiementsImpayes() {
        ArrayList<Paiement> resultat = new ArrayList<>();
        for (Paiement paiement : paiements) {
            if (paiement.getStatutPaiement().equals(StatutPaiement.I)) resultat.add(paiement);
        }
        return resultat;
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
     * Supprime une voiture de la liste des voitures de l'auto-école en fonction de sa plaque
     * @param plaque la plaque de la voiture à supprimer
     */
    public void supprimerVoiture(String plaque) {
        voitures.removeIf(voiture -> voiture.getPlaque().equals(plaque));
        sauvegarderVoitures();
    }

    /**
     * Change l'état d'une voiture et sauvegarde
     * @param plaque la plaque de la voiture à modifier
     * @param nouvelEtat le nouvel état de la voiture
     */
    public void changerEtatVoiture(String plaque, StatutVoiture nouvelEtat) {
        Voiture voiture = rechercherVoiture(plaque);
        if (voiture == null) return;

        voiture.setEtat(nouvelEtat);
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

    /**
     * Valide puis crée une nouvelle voiture pour l'auto-école
     * @throws OperationInvalideException si le prix ou l'un des kilométrages est négatif
     */
    public Voiture creerVoiture(String marque, String plaque, int annee, double prix, int kmAchat,
                                 StatutVoiture etat, int km) throws OperationInvalideException {
        if (prix < 0 || kmAchat < 0 || km < 0) {
            throw new OperationInvalideException("le prix et le kilométrage ne peuvent pas être négatifs");
        }

        ArrayList<DepenseVoiture> depenses = trouverDepensesVoitureSelonPlaque(plaque);
        Voiture voiture = new Voiture(plaque, marque, annee, prix, kmAchat, etat, km, depenses);
        ajouterVoiture(voiture);
        return voiture;
    }

    /**
     * Valide puis remplace les informations d'une voiture existante de l'auto-école
     * @param plaque la plaque de la voiture à modifier
     * @throws OperationInvalideException si le prix ou l'un des kilométrages est négatif, ou si aucune voiture
     *         ne correspond à la plaque donnée
     */
    public Voiture modifierVoiture(String plaque, String marque, int annee, double prix, int kmAchat,
                                    StatutVoiture etat, int km) throws OperationInvalideException {
        if (prix < 0 || kmAchat < 0 || km < 0) {
            throw new OperationInvalideException("le prix et le kilométrage ne peuvent pas être négatifs");
        }

        int index = voitures.indexOf(rechercherVoiture(plaque));
        if (index == -1) {
            throw new OperationInvalideException("aucune voiture trouvée avec la plaque " + plaque);
        }

        Voiture voiture = new Voiture(plaque, marque, annee, prix, kmAchat, etat, km, voitures.get(index).getDepensesVoiture());
        voitures.set(index, voiture);
        sauvegarderVoitures();
        return voiture;
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
     * Valide puis crée une nouvelle dépense pour une voiture de l'auto-école
     * @throws OperationInvalideException si le montant est négatif
     */
    public DepenseVoiture creerDepenseVoiture(String plaque, LocalDate date, TypeDepenseVoiture categorie,
                                               String description, double montant) throws OperationInvalideException {
        if (montant < 0) {
            throw new OperationInvalideException("le montant ne peut pas être négatif");
        }

        DepenseVoiture depense = new DepenseVoiture(prochainIdDepenseVoiture(), plaque, date, categorie, description, montant);
        ajouterDepenseVoiture(depense);

        Voiture voiture = rechercherVoiture(plaque);
        if (voiture != null) {
            voiture.updateDepenses(depensesVoiture);
        }

        return depense;
    }

    /**
     * Valide puis crée une nouvelle autre dépense de l'auto-école
     * @throws OperationInvalideException si le montant est négatif
     */
    public AutreDepense creerAutreDepense(LocalDate date, TypeAutreDepense categorie,
                                           String description, double montant) throws OperationInvalideException {
        if (montant < 0) {
            throw new OperationInvalideException("le montant ne peut pas être négatif");
        }

        AutreDepense depense = new AutreDepense(prochainIdAutreDepense(), date, categorie, description, montant);
        ajouterAutreDepense(depense);
        return depense;
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