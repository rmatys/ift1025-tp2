package ASupprimer;
// package Controleur;

// import Modele.*;
// import Vue.Saisie;

// import java.util.Scanner;

// public class Recherche {
//     /**
//      * Recherche un élève par son numéro SAAQ et affiche ses informations
//      */
//     public static void rechercheEleve(Scanner scanner, AutoEcole autoEcole) {
//         while(true) {
//             Saisie.afficherMessage("Recherche d'un élève par son numéro SAAQ");

//             try {
//                 long numSAAQ = Saisie.demanderLong(scanner, "Numéro SAAQ: ");

//                 Eleve eleve = autoEcole.rechercherEleve(numSAAQ);

//                 if (eleve == null) {
//                     Saisie.afficherMessage("Aucun élève attaché à ce numéro.");
//                     return;
//                 }

//                 Saisie.afficherMessage(" - " + eleve);
//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
//             }
//         }
//     }

//     /**
//      * Recherche une activité par son ID et affiche ses informations
//      */
//     public static void rechercheActivite(Scanner scanner, AutoEcole autoEcole) {
//         while(true) {
//             Saisie.afficherMessage("Recherche d'une activité par son ID");

//             try {
//                 int id = Saisie.demanderEntier(scanner, "ID de l'activité: ");

//                 Activite activite = autoEcole.rechercherActivite(id);

//                 if (activite == null) {
//                     Saisie.afficherMessage("Aucune activité attaché à cet identificateur.");
//                     return;
//                 }

//                 Saisie.afficherMessage(" - " + activite);

//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: il faut un numéro (int). Réessaie");
//             }
//         }
//     }

//     /**
//      * Recherche un paiement par son ID et affiche ses informations
//      */
//     public static void recherchePaiement(Scanner scanner, AutoEcole autoEcole) {
//         while(true) {
//             Saisie.afficherMessage("Recherche d'un paiement par son ID (format \"F-AAAA-XXXXX\")");

//             try {
//                 String id = Saisie.demanderLigne(scanner, "ID: ");

//                 Paiement paiement = autoEcole.rechercherPaiement(id);

//                 if (paiement == null) {
//                     Saisie.afficherMessage("Aucun paiement attaché à cet identifiant.");
//                     return;
//                 }

//                 Saisie.afficherMessage(" - " + paiement);
//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: il faut un identifiant (String format \"F-AAAA-XXXXX\"). Réessaie");
//             }
//         }
//     }

//     /**
//      * Recherche une voiture par sa plaque d'immatriculation et affiche ses informations
//      */
//     public static void rechercheVoiture(Scanner scanner, AutoEcole autoEcole) {
//         while(true) {
//             Saisie.afficherMessage("Recherche d'une voiture par sa plaque d'immatriculation");

//             try {
//                 String plaque = Saisie.demanderLigne(scanner, "Plaque d'immatriculation: ");

//                 Voiture voiture = autoEcole.rechercherVoiture(plaque);

//                 if (voiture == null) {
//                     Saisie.afficherMessage("Voiture de l'extérieur.");
//                     return;
//                 }

//                 Saisie.afficherMessage(" - " + voiture);
//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
//             }
//         }
//     }
// }
