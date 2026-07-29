package ASupprimer;
// package Controleur;

// import Modele.AutoEcole;
// import Modele.Paiement;
// import Vue.Saisie;

// import java.util.Scanner;

// public class Facture {
//     /**
//      * Génère une facture pour un paiement en recherchant le paiement par son identifiant
//      */
//     public static void genererFacture(Scanner scanner, AutoEcole autoEcole) {
//         while(true) {
//             Saisie.afficherMessage("Générer une facture pour un paiement.");
//             Saisie.afficherMessage("Recherche d'un paiement par son identifiant (format : F-AAAA-XXXXX)");

//             try {
//                 String id = Saisie.demanderLigne(scanner, "Identifiant de paiement: ");

//                 Paiement paiement = autoEcole.rechercherPaiement(id);

//                 if (paiement == null) {
//                     Saisie.afficherMessage("Aucun paiement attaché à cet identificateur.");
//                     return;
//                 }

//                 Modele.Facture.genererFacture(paiement);
//                 Saisie.afficherMessage("Facture créé pour le paiement " + id);

//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
//             }
//         }
//     }
// }
