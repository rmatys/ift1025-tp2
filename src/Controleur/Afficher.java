package Controleur;

import Modele.*;
import Vue.Saisie;

import java.util.Scanner;

/**
 * Recherche les entités demandées dans le Modèle puis délègue l'affichage à Vue.Afficher
 */
public class Afficher {
    /**
     * Affiche la liste de tous les élèves de l'auto-école
     */
    public static void afficherEleves(AutoEcole autoEcole) {
        Vue.Afficher.afficherEleves(autoEcole.getEleves());
    }

    /**
     * Affiche la liste de toutes les activités de l'auto-école
     */
    public static void afficherActivites(AutoEcole autoEcole) {
        Vue.Afficher.afficherActivites(autoEcole.getActivites());
    }

    /**
     * Affiche la liste de toutes les activités d'un élève en recherchant l'élève par son numéro SAAQ
     */
    public static void afficherActivitesEleve(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            Saisie.afficherMessage("Recherche d'un élève par son numéro SAAQ");

            try {
                long numSAAQ = Saisie.demanderLong(scanner, "Numéro SAAQ: ");

                Eleve eleve = autoEcole.rechercherEleve(numSAAQ);

                if (eleve == null) {
                    Saisie.afficherMessage("Aucun élève attaché à ce numéro.");
                    return;
                }

                Vue.Afficher.afficherActivitesEleve(eleve, autoEcole.getActivitesEleve(eleve));

                break;

            } catch (Exception e) {
                Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Affiche la liste de toutes les dépenses pour les voitures de l'auto-école
     */
    public static void afficherDepensesVoitures(AutoEcole autoEcole) {
        Vue.Afficher.afficherDepensesVoitures(autoEcole.getDepensesVoiture());
    }

    /**
     * Affiche la liste de toutes les dépenses pour une voiture spécifique en recherchant la voiture par sa plaque d'immatriculation
     */
    public static void afficherDepensesVoiture(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            Saisie.afficherMessage("Recherche d'une voiture par sa plaque d'immatriculation");

            try {
                String plaque = Saisie.demanderLigne(scanner, "Plaque d'immatriculation: ");

                Voiture voiture = autoEcole.rechercherVoiture(plaque);

                if (voiture == null) {
                    Saisie.afficherMessage("Voiture de l'extérieur. Comptabilisation des dépenses seulement pour les voitures de l'école. ");
                    return;
                }

                Vue.Afficher.afficherDepensesVoiture(voiture);

                break;

            } catch (Exception e) {
                Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Affiche la liste de toutes les voitures de l'auto-école
     */
    public static void afficherVoitures(AutoEcole autoEcole) {
        Vue.Afficher.afficherVoitures(autoEcole.getVoitures());
    }

    /**
     * Affiche la liste de tous les paiements d'un élève en recherchant l'élève par son numéro SAAQ
     */
    public static void afficherPaiementsEleve(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            Saisie.afficherMessage("Recherche d'un élève par son numéro SAAQ");

            try {
                long numSAAQ = Saisie.demanderLong(scanner, "Numéro SAAQ: ");

                Eleve eleve = autoEcole.rechercherEleve(numSAAQ);

                if (eleve == null) {
                    Saisie.afficherMessage("Aucun élève attaché à ce numéro.");
                    return;
                }

                Vue.Afficher.afficherPaiementsEleve(eleve, autoEcole.getPaiementsEleve(eleve));

                break;

            } catch (Exception e) {
                Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Affiche la liste de tous les paiements impayés
     */
    public static void afficherPaiementsImpayes(AutoEcole autoEcole) {
        Vue.Afficher.afficherPaiementsImpayes(autoEcole.getPaiementsImpayes());
    }
}
