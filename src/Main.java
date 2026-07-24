import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe principale de l'application de gestion d'une auto-école
 */
public class Main {
    Scanner scanner = new Scanner(System.in);
    AutoEcole autoEcole = new AutoEcole();

    /**
     * Point d'entrée de l'application
     */
    void main() {
        // test();

        autoEcole.chargerEleves();
        autoEcole.chargerActivites();
        autoEcole.chargerPaiements();
        autoEcole.chargerDepenses();
        autoEcole.chargerVoitures();

        gestionAutoEcole();

        sauvegarde();
    }

    /**
     * Tests internes pour l'auto-école
     *
     * Résultats:
     * ============================================
     *               TESTS INTERNES
     * ============================================
     *
     * --- Test 1 : Ajout élève ---
     * OK: élève ajouté
     *
     * --- Test 2 : Ajout voiture ---
     * OK: voiture ajoutée
     *
     * --- Test 3 : Ajout activité ---
     * OK: activité ajoutée
     *
     * --- Test 4 : Conflit horaire ---
     * OK: conflit détecté
     *
     * --- Test 5 : Ajout dépense voiture ---
     * OK: dépense ajoutée
     *
     * --- Test 6 : Ajout paiement ---
     * OK: paiement ajouté
     *
     * ============================================
     * Tests terminés.
     * ============================================
     */
    public void test() {
        System.out.println("============================================");
        System.out.println("              TESTS INTERNES");
        System.out.println("============================================");

        AutoEcole testAE = new AutoEcole();

        System.out.println("\n--- Test 1 : Ajout élève ---");
        Eleve e = new Eleve(111111111L, "Test", "Eleve", "adr", "tel", LocalDate.now());
        testAE.ajouterEleve(e);
        System.out.println(testAE.rechercherEleve(111111111L) != null
                ? "OK: élève ajouté"
                : "ERREUR: élève non trouvé");

        System.out.println("\n--- Test 2 : Ajout voiture ---");
        String plaque = "ABC123";
        Voiture v = new Voiture(plaque, "Toyota", 2020, 25000, 15000, StatutVoiture.D, 45000, new ArrayList<>());
        testAE.ajouterVoiture(v);
        System.out.println(testAE.rechercherVoiture(plaque) != null
                ? "OK: voiture ajoutée"
                : "ERREUR: voiture non trouvée");

        System.out.println("\n--- Test 3 : Ajout activité ---");
        PlageHoraire ph = new PlageHoraire(LocalDate.now(), LocalTime.of(9, 0), 60);
        Activite a = new Activite(1, ph, e, plaque, TypeActivite.LPA, StatutActivite.C);
        testAE.ajouterActivite(a);
        System.out.println(testAE.rechercherActivite(1) != null
                ? "OK: activité ajoutée"
                : "ERREUR: activité non trouvée");

        System.out.println("\n--- Test 4 : Conflit horaire ---");
        PlageHoraire ph2 = new PlageHoraire(LocalDate.now(), LocalTime.of(9, 30), 60);
        ArrayList<PlageHoraire> plagesHoraire = new ArrayList<>();
        for (Activite activite : testAE.getActivites()) {
            plagesHoraire.add(activite.getPlageHoraire());
        }
        boolean conflit = ph2.estEnConflitHoraire(plagesHoraire);
        System.out.println(conflit
                ? "OK: conflit détecté"
                : "ERREUR: conflit NON détecté");

        System.out.println("\n--- Test 5 : Ajout dépense voiture ---");
        DepenseVoiture d = new DepenseVoiture(1, "ABC123", LocalDate.now(), TypeDepenseVoiture.R, "Freins", 350.0);
        testAE.ajouterDepenseVoiture(d);
        v.updateDepenses(testAE.getDepensesVoiture());
        System.out.println(v.getDepensesVoiture().size() == 1
                ? "OK: dépense ajoutée"
                : "ERREUR: dépense non ajoutée");

        System.out.println("\n--- Test 6 : Ajout paiement ---");
        Paiement p = new Paiement(1, LocalDate.now(), StatutPaiement.I, a, MethodePaiement.E, e);
        testAE.ajouterPaiement(p);
        System.out.println(testAE.rechercherPaiement(p.getId()) != null
                ? "OK: paiement ajouté"
                : "ERREUR: paiement non trouvé");

        System.out.println("\n============================================");
        System.out.println("Tests terminés.");
        System.out.println("============================================");
    }


