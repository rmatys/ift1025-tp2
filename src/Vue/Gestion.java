package tp2.src.vue;

import tp2.src.modele.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Gestion {
    /**
     * Affiche le menu principal de l'application et gère les choix de l'utilisateur
     */
    public static void gestionAutoEcole(Scanner scanner, AutoEcole autoEcole) {
        System.out.println("============================================");
        System.out.println("                AUTO-ÉCOLE");

        while(true) {
            System.out.println("============================================");
            System.out.println("1. Gestion des élèves");
            System.out.println("2. Gestion des activités");
            System.out.println("3. Gestion des finances");
            System.out.println("4. Gestion des véhicules");
            System.out.println("5. Génération de rapports");
            System.out.println("6. Aide pour les types de valeurs");
            System.out.println("7. Quitter");
            System.out.println("============================================");
            System.out.print("Votre choix: ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        gestionEleves(scanner, autoEcole);
                        break;
                    case 2:
                        gestionActivites(scanner, autoEcole);
                        break;
                    case 3:
                        gestionFinanciere(scanner, autoEcole);
                        break;
                    case 4:
                        gestionVoiture(scanner, autoEcole);
                        break;
                    case 5:
                        gestionRapports(scanner, autoEcole);
                        break;
                    case 6:
                        help();
                        continue;
                    case 7:
                        System.out.println("Quitter.");
                        return;
                    default:
                        System.out.println("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
            }
        }
    }

    /**
     * Affiche le menu de gestion des élèves et gère les choix de l'utilisateur
     */
    public static void gestionEleves(Scanner scanner, AutoEcole autoEcole) {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES ÉLÈVES");

        while(true) {
            System.out.println("--------------------------------------------");
            System.out.println("1. Inscrire un nouvel élève");
            System.out.println("2. Rechercher un élève");
            System.out.println("3. Supprimer un élève");
            System.out.println("4. Afficher la liste des élèves");
            System.out.println("5. Retour au menu principal");
            System.out.println("--------------------------------------------");
            System.out.print("Votre choix: ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        Ajout.ajoutEleve(scanner, autoEcole);
                        break;
                    case 2:
                        Recherche.rechercheEleve(scanner, autoEcole);
                        break;
                    case 3:
                        Supprimer.supprimerEleve(scanner, autoEcole);
                        break;
                    case 4:
                        Afficher.afficherEleves(autoEcole);
                        break;
                    case 5:
                        System.out.println("Retour au menu principal.");
                        return;
                    default:
                        System.out.println("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
                        continue;
                }
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
            }
        }
    }

    /**
     * Affiche le menu de gestion des activités et gère les choix de l'utilisateur
     */
    public static void gestionActivites(Scanner scanner, AutoEcole autoEcole) {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES ACTIVITÉS");

        while(true) {
            System.out.println("--------------------------------------------");
            System.out.println("1. Planifier une nouvelle activité");
            System.out.println("2. Rechercher une activité");
            System.out.println("3. Annuler une activité");
            System.out.println("4. Marquer une activité comme complétée");
            System.out.println("5. Afficher les activités d'un élève");
            System.out.println("6. Afficher toutes les activités");
            System.out.println("7. Retour au menu principal");
            System.out.println("--------------------------------------------");
            System.out.print("Votre choix: ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        Ajout.ajoutActivite(scanner, autoEcole);
                        break;
                    case 2:
                        Recherche.rechercheActivite(scanner, autoEcole);
                        break;
                    case 3:
                        Supprimer.supprimerActivite(scanner, autoEcole);
                        break;
                    case 4:
                        Modifier.completerActivite(scanner, autoEcole);
                        break;
                    case 5:
                        Afficher.afficherActivitesEleve(scanner, autoEcole);
                        break;
                    case 6:
                        Afficher.afficherActivites(autoEcole);
                        break;
                    case 7:
                        System.out.println("Retour au menu principal.");
                        return;
                    default:
                        System.out.println("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
                        continue;
                }
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
            }
        }
    }

    /**
     * Affiche le menu de gestion financière et gère les choix de l'utilisateur
     */
    public static void gestionFinanciere(Scanner scanner, AutoEcole autoEcole) {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION FINANCIÈRE");

        while(true) {
            System.out.println("--------------------------------------------");
            System.out.println("1. Paiements");
            System.out.println("2. Enregistrer une dépense du véhicule");
            System.out.println("3. Enregistrer une dépense autre");
            System.out.println("4. Retour au menu principal");
            System.out.println("--------------------------------------------");
            System.out.print("Votre choix: ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        gestionPaiements(scanner, autoEcole);
                        break;
                    case 2:
                        Ajout.ajoutDepenseVoiture(scanner, autoEcole);
                        break;
                    case 3:
                        Ajout.ajoutAutreDepense(scanner, autoEcole);
                        break;
                    case 4:
                        System.out.println("Retour au menu principal.");
                        return;
                    default:
                        System.out.println("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
                        continue;
                }
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
            }
        }
    }

    /**
     * Affiche le menu de gestion des paiements et gère les choix de l'utilisateur
     */
    public static void gestionPaiements(Scanner scanner, AutoEcole autoEcole) {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES PAIEMENTS");

        while(true) {
            System.out.println("--------------------------------------------");
            System.out.println("1. Générer une facture pour une activité");
            System.out.println("2. Enregistrer un paiement");
            System.out.println("3. Modifier le statut d'un paiement");
            System.out.println("4. Rechercher un paiement");
            System.out.println("5. Afficher les paiements d'un élève");
            System.out.println("6. Afficher les impayés");
            System.out.println("7. Retour au menu de gestion financière");
            System.out.println("--------------------------------------------");
            System.out.print("Votre choix: ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        Facture.genererFacture(scanner, autoEcole);
                        break;
                    case 2:
                        Ajout.ajoutPaiement(scanner, autoEcole);
                        break;
                    case 3:
                        Modifier.changerEtatPaiement(scanner, autoEcole);
                        break;
                    case 4:
                        Recherche.recherchePaiement(scanner, autoEcole);
                        break;
                    case 5:
                        Afficher.afficherPaiementsEleve(scanner, autoEcole);
                        break;
                    case 6:
                        Afficher.afficherPaiementsImpayes(autoEcole);
                        break;
                    case 7:
                        System.out.println("Retour au menu de gestion financière.");
                        return;
                    default:
                        System.out.println("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
                        continue;
                }
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
            }
        }
    }

    /**
     * Affiche le menu de gestion des véhicules et gère les choix de l'utilisateur
     */
    public static void gestionVoiture(Scanner scanner, AutoEcole autoEcole) {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES VOITURES");

        while(true) {
            System.out.println("--------------------------------------------");
            System.out.println("1. Ajouter une voiture à l'école");
            System.out.println("2. Rechercher une voiture");
            System.out.println("3. Modifier l'état d'une voiture");
            System.out.println("4. Enregistrer une dépense");
            System.out.println("5. Afficher l'historique des dépenses pour une voiture spécifique");
            System.out.println("6. Afficher toutes les dépenses pour les voitures");
            System.out.println("7. Afficher la liste des voitures");
            System.out.println("8. Retour au menu principal");
            System.out.println("--------------------------------------------");
            System.out.print("Votre choix: ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        Ajout.ajoutVoiture(scanner, autoEcole);
                        break;
                    case 2:
                        Recherche.rechercheVoiture(scanner, autoEcole);
                        break;
                    case 3:
                        Modifier.changerEtatVoiture(scanner, autoEcole);
                        break;
                    case 4:
                        Ajout.ajoutDepenseVoiture(scanner, autoEcole);
                        break;
                    case 5:
                         Afficher.afficherDepensesVoiture(scanner, autoEcole);
                        break;
                    case 6:
                        Afficher.afficherDepensesVoitures(autoEcole);
                        break;
                    case 7:
                        Afficher.afficherVoitures(autoEcole);
                        break;
                    case 8:
                        System.out.println("Retour au menu principal.");
                        return;
                    default:
                        System.out.println("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
                        continue;
                }
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
            }
        }
    }

    /**
     * Affiche le menu de gestion des rapports et gère les choix de l'utilisateur
     */
    public static void gestionRapports(Scanner scanner, AutoEcole autoEcole) {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES RAPPORTS");

        while(true) {
            System.out.println("--------------------------------------------");
            System.out.println("1. Rapport des élèves");
            System.out.println("2. Rapport de revenus");
            System.out.println("3. Rapport des dépenses de voiture");
            System.out.println("4. Rapport des autres dépenses");
            System.out.println("5. Générer tous les rapports");
            System.out.println("6. Retour au menu principal");
            System.out.println("--------------------------------------------");
            System.out.print("Votre choix: ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        Rapports.genererRapportEleves(autoEcole.getEleves());
                        break;
                    case 2:
                        Rapports.genererRapportRevenus(autoEcole.getActivites(), autoEcole.getPaiements());
                        break;
                    case 3:
                        Rapports.genererRapportDepensesVoiture(autoEcole.getDepensesVoiture());
                        break;
                    case 4:
                        Rapports.genererRapportAutresDepenses(autoEcole.getAutresDepenses());
                        break;
                    case 5:
                        Rapports.genererRapportEleves(autoEcole.getEleves());
                        Rapports.genererRapportRevenus(autoEcole.getActivites(), autoEcole.getPaiements());
                        Rapports.genererRapportDepensesVoiture(autoEcole.getDepensesVoiture());
                        Rapports.genererRapportAutresDepenses(autoEcole.getAutresDepenses());
                        break;
                    case 6:
                        System.out.println("Retour au menu principal.");
                        return;
                    default:
                        System.out.println("Erreur: il faut un chiffre qui correspond à l'une des options. Réessaie");
                        continue;
                }
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Erreur: il faut un chiffre (integer) qui correspond à l'une des options. Réessaie");
            }
        }
    }

    /**
     * Affiche les règles pour les différents types de valeurs
     */
    public static void help() {
        System.out.println("------------------------------------------------------");
        System.out.println("Voici les règles pour les différents types de valeurs");

        System.out.print("MethodePaiement: ");
        for (MethodePaiement m : MethodePaiement.values()) {
            System.out.print(m + "(" + m.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("StatutActivite: ");
        for (StatutActivite s : StatutActivite.values()) {
            System.out.print(s + "(" + s.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("StatutPaiement: ");
        for (StatutPaiement s : StatutPaiement.values()) {
            System.out.print(s + "(" + s.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("StatutVoiture: ");
        for (StatutVoiture s : StatutVoiture.values()) {
            System.out.print(s + "(" + s.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("TypeActivite: ");
        for (TypeActivite t : TypeActivite.values()) {
            System.out.print(t + "(" + t.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("TypeAutreDepense: ");
        for (TypeAutreDepense t : TypeAutreDepense.values()) {
            System.out.print(t + "(" + t.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("TypeDepenseVoiture: ");
        for (TypeDepenseVoiture t : TypeDepenseVoiture.values()) {
            System.out.print(t + "(" + t.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.println("NumSAAQ: long");
        System.out.println("Téléphone: String");
        System.out.println("Date: format JJ-MM-AAAA");
        System.out.println("Heure: format HH:MM");
        System.out.println("Duree: int (minute)");
        System.out.println("Plaque: String (3 lettres, 3 chiffres)");
        System.out.println("ID: int");
        System.out.println("Montant: double");
        System.out.println("Prix: double");
        System.out.println("Kilomètres: int");

        System.out.println("------------------------------------------------------");
    }
}
