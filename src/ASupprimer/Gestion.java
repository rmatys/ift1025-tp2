package ASupprimer;
// package Controleur;

// import Modele.*;
// import Vue.Saisie;
// import Vue.VueMenu;

// import java.util.InputMismatchException;
// import java.util.Scanner;

// public class Gestion {
//     /**
//      * Affiche le menu principal de l'application et gère les choix de l'utilisateur
//      */
//     public static void gestionAutoEcole(Scanner scanner, AutoEcole autoEcole) {
//         VueMenu.afficherTitrePrincipal();

//         while(true) {
//             VueMenu.afficherOptionsPrincipal();

//             try {
//                 int choix = Saisie.demanderEntier(scanner, "Votre choix: ");

//                 switch (choix) {
//                     case 1:
//                         gestionEleves(scanner, autoEcole);
//                         break;
//                     case 2:
//                         gestionActivites(scanner, autoEcole);
//                         break;
//                     case 3:
//                         gestionFinanciere(scanner, autoEcole);
//                         break;
//                     case 4:
//                         gestionVoiture(scanner, autoEcole);
//                         break;
//                     case 5:
//                         gestionRapports(scanner, autoEcole);
//                         break;
//                     case 6:
//                         VueMenu.afficherAide();
//                         continue;
//                     case 7:
//                         Saisie.afficherMessage("Quitter.");
//                         return;
//                     default:
//                         Saisie.afficherMessage("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
//                 }
//             } catch (InputMismatchException e) {
//                 Saisie.afficherMessage("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
//             }
//         }
//     }

//     /**
//      * Affiche le menu de gestion des élèves et gère les choix de l'utilisateur
//      */
//     public static void gestionEleves(Scanner scanner, AutoEcole autoEcole) {
//         VueMenu.afficherTitreEleves();

//         while(true) {
//             VueMenu.afficherOptionsEleves();

//             try {
//                 int choix = Saisie.demanderEntier(scanner, "Votre choix: ");

//                 switch (choix) {
//                     case 1:
//                         Ajout.ajoutEleve(scanner, autoEcole);
//                         break;
//                     case 2:
//                         Recherche.rechercheEleve(scanner, autoEcole);
//                         break;
//                     case 3:
//                         Supprimer.supprimerEleve(scanner, autoEcole);
//                         break;
//                     case 4:
//                         Afficher.afficherEleves(autoEcole);
//                         break;
//                     case 5:
//                         Saisie.afficherMessage("Retour au menu principal.");
//                         return;
//                     default:
//                         Saisie.afficherMessage("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
//                         continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 Saisie.afficherMessage("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
//             }
//         }
//     }

//     /**
//      * Affiche le menu de gestion des activités et gère les choix de l'utilisateur
//      */
//     public static void gestionActivites(Scanner scanner, AutoEcole autoEcole) {
//         VueMenu.afficherTitreActivites();

//         while(true) {
//             VueMenu.afficherOptionsActivites();

//             try {
//                 int choix = Saisie.demanderEntier(scanner, "Votre choix: ");

//                 switch (choix) {
//                     case 1:
//                         Ajout.ajoutActivite(scanner, autoEcole);
//                         break;
//                     case 2:
//                         Recherche.rechercheActivite(scanner, autoEcole);
//                         break;
//                     case 3:
//                         Supprimer.supprimerActivite(scanner, autoEcole);
//                         break;
//                     case 4:
//                         Modifier.completerActivite(scanner, autoEcole);
//                         break;
//                     case 5:
//                         Afficher.afficherActivitesEleve(scanner, autoEcole);
//                         break;
//                     case 6:
//                         Afficher.afficherActivites(autoEcole);
//                         break;
//                     case 7:
//                         Saisie.afficherMessage("Retour au menu principal.");
//                         return;
//                     default:
//                         Saisie.afficherMessage("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
//                         continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 Saisie.afficherMessage("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
//             }
//         }
//     }

//     /**
//      * Affiche le menu de gestion financière et gère les choix de l'utilisateur
//      */
//     public static void gestionFinanciere(Scanner scanner, AutoEcole autoEcole) {
//         VueMenu.afficherTitreFinanciere();

//         while(true) {
//             VueMenu.afficherOptionsFinanciere();

//             try {
//                 int choix = Saisie.demanderEntier(scanner, "Votre choix: ");

//                 switch (choix) {
//                     case 1:
//                         gestionPaiements(scanner, autoEcole);
//                         break;
//                     case 2:
//                         Ajout.ajoutDepenseVoiture(scanner, autoEcole);
//                         break;
//                     case 3:
//                         Ajout.ajoutAutreDepense(scanner, autoEcole);
//                         break;
//                     case 4:
//                         Saisie.afficherMessage("Retour au menu principal.");
//                         return;
//                     default:
//                         Saisie.afficherMessage("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
//                         continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 Saisie.afficherMessage("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
//             }
//         }
//     }

