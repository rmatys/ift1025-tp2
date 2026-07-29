package Controleur;

import Modele.*;
import Vue.Saisie;

import java.util.Scanner;

public class Supprimer {
    /**
     * Supprime un élève par son numéro SAAQ
     */
    public static void supprimerEleve(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            Saisie.afficherMessage("Recherche d'un élève par son numéro SAAQ");

            try {
                long numSAAQ = Saisie.demanderLong(scanner, "Numéro SAAQ: ");

                Eleve eleve = autoEcole.rechercherEleve(numSAAQ);

                if (eleve == null) {
                    Saisie.afficherMessage("Aucun élève attaché à ce numéro.");
                    return;
                }

                autoEcole.supprimerEleve(numSAAQ);
                Saisie.afficherMessage(" - " + eleve);
                Saisie.afficherMessage("L'élève à été supprimé");

                break;

            } catch (Exception e) {
                Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Supprime une activité par son ID
     */
    public static void supprimerActivite(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            Saisie.afficherMessage("Recherche d'une activité par son ID");

            try {
                int id = Saisie.demanderEntier(scanner, "ID de l'activité: ");

                Activite activite = autoEcole.rechercherActivite(id);

                if (activite == null) {
                    Saisie.afficherMessage("Aucune activité attaché à cet identificateur.");
                    return;
                }

                autoEcole.annulerActivite(id);
                Saisie.afficherMessage(" - " + activite);
                Saisie.afficherMessage("L'activité à été annulé");

                break;

            } catch (Exception e) {
                Saisie.afficherMessage("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }
}
