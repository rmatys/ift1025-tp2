package Vue;

import Modele.*;

import java.util.ArrayList;

/**
 * Affichage pur des entités du Modèle (listes déjà résolues par le Contrôleur).
 */
public class Afficher {
    public static void afficherEleves(ArrayList<Eleve> eleves) {
        System.out.println("Liste de tous les élèves de l'école: ");
        for (Eleve eleve : eleves) {
            System.out.println(" - " + eleve);
        }
        if (eleves.isEmpty()) System.out.println("Aucun élève dans le système.");
    }

    public static void afficherActivites(ArrayList<Activite> activites) {
        System.out.println("Liste de toutes les activités: ");
        for (Activite activite : activites) {
            System.out.println(" - " + activite);
        }
        if (activites.isEmpty()) System.out.println("Aucune activité dans le système.");
    }

    public static void afficherActivitesEleve(Eleve eleve, ArrayList<Activite> activites) {
        System.out.println("Liste des activités de l'élève: ");
        System.out.println(" * " + eleve);
        for (Activite activite : activites) {
            System.out.println(" - " + activite);
        }
        if (activites.isEmpty()) System.out.println("Aucune activité associé à l'élève");
    }

    public static void afficherDepensesVoitures(ArrayList<DepenseVoiture> depenses) {
        System.out.println("Liste de toutes les dépenses pour les voitures: ");
        for (DepenseVoiture depense : depenses) {
            System.out.println(" - " + depense);
        }
        if (depenses.isEmpty()) System.out.println("Aucune dépense de voiture dans le système.");
    }

    public static void afficherDepensesVoiture(Voiture voiture) {
        System.out.println("Liste des dépenses pour la voiture: ");
        System.out.println(" * " + voiture);
        ArrayList<DepenseVoiture> depenses = voiture.getDepensesVoiture();
        for (DepenseVoiture depense : depenses) {
            System.out.println(" - " + depense);
        }
        if (depenses.isEmpty()) System.out.println("Aucune dépense associé à la voiture");
    }

    public static void afficherVoitures(ArrayList<Voiture> voitures) {
        System.out.println("Liste de toutes les voitures: ");
        for (Voiture voiture : voitures) {
            System.out.println(" - " + voiture);
        }
        if (voitures.isEmpty()) System.out.println("Aucune voiture dans le système.");
    }

    public static void afficherPaiementsEleve(Eleve eleve, ArrayList<Paiement> paiements) {
        System.out.println("Liste des paiements pour cet élève: ");
        System.out.println(" * " + eleve);
        for (Paiement paiement : paiements) {
            System.out.println(" - " + paiement);
        }
        if (paiements.isEmpty()) System.out.println("Aucun paiement associé à cet élève.");
    }

    public static void afficherPaiementsImpayes(ArrayList<Paiement> paiements) {
        System.out.println("Liste de tous les paiements impayés: ");
        for (Paiement paiement : paiements) {
            System.out.println(" - " + paiement);
        }
        if (paiements.isEmpty()) System.out.println("Aucun paiement impayé.");
    }
}