//     /**
//      * Affiche le menu de gestion des paiements et gère les choix de l'utilisateur
//      */
//     public static void gestionPaiements(Scanner scanner, AutoEcole autoEcole) {
//         VueMenu.afficherTitrePaiements();

//         while(true) {
//             VueMenu.afficherOptionsPaiements();

//             try {
//                 int choix = Saisie.demanderEntier(scanner, "Votre choix: ");

//                 switch (choix) {
//                     case 1:
//                         Facture.genererFacture(scanner, autoEcole);
//                         break;
//                     case 2:
//                         Ajout.ajoutPaiement(scanner, autoEcole);
//                         break;
//                     case 3:
//                         Modifier.changerEtatPaiement(scanner, autoEcole);
//                         break;
//                     case 4:
//                         Recherche.recherchePaiement(scanner, autoEcole);
//                         break;
//                     case 5:
//                         Afficher.afficherPaiementsEleve(scanner, autoEcole);
//                         break;
//                     case 6:
//                         Afficher.afficherPaiementsImpayes(autoEcole);
//                         break;
//                     case 7:
//                         Saisie.afficherMessage("Retour au menu de gestion financière.");
//                         return;
//                     default:
//                         Saisie.afficherMessage("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
//                         continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 Saisie.afficherMessage("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
//             }
//         }
//     }

//     /**
//      * Affiche le menu de gestion des véhicules et gère les choix de l'utilisateur
//      */
//     public static void gestionVoiture(Scanner scanner, AutoEcole autoEcole) {
//         VueMenu.afficherTitreVoiture();

//         while(true) {
//             VueMenu.afficherOptionsVoiture();

//             try {
//                 int choix = Saisie.demanderEntier(scanner, "Votre choix: ");

//                 switch (choix) {
//                     case 1:
//                         Ajout.ajoutVoiture(scanner, autoEcole);
//                         break;
//                     case 2:
//                         Recherche.rechercheVoiture(scanner, autoEcole);
//                         break;
//                     case 3:
//                         Modifier.changerEtatVoiture(scanner, autoEcole);
//                         break;
//                     case 4:
//                         Ajout.ajoutDepenseVoiture(scanner, autoEcole);
//                         break;
//                     case 5:
//                         Afficher.afficherDepensesVoiture(scanner, autoEcole);
//                         break;
//                     case 6:
//                         Afficher.afficherDepensesVoitures(autoEcole);
//                         break;
//                     case 7:
//                         Afficher.afficherVoitures(autoEcole);
//                         break;
//                     case 8:
//                         Saisie.afficherMessage("Retour au menu principal.");
//                         return;
//                     default:
//                         Saisie.afficherMessage("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
//                         continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 Saisie.afficherMessage("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
//             }
//         }
//     }

//     /**
//      * Affiche le menu de gestion des rapports et gère les choix de l'utilisateur
//      */
//     public static void gestionRapports(Scanner scanner, AutoEcole autoEcole) {
//         VueMenu.afficherTitreRapports();

//         while(true) {
//             VueMenu.afficherOptionsRapports();

//             try {
//                 int choix = Saisie.demanderEntier(scanner, "Votre choix: ");

//                 switch (choix) {
//                     case 1:
//                         Rapports.genererRapportEleves(autoEcole.getEleves());
//                         break;
//                     case 2:
//                         Rapports.genererRapportRevenus(autoEcole.getActivites(), autoEcole.getPaiements());
//                         break;
//                     case 3:
//                         Rapports.genererRapportDepensesVoiture(autoEcole.getDepensesVoiture());
//                         break;
//                     case 4:
//                         Rapports.genererRapportAutresDepenses(autoEcole.getAutresDepenses());
//                         break;
//                     case 5:
//                         Rapports.genererRapportEleves(autoEcole.getEleves());
//                         Rapports.genererRapportRevenus(autoEcole.getActivites(), autoEcole.getPaiements());
//                         Rapports.genererRapportDepensesVoiture(autoEcole.getDepensesVoiture());
//                         Rapports.genererRapportAutresDepenses(autoEcole.getAutresDepenses());
//                         break;
//                     case 6:
//                         Saisie.afficherMessage("Retour au menu principal.");
//                         return;
//                     default:
//                         Saisie.afficherMessage("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
//                         continue;
//                 }
//                 break;
//             } catch (InputMismatchException e) {
//                 Saisie.afficherMessage("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
//             }
//         }
//     }
// }
