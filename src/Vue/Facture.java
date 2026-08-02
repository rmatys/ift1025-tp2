package Vue;

import Modele.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Facture {
    private static final Logger logger = Logger.getLogger(Facture.class.getName());

    /**
     * Génère une facture pour un paiement en recherchant le paiement par son identifiant
     */
    public static void genererFacture(Scanner scanner, AutoEcole autoEcole) {
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
    public static void creationFacture(Paiement paiement) {
        File dir = new File(CSV.getDir("facturation"));
        if (!dir.exists()) {
            boolean succes = dir.mkdirs();
            if (!succes) {
                System.err.println("Impossible de créer un folder dans: " + dir);
            }
        }

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
            logger.log(Level.SEVERE, "Une erreur est survenue", e);
        }
    }
}
