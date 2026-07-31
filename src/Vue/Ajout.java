package MenuTextuel;

import Modele.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Ajout {
    /**
     * Ajoute un nouvel élève à l'auto-école en demandant les informations à l'utilisateur
     */
    public static void ajoutEleve(Scanner scanner, AutoEcole autoEcole) {
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
    public static void ajoutActivite(Scanner scanner, AutoEcole autoEcole) {
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
    public static void ajoutPaiement(Scanner scanner, AutoEcole autoEcole) {
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

                creationPaiement(scanner, autoEcole, activite);
                System.out.println("Paiement ajouté dans le système pour l'activité ID: " + id);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (int). Réessaie");
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

    /**
     * Ajoute une nouvelle dépense pour une voiture de l'auto-école en demandant les informations à l'utilisateur
     */
    public static void ajoutDepenseVoiture(Scanner scanner, AutoEcole autoEcole) {
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
    public static void ajoutAutreDepense(Scanner scanner, AutoEcole autoEcole) {
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
    public static void ajoutVoiture(Scanner scanner, AutoEcole autoEcole) {
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
}
