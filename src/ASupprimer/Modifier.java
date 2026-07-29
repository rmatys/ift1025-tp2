package ASupprimer;
// package Controleur;

// import Modele.*;
// import Vue.Saisie;

// import java.util.Scanner;

// public class Modifier {
//     /**
//      * Change l'état d'une activité en recherchant l'activité par son ID
//      */
//     public static void completerActivite(Scanner scanner, AutoEcole autoEcole) {
//         while(true) {
//             Saisie.afficherMessage("Recherche d'une activité par son ID");

//             try {
//                 int id = Saisie.demanderEntier(scanner, "ID de l'activité: ");

//                 Activite activite = autoEcole.rechercherActivite(id);

//                 if (activite == null) {
//                     Saisie.afficherMessage("Aucune activité attaché à cet identificateur.");
//                     return;
//                 }

//                 autoEcole.completerActivite(id);
//                 Saisie.afficherMessage(" - " + activite);
//                 Saisie.afficherMessage("L'activité à été marqué comme complété");

//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: il faut un numéro (int). Réessaie");
//             }
//         }
//     }

//     /**
//      * Change l'état d'une voiture en recherchant la voiture par sa plaque d'immatriculation
//      */
//     public static void changerEtatVoiture(Scanner scanner, AutoEcole autoEcole) {
//         while(true) {
//             Saisie.afficherMessage("Recherche d'une voiture par sa plaque d'immatriculation");

//             try {
//                 String plaque = Saisie.demanderLigne(scanner, "Plaque d'immatriculation: ");

//                 Voiture voiture = autoEcole.rechercherVoiture(plaque);

//                 if (voiture == null) {
//                     Saisie.afficherMessage("Voiture de l'extérieur (non modifiable).");
//                     return;
//                 }

//                 Saisie.afficherMessage("Voiture actuelle: ");
//                 Saisie.afficherMessage(" - " + voiture);
//                 subChangerEtatVoiture(scanner, autoEcole, voiture);

//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
//             }
//         }
//     }

//     /**
//      * Change l'état d'une voiture en demandant à l'utilisateur le nouvel état
//      */
//     public static void subChangerEtatVoiture(Scanner scanner, AutoEcole autoEcole, Voiture voiture) {
//         while(true) {
//             Saisie.afficherMessage("Changement de l'état, choix disponibles  R (réparation), V (vendu), D (disponible).");

//             try {
//                 String etat = Saisie.demanderLigne(scanner, "Votre choix: ");

//                 StatutVoiture statutVoiture = StatutVoiture.valueOf(etat);

//                 autoEcole.changerEtatVoiture(voiture.getPlaque(), statutVoiture);
//                 Saisie.afficherMessage("Changement effectué, la voiture est maintenant dans l'état " + statutVoiture.getLibelle());

//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: les options sont R, V et D. Réessaie");
//             }
//         }
//     }

//     /**
//      * Change l'état d'un paiement en recherchant le paiement par son identifiant
//      */
//     public static void changerEtatPaiement(Scanner scanner, AutoEcole autoEcole) {
//         while(true) {
//             Saisie.afficherMessage("Recherche d'un paiement par son identifiant (format : F-AAAA-XXXXX)");

//             try {
//                 String id = Saisie.demanderLigne(scanner, "Identifiant de paiement: ");

//                 Paiement paiement = autoEcole.rechercherPaiement(id);

//                 if (paiement == null) {
//                     Saisie.afficherMessage("Aucun paiement attaché à cet identificateur.");
//                     return;
//                 }

//                 Saisie.afficherMessage("Paiement actuel: ");
//                 Saisie.afficherMessage(" - " + paiement);
//                 subChangerEtatPaiement(scanner, autoEcole, paiement);

//                 break;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: il faut un numéro (long). Réessaie");
//             }
//         }
//     }

//     /**
//      * Change l'état d'un paiement en demandant à l'utilisateur le nouvel état
//      */
//     public static void subChangerEtatPaiement(Scanner scanner, AutoEcole autoEcole, Paiement paiement) {
//         while(true) {
//             Saisie.afficherMessage("Changement de l'état, choix disponibles  P (payé), I (impayé), PP (partiellement payé).");

//             try {
//                 String etat = Saisie.demanderLigne(scanner, "Votre choix: ");

//                 StatutPaiement statutPaiement = StatutPaiement.valueOf(etat);

//                 double montantRestant = paiement.getMontantRestant();
//                 if (statutPaiement.equals(StatutPaiement.PP)) {
//                     montantRestant = demanderMontantRestant(scanner, paiement);
//                 }

//                 autoEcole.changerEtatPaiement(paiement.getId(), statutPaiement, montantRestant);
//                 Saisie.afficherMessage("Changement effectué, le paiement est maintenant dans l'état " + statutPaiement.getLibelle());

//                 break;

//             } catch (OperationInvalideException e) {
//                 Saisie.afficherMessage("Erreur: " + e.getMessage() + ". Réessaie");
//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: les options sont P, I et PP. Réessaie");
//             }
//         }
//     }

//     /**
//      * Demande à l'utilisateur le nouveau montant restant pour un paiement partiellement payé
//      */
//     private static double demanderMontantRestant(Scanner scanner, Paiement paiement) {
//         while (true) {
//             Saisie.afficherMessage("Paiement à été partiellement payé.");

//             try {
//                 double montantRestant = Saisie.demanderDouble(scanner, "Montant restant: ");

//                 if (montantRestant > paiement.getMontantRestant()) {
//                     Saisie.afficherMessage("Erreur: le montant restant doit être plus petit que ce qu'il y avait avant. Réessaie");
//                     continue;
//                 }

//                 return montantRestant;

//             } catch (Exception e) {
//                 Saisie.afficherMessage("Erreur: le montant restant doit être un nombre à virgule (double).");
//             }
//         }
//     }
// }
