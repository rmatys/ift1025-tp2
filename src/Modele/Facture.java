package Modele;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsable de la génération des factures, enregistrées dans des fichiers texte.
 */
public class Facture {
    private static final Logger logger = Logger.getLogger(Facture.class.getName());

    /**
     * Crée une facture pour un paiement et l'enregistre dans un fichier texte
     */
    public static boolean genererFacture(Paiement paiement) {
        File dir = new File(CSV.getDir("facturation"));
        if (!dir.exists()) {
            boolean succes = dir.mkdirs();
            if (!succes) {
                logger.log(Level.WARNING, "Impossible de créer un folder dans: " + dir);
                return false;
            }
        }

        File f = new File(dir, paiement.getId() + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
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

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Une erreur est survenue", e);
            return false;
        }
        return true;
    }
}
