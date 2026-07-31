package tp2.src.vue;

import tp2.src.modele.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Afficher {
    /**
     * Affiche la liste de tous les élèves de l'auto-école
     */
    public static void afficherEleves(AutoEcole autoEcole) {
        int count = 0;
        System.out.println("Liste de tous les élèves de l'école: ");
        for (Eleve eleve : autoEcole.getEleves()) {
            System.out.println(" - " + eleve);
            count++;
        }
        if (count == 0) System.out.println("Aucun élève dans le système.");
    }

    /**
     * Affiche la liste de toutes les activités de l'auto-école
     */
    public static void afficherActivites(AutoEcole autoEcole) {
        int count = 0;
        System.out.println("Liste de toutes les activités: ");
        for (Activite activite : autoEcole.getActivites()) {
            System.out.println(" - " + activite);
            count++;
        }
        if (count == 0) System.out.println("Aucune activité dans le système.");
    }

    /**
     * Affiche la liste de toutes les activités d'un élève en recherchant l'élève par son numéro SAAQ
     */
    public static void afficherActivitesEleve(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'un élève par son numéro SAAQ");
            System.out.print("Numéro SAAQ: ");

            try {
                long numSAAQ = scanner.nextLong();
                scanner.nextLine();

                Eleve eleve = autoEcole.rechercherEleve(numSAAQ);

                if (eleve == null) {
                    System.out.println("Aucun élève attaché à ce numéro.");
                    return;
                }

                System.out.println("Liste des activités de l'élève: ");
                System.out.println(" * " + eleve);
                int count = 0;
                for (Activite activite : autoEcole.getActivites()) {
                    if (activite.getEleve().equals(eleve)) {
                        System.out.println(" - " + activite);
                        count++;
                    }
                }
                if (count == 0) System.out.println("Aucune activité associé à l'élève");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Affiche la liste de toutes les dépenses pour les voitures de l'auto-école
     */
    public static void afficherDepensesVoitures(AutoEcole autoEcole) {
        int count = 0;
        System.out.println("Liste de toutes les dépenses pour les voitures: ");
        for (DepenseVoiture depense : autoEcole.getDepensesVoiture()) {
            System.out.println(" - " + depense);
            count++;
        }
        if (count == 0) System.out.println("Aucune dépense de voiture dans le système.");
    }

    /**
     * Affiche la liste de toutes les dépenses pour une voiture spécifique en recherchant la voiture par sa plaque d'immatriculation
     */
    public static void afficherDepensesVoiture(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'une voiture par sa plaque d'immatriculation");
            System.out.print("Plaque d'immatriculation: ");

            try {
                String plaque = scanner.nextLine();

                Voiture voiture = autoEcole.rechercherVoiture(plaque);

                if (voiture == null) {
                    System.out.println("Voiture de l'extérieur. Comptabilisation des dépenses seulement pour les voitures de l'école. ");
                    return;
                }

                System.out.println("Liste des dépenses pour la voiture: ");
                System.out.println(" * " + voiture);
                ArrayList<DepenseVoiture> depenses = voiture.getDepensesVoiture();
                for (DepenseVoiture depense : depenses) { System.out.println(" - " + depense); }
                if (depenses.isEmpty()) System.out.println("Aucune dépense associé à la voiture");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Affiche la liste de toutes les voitures de l'auto-école
     */
    public static void afficherVoitures(AutoEcole autoEcole) {
        int count = 0;
        System.out.println("Liste de toutes les voitures: ");
        for (Voiture voiture : autoEcole.getVoitures()) {
            System.out.println(" - " + voiture);
            count++;
        }
        if (count == 0) System.out.println("Aucune voiture dans le système.");
    }

    /**
     * Affiche la liste de tous les paiements d'un élève en recherchant l'élève par son numéro SAAQ
     */
    public static void afficherPaiementsEleve(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'un élève par son numéro SAAQ");
            System.out.print("Numéro SAAQ: ");

            try {
                long numSAAQ = scanner.nextLong();
                scanner.nextLine();

                Eleve eleve = autoEcole.rechercherEleve(numSAAQ);

                if (eleve == null) {
                    System.out.println("Aucun élève attaché à ce numéro.");
                    return;
                }

                System.out.println("Liste des paiements pour cet élève: ");
                System.out.println(" * " + eleve);
                ArrayList<Paiement> paiements = autoEcole.getPaiements();
                for (Paiement paiement : paiements) { System.out.println(" - " + paiement); }
                if (paiements.isEmpty()) System.out.println("Aucun paiement associé à cet élève.");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Affiche la liste de tous les paiements impayés
     */
    public static void afficherPaiementsImpayes(AutoEcole autoEcole) {
        System.out.println("Liste de tous les paiements impayés: ");

        int count = 0;
        for (Paiement paiement : autoEcole.getPaiements()) {
            if (paiement.getStatutPaiement().equals(StatutPaiement.I)) {
                System.out.println(" - " + paiement);
                count++;
            }
        }

        if (count == 0) System.out.println("Aucun paiement impayé.");
    }
}
