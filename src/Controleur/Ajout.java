package Controleur;

import Modele.*;
import Vue.Saisie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Ajout {
    /**
     * Ajoute un nouvel élève à l'auto-école en demandant les informations à l'utilisateur
     */
    public static void ajoutEleve(Scanner scanner, AutoEcole autoEcole) {
        int bonneLongueur = 5;

        while (true) {
            Saisie.afficherMessage("Ajout d'un nouvel élève");
            Saisie.afficherMessage("Donner les informations sous ce format:");
            Saisie.afficherMessage("NumSAAQ,Nom,Prenom,Adresse,Telephone");
            Saisie.afficherMessage("ex: 123456789,Dupont,Marie,adr1,5145551234");
            String ligne = Saisie.demanderLigne(scanner, "Votre entrée: ");

            String[] infosEleve = ligne.split(",");

            if (infosEleve.length != bonneLongueur) {
                Saisie.afficherMessage("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
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
                Saisie.afficherMessage("Élève ajouté dans le système.");

                break;

            } catch (NumberFormatException e) {
                Saisie.afficherMessage("Erreur: le NumSAAQ doit être un nombre valide. Réessaie");
            } catch (Exception e) {
                Saisie.afficherMessage("Erreur: entrée invalide (" + e.getMessage() + "). Réessaie");
            }
        }
    }

    /**
     * Ajoute une nouvelle activité à l'auto-école en demandant les informations à l'utilisateur
     */
    public static void ajoutActivite(Scanner scanner, AutoEcole autoEcole) {
        int bonneLongueur = 7;

        while (true) {
            Saisie.afficherMessage("Ajout d'une nouvelle activité");
            Saisie.afficherMessage("Donner les informations sous ce format:");
            Saisie.afficherMessage("Type,NumSAAQ,Date,Heure,Duree,Statut,Plaque");
            Saisie.afficherMessage("ex: LPA,123456789,12-04-2026,9:00,90,C,ABC123");
            String ligne = Saisie.demanderLigne(scanner, "Votre entrée: ");

            String[] infosActivite = ligne.split(",");

            if (infosActivite.length != bonneLongueur) {
                Saisie.afficherMessage("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
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

                autoEcole.creerActivite(horaire, numSAAQ, plaque, type, statut);
                Saisie.afficherMessage("Activité ajoutée dans le système.");

                break;

            } catch (NumberFormatException e) {
                Saisie.afficherMessage("Erreur: le NumSAAQ ou la durée doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                Saisie.afficherMessage("Erreur IllegalArgumentException: " + e.getMessage() + ". Réessaie");
            } catch (OperationInvalideException e) {
                Saisie.afficherMessage("Erreur OperationInvalideException: " + e.getMessage() + ". Réessaie");
            }
        }
    }

    /**
     * Ajoute un nouveau paiement à l'auto-école en demandant les informations à l'utilisateur
     */
    public static void ajoutPaiement(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            Saisie.afficherMessage("Recherche d'une activité par son ID");

            try {
                int id = Saisie.demanderEntier(scanner, "ID de l'activité: ");

                Activite activite = autoEcole.rechercherActivite(id);

                if (activite == null) {
                    Saisie.afficherMessage("Aucune activité attaché à cet identificateur.");
                    return;
                }

                creationPaiement(scanner, autoEcole, activite);
                Saisie.afficherMessage("Paiement ajouté dans le système pour l'activité ID: " + id);

                break;

            } catch (Exception e) {
                Saisie.afficherMessage("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }

    /**
     * Pour améliorer la lisibilité de la méthode ajoutPaiement()
     * @param activite Activtie
     */
    private static void creationPaiement(Scanner scanner, AutoEcole autoEcole, Activite activite) {
        int bonneLongueur = 3;

        while (true) {
            Saisie.afficherMessage("Ajout d'une paiement pour l'activité avec l'ID " + activite.getId());
            Saisie.afficherMessage(" - " + activite);
            Saisie.afficherMessage("Donner les informations sous ce format:");
            Saisie.afficherMessage("Date,StatutPaiement,MethodePaiement");
            Saisie.afficherMessage("ex: 12-04-2026,P,E");
            String ligne = Saisie.demanderLigne(scanner, "Votre entrée: ");

            String[] infosPaiement = ligne.split(",");

            if (infosPaiement.length != bonneLongueur) {
                Saisie.afficherMessage("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
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
                Saisie.afficherMessage("Erreur: le NumSAAQ ou la durée doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                Saisie.afficherMessage("Erreur: " + e.getMessage() + ". Réessaie");
            }
        }
    }

    /**
     * Ajoute une nouvelle dépense pour une voiture de l'auto-école en demandant les informations à l'utilisateur
     */
    public static void ajoutDepenseVoiture(Scanner scanner, AutoEcole autoEcole) {
        int bonneLongueur = 5;

        while (true) {
            Saisie.afficherMessage("Ajout d'une nouvelle dépense pour une voiture de l'école");
            Saisie.afficherMessage("Donner les informations sous ce format:");
            Saisie.afficherMessage("Plaque,Date,Categorie,Description,Montant");
            Saisie.afficherMessage("ex: ABC123,25-05-2026,R,Remplacement freins,350.00");
            String ligne = Saisie.demanderLigne(scanner, "Votre entrée: ");

            String[] infosDepense = ligne.split(",");

            if (infosDepense.length != bonneLongueur) {
                Saisie.afficherMessage("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
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

                autoEcole.creerDepenseVoiture(plaque, date, categorie, description, montant);
                Saisie.afficherMessage("Dépense ajoutée dans le système");

                if (autoEcole.rechercherVoiture(plaque) != null) {
                    Saisie.afficherMessage("Dépense ajoutée pour la voiture: " + plaque);
                }

                break;

            } catch (NumberFormatException e) {
                Saisie.afficherMessage("Erreur: le montant doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                Saisie.afficherMessage("Erreur IllegalArgumentException: " + e.getMessage() + ". Réessaie");
            } catch (OperationInvalideException e) {
                Saisie.afficherMessage("Erreur OperationInvalideException: " + e.getMessage() + ". Réessaie");
            }
        }
    }

    /**
     * Ajoute une nouvelle dépense autre à l'auto-école en demandant les informations à l'utilisateur
     */
    public static void ajoutAutreDepense(Scanner scanner, AutoEcole autoEcole) {
        int bonneLongueur = 4;

        while (true) {
            Saisie.afficherMessage("Ajout d'une nouvelle dépense pour une voiture de l'école");
            Saisie.afficherMessage("Donner les informations sous ce format:");
            Saisie.afficherMessage("Date,Categorie,Description,Montant");
            Saisie.afficherMessage("ex: 25-05-2026,P,Publicité école,350.00");
            String ligne = Saisie.demanderLigne(scanner, "Votre entrée: ");

            String[] infosDepense = ligne.split(",");

            if (infosDepense.length != bonneLongueur) {
                Saisie.afficherMessage("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
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

                autoEcole.creerAutreDepense(date, categorie, description, montant);
                Saisie.afficherMessage("Autre dépense ajouté dans le système.");

                break;

            } catch (NumberFormatException e) {
                Saisie.afficherMessage("Erreur: le montant doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                Saisie.afficherMessage("Erreur IllegalArgumentException: " + e.getMessage() + ". Réessaie");
            } catch (OperationInvalideException e) {
                Saisie.afficherMessage("Erreur OperationInvalideException: " + e.getMessage() + ". Réessaie");
            }
        }
    }

    /**
     * Ajoute une nouvelle voiture à l'auto-école en demandant les informations à l'utilisateur
     */
    public static void ajoutVoiture(Scanner scanner, AutoEcole autoEcole) {
        int bonneLongueur = 7;

        while (true) {
            Saisie.afficherMessage("Ajout d'une nouvelle activité");
            Saisie.afficherMessage("Donner les informations sous ce format:");
            Saisie.afficherMessage("Marque,Plaque,Annee,Prix,KmAchat,Etat,Km");
            Saisie.afficherMessage("ex: Toyota,ABC123,2020,25000.00,15000,D,45230");
            String ligne = Saisie.demanderLigne(scanner, "Votre entrée: ");

            String[] infosVoiture = ligne.split(",");

            if (infosVoiture.length != bonneLongueur) {
                Saisie.afficherMessage("Erreur: il faut exactement " + bonneLongueur + " informations séparées par des virgules. Réessaie");
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

                autoEcole.creerVoiture(marque, plaque, annee, prix, kmAchat, etat, km);
                Saisie.afficherMessage("Voiture ajoutée dans le système.");

                break;

            } catch (NumberFormatException e) {
                Saisie.afficherMessage("Erreur: l'année, le prix ou le kilométrage doit être un nombre valide. Réessaie");
            } catch (IllegalArgumentException e) {
                Saisie.afficherMessage("Erreur IllegalArgumentException: " + e.getMessage() + ". Réessaie.");
            } catch (OperationInvalideException e) {
                Saisie.afficherMessage("Erreur OperationInvalideException: " + e.getMessage() + ". Réessaie.");
            }
        }
    }
}