    // Menus

    /**
     * Affiche le menu principal de l'application et gère les choix de l'utilisateur
     */
    public void gestionAutoEcole() {
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
                        gestionEleves();
                        break;
                    case 2:
                        gestionActivites();
                        break;
                    case 3:
                        gestionFinanciere();
                        break;
                    case 4:
                        gestionVoiture();
                        break;
                    case 5:
                        gestionRapports();
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
    public void gestionEleves() {
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
                        ajoutEleve();
                        break;
                    case 2:
                        rechercheEleve();
                        break;
                    case 3:
                        supprimerEleve();
                        break;
                    case 4:
                        afficherEleves();
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
    public void gestionActivites() {
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
                        ajoutActivite();
                        break;
                    case 2:
                        rechercheActivite();
                        break;
                    case 3:
                        supprimerActivite();
                        break;
                    case 4:
                        completerActivite();
                        break;
                    case 5:
                        afficherActivitesEleve();
                        break;
                    case 6:
                        afficherActivites();
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
    public void gestionFinanciere() {
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
                        gestionPaiements();
                        break;
                    case 2:
                        ajoutDepenseVoiture();
                        break;
                    case 3:
                        ajoutAutreDepense();
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
    public void gestionPaiements() {
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
                        genererFacture();
                        break;
                    case 2:
                        ajoutPaiement();
                        break;
                    case 3:
                        changerEtatPaiement();
                        break;
                    case 4:
                        recherchePaiement();
                        break;
                    case 5:
                        afficherPaiementsEleve();
                        break;
                    case 6:
                        afficherPaiementsImpayes();
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
    public void gestionVoiture() {
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
                        ajoutVoiture();
                        break;
                    case 2:
                        rechercheVoiture();
                        break;
                    case 3:
                        changerEtatVoiture();
                        break;
                    case 4:
                        ajoutDepenseVoiture();
                        break;
                    case 5:
                        afficherDepensesVoiture();
                        break;
                    case 6:
                        afficherDepensesVoitures();
                        break;
                    case 7:
                        afficherVoitures();
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
    public void gestionRapports() {
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


    // Ajouts

    /**
     * Ajoute un nouvel élève à l'auto-école en demandant les informations à l'utilisateur
     */
    public void ajoutEleve() {
        int bonneLongueur = 5;

        while (true) {
            System.out.println("Ajout d'un nouvel élève");
            System.out.println("Donner les informations sous ce format:");
            System.out.println("NumSAAQ,Nom,Prenom,Adresse,Telephone");
            System.out.println("ex: 123456789,Dupont,Marie,adr1,5145551234");
            System.out.print("Votre entrée: ");
            String ligne = scanner.nextLine();

            String[] infosEleve = ligne.split(",");

            if (infosEleve.length != bonneLongueur) {
                System.out.println("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
                continue;
            }

            try {
                for (int i=0; i<bonneLongueur; i++) {
                    infosEleve[i] = infosEleve[i].trim();
                }

                long numSAAQ = Long.parseLong(infosEleve[0]);
                String nom = infosEleve[1];
                String prenom = infosEleve[2];
                String adresse = infosEleve[3];
                String tel = infosEleve[4];

                LocalDate dateAuj = LocalDate.now();

                autoEcole.ajouterEleve(new Eleve(numSAAQ, nom, prenom, adresse, tel, dateAuj));
                System.out.println("Élève ajouté dans le système.");

                break;

            } catch (NumberFormatException e) {
                System.out.println("Erreur: le NumSAAQ doit être un nombre valide. Réessaie");
            } catch (Exception e) {
                System.out.println("Erreur: entrée invalide (" + e.getMessage() + "). Réessaie");
            }
        }
    }

    /**
     * Ajoute une nouvelle activité à l'auto-école en demandant les informations à l'utilisateur
     */
    public void ajoutActivite() {
        int bonneLongueur = 7;

        while (true) {
            System.out.println("Ajout d'une nouvelle activité");
            System.out.println("Donner les informations sous ce format:");
            System.out.println("Type,NumSAAQ,Date,Heure,Duree,Statut,Plaque");
            System.out.println("ex: LPA,123456789,12-04-2026,9:00,90,C,ABC123");
            System.out.print("Votre entrée: ");
            String ligne = scanner.nextLine();

            String[] infosActivite = ligne.split(",");

            if (infosActivite.length != bonneLongueur) {
                System.out.println("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
                continue;
            }

            try {
                for (int i=0; i<bonneLongueur; i++) {
                    infosActivite[i] = infosActivite[i].trim();
                }

                TypeActivite type = TypeActivite.valueOf(infosActivite[0]);
                long numSAAQ = Long.parseLong(infosActivite[1]);
                LocalDate date = LocalDate.parse(infosActivite[2], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime heure = LocalTime.parse(infosActivite[3], DateTimeFormatter.ofPattern("H:mm"));
                int duree = Integer.parseInt(infosActivite[4]);
                StatutActivite statut = StatutActivite.valueOf(infosActivite[5]);
                String plaque = infosActivite[6];

                PlageHoraire horaire = new PlageHoraire(date, heure, duree);
                Eleve eleve = autoEcole.rechercherEleve(numSAAQ);
                Voiture voiture = autoEcole.rechercherVoiture(plaque);

                if (eleve == null) {
                    System.out.println("Erreur: aucun élève trouvé avec le NumSAAQ " + numSAAQ + ". Réessaie");
                    continue;
                }

                if (statut.equals(StatutActivite.C)) {
                    eleve.setDateFin(LocalDate.now());
                    autoEcole.sauvegarderActivites();
                }

                ArrayList<PlageHoraire> horaires = new ArrayList<>();
                for (Activite activite : autoEcole.getActivites()) horaires.add(activite.getPlageHoraire());
                if (horaire.estEnConflitHoraire(horaires)) {
                    System.out.println("Conflit d'horaire à " + horaire + ". Réessaie");
                    continue;
                } else if (voiture != null && !voiture.estDisponible()) {
                    System.out.println("La voiture de l'école n'est pas disponible. Réessaie");
                    continue;
                }

                int prochainId = autoEcole.prochainIdActivite();
                autoEcole.ajouterActivite(new Activite(prochainId, horaire, eleve, plaque, type, statut));
                System.out.println("Activité ajoutée dans le système.");

                break;

            } catch (NumberFormatException e) {
                System.out.println("Erreur: le NumSAAQ ou la durée doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage() + ". Réessaie");
            }
        }
    }

    /**
     * Ajoute un nouveau paiement à l'auto-école en demandant les informations à l'utilisateur
     */
    public void ajoutPaiement() {
        while(true) {
            System.out.println("Recherche d'une activité par son ID");
            System.out.print("ID de l'activité: ");

            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                Activite activite = autoEcole.rechercherActivite(id);

                if (activite == null) {
                    System.out.println("Aucune activité attaché à cet identificateur.");
                    return;
                }

                creationPaiement(activite);
                System.out.println("Paiement ajouté dans le système pour l'activité ID: " + id);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }

    /**
     * Ajoute une nouvelle dépense pour une voiture de l'auto-école en demandant les informations à l'utilisateur
     */
    public void ajoutDepenseVoiture() {
        int bonneLongueur = 5;

        while (true) {
            System.out.println("Ajout d'une nouvelle dépense pour une voiture de l'école");
            System.out.println("Donner les informations sous ce format:");
            System.out.println("Plaque,Date,Categorie,Description,Montant");
            System.out.println("ex: ABC123,25-05-2026,R,Remplacement freins,350.00");
            System.out.print("Votre entrée: ");
            String ligne = scanner.nextLine();

            String[] infosDepense = ligne.split(",");

            if (infosDepense.length != bonneLongueur) {
                System.out.println("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
                continue;
            }

            try {
                for (int i=0; i<bonneLongueur; i++) {
                    infosDepense[i] = infosDepense[i].trim();
                }

                String plaque = infosDepense[0];
                LocalDate date = LocalDate.parse(infosDepense[1], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                TypeDepenseVoiture categorie = TypeDepenseVoiture.valueOf(infosDepense[2]);
                String description = infosDepense[3];
                double montant = Double.parseDouble(infosDepense[4]);

                if (montant < 0) {
                    System.out.println("Erreur: le montant ne peut pas être négatif. Réessaie");
                    continue;
                }

                int prochainId = autoEcole.prochainIdDepenseVoiture();
                autoEcole.ajouterDepenseVoiture(new DepenseVoiture(prochainId, plaque, date, categorie, description, montant));
                System.out.println("Dépense ajoutée dans le système");

                Voiture voiture = autoEcole.rechercherVoiture(plaque);
                if (voiture != null) {
                    voiture.updateDepenses(autoEcole.getDepensesVoiture());
                    System.out.println("Dépense ajoutée pour la voiture: " + plaque);
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Erreur: le montant doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage() + ". Réessaie");
            }
        }
    }

    /**
     * Ajoute une nouvelle dépense autre à l'auto-école en demandant les informations à l'utilisateur
     */
    public void ajoutAutreDepense() {
        int bonneLongueur = 4;

        while (true) {
            System.out.println("Ajout d'une nouvelle dépense pour une voiture de l'école");
            System.out.println("Donner les informations sous ce format:");
            System.out.println("Date,Categorie,Description,Montant");
            System.out.println("ex: 25-05-2026,P,Publicité école,350.00");
            System.out.print("Votre entrée: ");
            String ligne = scanner.nextLine();

            String[] infosDepense = ligne.split(",");

            if (infosDepense.length != bonneLongueur) {
                System.out.println("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
                continue;
            }

            try {
                for (int i=0; i<bonneLongueur; i++) {
                    infosDepense[i] = infosDepense[i].trim();
                }

                LocalDate date = LocalDate.parse(infosDepense[0], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                TypeAutreDepense categorie = TypeAutreDepense.valueOf(infosDepense[1]);
                String description = infosDepense[2];
                double montant = Double.parseDouble(infosDepense[3]);

                if (montant < 0) {
                    System.out.println("Erreur: le montant ne peut pas être négatif. Réessaie");
                    continue;
                }

                int prochainId = autoEcole.prochainIdAutreDepense();
                autoEcole.ajouterAutreDepense(new AutreDepense(prochainId, date, categorie, description, montant));
                System.out.println("Autre dépense ajouté dans le système.");

                break;

            } catch (NumberFormatException e) {
                System.out.println("Erreur: le montant doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage() + ". Réessaie");
            }
        }
    }

    /**
     * Ajoute une nouvelle voiture à l'auto-école en demandant les informations à l'utilisateur
     */
    public void ajoutVoiture() {
        int bonneLongueur = 7;

        while (true) {
            System.out.println("Ajout d'une nouvelle activité");
            System.out.println("Donner les informations sous ce format:");
            System.out.println("Marque,Plaque,Annee,Prix,KmAchat,Etat,Km");
            System.out.println("ex: Toyota,ABC123,2020,25000.00,15000,D,45230");
            System.out.print("Votre entrée: ");
            String ligne = scanner.nextLine();

            String[] infosVoiture = ligne.split(",");

            if (infosVoiture.length != bonneLongueur) {
                System.out.println("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
                continue;
            }

            try {
                for (int i=0; i<bonneLongueur; i++) {
                    infosVoiture[i] = infosVoiture[i].trim();
                }

                String marque = infosVoiture[0];
                String plaque = infosVoiture[1];
                int annee = Integer.parseInt(infosVoiture[2]);
                double prix = Double.parseDouble(infosVoiture[3]);
                int kmAchat = Integer.parseInt(infosVoiture[4]);
                StatutVoiture etat = StatutVoiture.valueOf(infosVoiture[5]);
                int km = Integer.parseInt(infosVoiture[6]);

                if (prix < 0 || kmAchat < 0 || km < 0) {
                    System.out.println("Erreur: le prix et le kilométrage ne peuvent pas être négatifs. Réessaie");
                    continue;
                }

                ArrayList<DepenseVoiture> depenses = autoEcole.trouverDepensesVoitureSelonPlaque(plaque);

                autoEcole.ajouterVoiture(new Voiture(plaque, marque, annee, prix, kmAchat, etat, km, depenses));
                System.out.println("Voiture ajoutée dans le système.");

                break;

            } catch (NumberFormatException e) {
                System.out.println("Erreur: l'année, le prix ou le kilométrage doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage() + ". Réessaie.");
            }
        }
    }


    // Recherche

    /**
     * Recherche un élève par son numéro SAAQ et affiche ses informations
     */
    public void rechercheEleve() {
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

                 System.out.println(" - " + eleve);
                 break;

             } catch (Exception e) {
                 System.out.println("Erreur: il faut un numéro (long). Réessaie");
             }
        }
    }

    /**
     * Recherche une activité par son ID et affiche ses informations
     */
    public void rechercheActivite() {
        while(true) {
            System.out.println("Recherche d'une activité par son ID");
            System.out.print("ID de l'activité: ");

            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                Activite activite = autoEcole.rechercherActivite(id);

                if (activite == null) {
                    System.out.println("Aucune activité attaché à cet identificateur.");
                    return;
                }

                System.out.println(" - " + activite);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }

    /**
     * Recherche un paiement par son ID et affiche ses informations
     */
    public void recherchePaiement() {
        while(true) {
            System.out.println("Recherche d'un paiement par son ID (format \"F-AAAA-XXXXX\")");
            System.out.print("ID: ");

            try {
                String id = scanner.nextLine();

                Paiement paiement = autoEcole.rechercherPaiement(id);

                if (paiement == null) {
                    System.out.println("Aucun paiement attaché à cet identifiant.");
                    return;
                }

                System.out.println(" - " + paiement);
                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un identifiant (String format \"F-AAAA-XXXXX\"). Réessaie");
            }
        }
    }

    /**
     * Recherche une voiture par sa plaque d'immatriculation et affiche ses informations
     */
    public void rechercheVoiture() {
        while(true) {
            System.out.println("Recherche d'une voiture par sa plaque d'immatriculation");
            System.out.print("Plaque d'immatriculation: ");

            try {
                String plaque = scanner.nextLine();

                Voiture voiture = autoEcole.rechercherVoiture(plaque);

                if (voiture == null) {
                    System.out.println("Voiture de l'extérieur.");
                    return;
                }

                System.out.println(" - " + voiture);
                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }


    // Supprimer

    /**
     * Supprime un élève par son numéro SAAQ
     */
    public void supprimerEleve() {
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

                autoEcole.supprimerEleve(numSAAQ);
                System.out.println(" - " + eleve);
                System.out.println("L'élève à été supprimé");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Supprime une activité par son ID
     */
    public void supprimerActivite() {
        while(true) {
            System.out.println("Recherche d'une activité par son ID");
            System.out.print("ID de l'activité: ");

            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                Activite activite = autoEcole.rechercherActivite(id);

                if (activite == null) {
                    System.out.println("Aucune activité attaché à cet identificateur.");
                    return;
                }

                autoEcole.annulerActivite(id);
                System.out.println(" - " + activite);
                System.out.println("L'activité à été annulé");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }


    // Afficher

    /**
     * Affiche la liste de tous les élèves de l'auto-école
     */
    public void afficherEleves() {
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
    public void afficherActivites() {
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
    public void afficherActivitesEleve() {
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
    public void afficherDepensesVoitures() {
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
    public void afficherDepensesVoiture() {
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
    public void afficherVoitures() {
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
    public void afficherPaiementsEleve() {
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
    public void afficherPaiementsImpayes() {
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


    // Changer état

    /**
     * Change l'état d'une activité en recherchant l'activité par son ID
     */
    public void completerActivite() {
        while(true) {
            System.out.println("Recherche d'une activité par son ID");
            System.out.print("ID de l'activité: ");

            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                Activite activite = autoEcole.rechercherActivite(id);

                if (activite == null) {
                    System.out.println("Aucune activité attaché à cet identificateur.");
                    return;
                }

                autoEcole.completerActivite(id);
                System.out.println(" - " + activite);
                System.out.println("L'activité à été marqué comme complété");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }

    /**
     * Change l'état d'une voiture en recherchant la voiture par sa plaque d'immatriculation
     */
    public void changerEtatVoiture() {
        while(true) {
            System.out.println("Recherche d'une voiture par sa plaque d'immatriculation");
            System.out.print("Plaque d'immatriculation: ");

            try {
                String plaque = scanner.nextLine();

                Voiture voiture = autoEcole.rechercherVoiture(plaque);

                if (voiture == null) {
                    System.out.println("Voiture de l'extérieur (non modifiable).");
                    return;
                }

                System.out.println("Voiture actuelle: ");
                System.out.println(" - " + voiture);
                subChangerEtatVoiture(voiture);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Change l'état d'une voiture en demandant à l'utilisateur le nouvel état
     */
    public void subChangerEtatVoiture(Voiture voiture) {
        while(true) {
            System.out.println("Changement de l'état, choix disponibles  R (réparation), V (vendu), D (disponible).");
            System.out.print("Votre choix: ");

            try {
                String etat = scanner.nextLine();

                StatutVoiture statutVoiture = StatutVoiture.valueOf(etat);

                voiture.setEtat(statutVoiture);
                autoEcole.sauvegarderVoitures();
                System.out.println("Changement effectué, la voiture est maintenant dans l'état " + statutVoiture.getLibelle());

                break;

            } catch (Exception e) {
                System.out.println("Erreur: les options sont R, V et D. Réessaie");
            }
        }
    }

    /**
     * Change l'état d'un paiement en recherchant le paiement par son identifiant
     */
    public void changerEtatPaiement() {
        while(true) {
            System.out.println("Recherche d'un paiement par son identifiant (format : F-AAAA-XXXXX)");
            System.out.print("Identifiant de paiement: ");

            try {
                String id = scanner.nextLine();

                Paiement paiement = autoEcole.rechercherPaiement(id);

                if (paiement == null) {
                    System.out.println("Aucun paiement attaché à cet identificateur.");
                    return;
                }

                System.out.println("Paiement actuel: ");
                System.out.println(" - " + paiement);
                subChangerEtatPaiement(paiement);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Change l'état d'un paiement en demandant à l'utilisateur le nouvel état
     */
    public void subChangerEtatPaiement(Paiement paiement) {
        while(true) {
            System.out.println("Changement de l'état, choix disponibles  P (payé), I (impayé), PP (partiellement payé).");
            System.out.print("Votre choix: ");

            try {
                String etat = scanner.nextLine();

                StatutPaiement statutPaiement = StatutPaiement.valueOf(etat);

                if (statutPaiement.equals(StatutPaiement.P)) {
                    paiement.setMontantRestant(0);
                } else if (statutPaiement.equals(StatutPaiement.PP)) {
                    montantPaye(paiement);
                }

                paiement.setDate(LocalDate.now());
                paiement.setEtat(statutPaiement);
                autoEcole.sauvegarderPaiements();
                System.out.println("Changement effectué, le paiement est maintenant dans l'état " + statutPaiement.getLibelle());

                break;

            } catch (Exception e) {
                System.out.println("Erreur: les options sont P, I et PP. Réessaie");
            }
        }
    }

    /**
     * Change le montant restant d'un paiement en demandant à l'utilisateur le nouveau montant
     */
    public void montantPaye(Paiement paiement) {
        while(true) {
            System.out.println("Paiement à été partiellement payé.");
            System.out.print("Montant restant: ");

            try {
                double montantRestant = scanner.nextDouble();
                scanner.nextLine();

                if (montantRestant > paiement.getMontantRestant()) {
                    System.out.println("Erreur: le montant restant doit être plus petit que ce qu'il y avait avant. Réessaie");
                    continue;
                }

                paiement.setMontantRestant(montantRestant);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: le montant restant doit être un nombre à virgule (double).");
            }
        }
    }


    // Création

    /**
     * Génère une facture pour un paiement en recherchant le paiement par son identifiant
     */
    public void genererFacture() {
        while(true) {
            System.out.println("Générer une facture pour un paiement.");
            System.out.println("Recherche d'un paiement par son identifiant (format : F-AAAA-XXXXX)");
            System.out.print("Identifiant de paiement: ");

            try {
                String id = scanner.nextLine();

                Paiement paiement = autoEcole.rechercherPaiement(id);

                if (paiement == null) {
                    System.out.println("Aucun paiement attaché à cet identificateur.");
                    return;
                }

                creationFacture(paiement);
                System.out.println("Facture créé pour le paiement " + id);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Crée une facture pour un paiement et l'enregistre dans un fichier texte
     */
    public void creationFacture(Paiement paiement) {
        File dir = new File(CSV.getDir("facturation"));
        if (!dir.exists()) dir.mkdirs();
        String filePath = dir + "//" + paiement.getId() + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".txt";

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            Eleve eleve = paiement.getEleve();
            Activite activite = paiement.getActivite();

            pw.println("================================================");
            pw.println("          AUTO-ÉCOLE - FACTURE");
            pw.println("================================================");
            pw.println("Numéro de facture: " + paiement.getId());
            pw.println("Date de création: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("ÉLÈVE");
            pw.println("------------------------------------------------");
            pw.println("NumSAAQ: " + eleve.getNumSAAQ());
            pw.println("Nom: " + eleve.getNom() + ", " + eleve.getPrenom());
            pw.println("Adresse: " + eleve.getAdresse());
            pw.println("Téléphone: " + eleve.getTelephone());
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("ACTIVITÉ");
            pw.println("------------------------------------------------");
            pw.println("ID: " + activite.getId());
            pw.println("Type: " + activite.getType() + " - " + activite.getType().getLibelle());
            pw.println("Date: " + activite.getPlageHoraire().getDate());
            pw.println("Heure: " + activite.getPlageHoraire().getHeureDebut());
            pw.println("Durée: " + activite.getPlageHoraire().getDuree() + " minutes");
            pw.println("Véhicule: " + activite.getPlaque());
            pw.println("Statut: " + activite.getStatut().getLibelle());
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("PAIEMENT");
            pw.println("------------------------------------------------");
            pw.println("Montant total: " + paiement.getMontant());
            pw.println("Montant restant: " + paiement.getMontantRestant());
            pw.println("Méthode: " + paiement.getMethodePaiement().getLibelle());
            pw.println("Statut: " + paiement.getStatutPaiement().getLibelle());
            pw.println("Motif: " + paiement.getTypeActivite().getLibelle());
            pw.println();
            pw.println("================================================");
            pw.println("Bonne journée!");
            pw.println("================================================");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Autres méthodes

    /**
     * Affiche les règles pour les différents types de valeurs
     */
    public void help() {
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

    /**
     * Sauvegarde toutes les données de l'auto-école dans les fichiers CSV
     */
    public void sauvegarde() {
        autoEcole.sauvegarderEleves();
        autoEcole.sauvegarderActivites();
        autoEcole.sauvegarderPaiements();
        autoEcole.sauvegarderDepensesVoiture();
        autoEcole.sauvegarderAutresDepenses();
        autoEcole.sauvegarderVoitures();
        System.out.println("Sauvegarde terminée");
    }

    /**
     * Pour améliorer la lisibilité de la méthode ajoutPaiement()
     * @param activite
     */
    private void creationPaiement(Activite activite) {
        int bonneLongueur = 3;

        while (true) {
            System.out.println("Ajout d'une paiement pour l'activité avec l'ID " + activite.getId());
            System.out.println(" - " + activite);
            System.out.println("Donner les informations sous ce format:");
            System.out.println("Date,StatutPaiement,MethodePaiement");
            System.out.println("ex: 12-04-2026,P,E");
            System.out.print("Votre entrée: ");
            String ligne = scanner.nextLine();

            String[] infosPaiement = ligne.split(",");

            if (infosPaiement.length != bonneLongueur) {
                System.out.println("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
                continue;
            }

            try {
                for (int i=0; i<bonneLongueur; i++) {
                    infosPaiement[i] = infosPaiement[i].trim();
                }

                LocalDate date = LocalDate.parse(infosPaiement[0], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                StatutPaiement statut = StatutPaiement.valueOf(infosPaiement[1]);
                MethodePaiement methode = MethodePaiement.valueOf(infosPaiement[2]);
                Eleve eleve = activite.getEleve();

                int prochainId = autoEcole.prochainNumeroPaiement();
                autoEcole.ajouterPaiement(new Paiement(prochainId, date, statut, activite, methode, eleve));

                break;

            } catch (NumberFormatException e) {
                System.out.println("Erreur: le NumSAAQ ou la durée doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage() + ". Réessaie");
            }
        }
    }
}
