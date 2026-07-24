import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant les rapports générés par l'auto-école.
 */
public class Rapports {

    /**
     * Génère un rapport des élèves
     * @param eleves la liste des élèves
     */
    public static void genererRapportEleves(ArrayList<Eleve> eleves) {
        int nbrElevesActif = 0;
        int nbrElevesGradue = 0;
        for (Eleve eleve : eleves) {
            if (eleve.getDateFin() == null)  {
                nbrElevesActif++;
            } else {
                nbrElevesGradue++;
            }
        }

        File dir = new File(CSV.getDir("rapport"));
        if (!dir.exists()) dir.mkdirs();
        File f = new File(dir, "rapportEleves" + CSV.YEAR + ".txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.println("================================================");
            pw.println("       AUTO-ÉCOLE - RAPPORT DES ÉLÈVES");
            pw.println("       Année " + CSV.YEAR);
            pw.println("================================================");
            pw.println("Date de génération : " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("ÉLÈVES ACTIFS (en cours de formation)");
            pw.println("------------------------------------------------");
            pw.println("NumSAAQ      Nom            Prénom       Inscription");
            for (Eleve e : eleves) {
                if (e.getDateFin() == null) {
                    int lenAjout1 = 13 - (e.getNumSAAQ() + "").length();
                    int lenAjout2 = 15 - e.getNom().length();
                    int lenAjout3 = 13 - e.getPrenom().length();
                    pw.println(e.getNumSAAQ() +
                            " ".repeat(lenAjout1) + e.getNom() +
                            " ".repeat(lenAjout2) + e.getPrenom() +
                            " ".repeat(lenAjout3) + e.getDateDebut());
                }
            }
            pw.println();
            pw.println("Total élèves actifs : " + nbrElevesActif);
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("ÉLÈVES AYANT COMPLÉTÉ LEUR FORMATION");
            pw.println("------------------------------------------------");
            pw.println("NumSAAQ      Nom            Prénom       Inscription    Fin de service");
            for (Eleve e : eleves) {
                if (e.getDateFin() != null) {
                    int lenAjout1 = 13 - (e.getNumSAAQ() + "").length();
                    int lenAjout2 = 15 - e.getNom().length();
                    int lenAjout3 = 13 - e.getPrenom().length();
                    int lenAjout4 = 15 - (e.getDateDebut() + "").length();
                    pw.println(e.getNumSAAQ() +
                            " ".repeat(lenAjout1) + e.getNom() +
                            " ".repeat(lenAjout2) + e.getPrenom() +
                            " ".repeat(lenAjout3) + e.getDateDebut() +
                            " ".repeat(lenAjout4) + e.getDateFin());
                }
            }
            pw.println();
            pw.println("Total élèves complétés : " + nbrElevesGradue);
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("RÉSUMÉ");
            pw.println("------------------------------------------------");
            pw.println("Nombre total d'élèves (année " + CSV.YEAR + ") : " + eleves.size());
            pw.println("Élèves actifs                      : " + nbrElevesActif);
            pw.println("Élèves ayant terminé               : " + nbrElevesGradue);
            pw.println("================================================");

            System.out.println("Rapport sur les élèves sauvegarder dans .../rapport/");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Génère un rapport des revenus
     * @param activites la liste des activités
     * @param paiements la liste des paiements
     */
    public static void genererRapportRevenus(ArrayList<Activite> activites, ArrayList<Paiement> paiements) {
        File dir = new File(CSV.getDir("rapport"));
        if (!dir.exists()) dir.mkdirs();
        File f = new File(dir, "rapportRevenus" + CSV.YEAR + ".txt");

        int nombreLecons = 0;
        int nombreExamens = 0;
        double revenuLecons = 0;
        double revenuExamens = 0;
        double revenuBrut = 0;
        double revenuReel = 0;
        double soldeFutur = 0;

        for (Paiement p : paiements) {
            if (p.getStatutPaiement() == StatutPaiement.P) {
                revenuBrut += p.getMontant();
                revenuReel += p.getMontant();
            } else if (p.getStatutPaiement() == StatutPaiement.PP) {
                revenuBrut += p.getMontant();
                revenuReel += (p.getMontant()- p.getMontantRestant());
                soldeFutur += p.getMontantRestant();
            } else {
                revenuBrut += p.getMontant();
                soldeFutur += p.getMontantRestant();
            }
        }
        for (Activite activite : activites) {
            TypeActivite typeActivite = activite.getType();
            if (typeActivite == null) continue;
            switch (typeActivite) {
                case LPA:
                case LPZ:
                case LPS:
                case LT:
                    nombreLecons++;
                    revenuLecons = activite.getMontant();
                    break;
                case ET:
                case EP:
                case EPL:
                    nombreExamens++;
                    revenuExamens = activite.getMontant();
                    break;
                default:
                    break;
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.println("================================================");
            pw.println("       AUTO-ÉCOLE - RAPPORT DE REVENUS");
            pw.println("       Année " + CSV.YEAR);
            pw.println("================================================");
            pw.println("Date de génération : " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("DÉTAILS DES PAIEMENTS");
            pw.println("------------------------------------------------");
            pw.println("Numéro de paiement      Date           Numéro SAAQ       Montant     MontantRestant     Méthode de paiement      Statut de paiement");
            for (Paiement p : paiements) {
                int lenAjout1 = 24 - p.getId().length();
                int lenAjout2 = 15 - (p.getDate() + "").length();
                int lenAjout3 = 18 - (p.getEleve().getNumSAAQ() + "").length();
                int lenAjout4 = 12 - (String.format("%.2f", p.getMontant()) + " $").length();
                int lenAjout5 = 19 - (String.format("%.2f", p.getMontantRestant()) + " $").length();
                int lenAjout6 = 25 - p.getMethodePaiement().getLibelle().length();
                pw.println(p.getId() +
                        " ".repeat(lenAjout1) + p.getDate() +
                        " ".repeat(lenAjout2) + p.getEleve().getNumSAAQ() +
                        " ".repeat(lenAjout3) + String.format("%.2f", p.getMontant()) + " $" +
                        " ".repeat(lenAjout4) + String.format("%.2f", p.getMontantRestant()) + " $" +
                        " ".repeat(lenAjout5) + p.getMethodePaiement().getLibelle() +
                        " ".repeat(lenAjout6) + p.getStatutPaiement().getLibelle()
                );
            }
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("RÉSUMÉ DES LEÇONS");
            pw.println("------------------------------------------------");
            pw.println("Nombre total de leçons        : " + nombreLecons);
            pw.println("Revenu total des leçons       : " + String.format("%.2f", revenuLecons) + " $");
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("RÉSUMÉ DES EXAMENS");
            pw.println("------------------------------------------------");
            pw.println("Nombre total d'examens        : " + nombreExamens);
            pw.println("Revenu total des examens      : " + String.format("%.2f", revenuExamens) + " $");
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("TOTAL");
            pw.println("------------------------------------------------");
            pw.println("Revenu brut facturé            : " + String.format("%.2f", revenuBrut) + " $");
            pw.println("Revenu réellement encaissé     : " + String.format("%.2f", revenuReel) + " $");
            pw.println("Solde à recevoir               : " + String.format("%.2f", soldeFutur) + " $");
            pw.println("================================================");

            System.out.println("Rapport de revenus sauvegarder dans .../rapport/");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Génère un rapport des dépenses voiture
     * @param depensesVoiture la liste des dépenses voiture
     */
    public static void genererRapportDepensesVoiture(ArrayList<DepenseVoiture> depensesVoiture) {
        File dir = new File(CSV.getDir("rapport"));
        if (!dir.exists()) dir.mkdirs();
        File f = new File(dir, "rapportDepensesVoiture" + CSV.YEAR + ".txt");

        double totalR = 0;
        double totalE = 0;
        double totalC = 0;
        double total = 0;

        for (DepenseVoiture d : depensesVoiture) {
            TypeDepenseVoiture categorie = d.getCategorie();
            if (categorie == null) continue;

            total += d.getMontant();
            switch (categorie) {
                case R:
                    totalR += d.getMontant();
                    break;
                case E:
                    totalE += d.getMontant();
                    break;
                case C:
                    totalC += d.getMontant();
                    break;
                default:
                    break;
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.println("================================================");
            pw.println("       AUTO-ÉCOLE - RAPPORT DES DÉPENSES VÉHICULE");
            pw.println("       Année " + CSV.YEAR);
            pw.println("================================================");
            pw.println("Date de génération : " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("DÉTAIL DES DÉPENSES");
            pw.println("------------------------------------------------");
            pw.println("Plaque      Date         Catégorie      Description                Montant");
            for (DepenseVoiture d : depensesVoiture) {
                int lenAjout1 = 12 - d.getPlaque().length();
                int lenAjout2 = 13 - (d.getDate() + "").length();
                int lenAjout3 = 15 - d.getCategorie().getLibelle().length();
                int lenAjout4 = 27 - d.getDescription().length();
                pw.println(d.getPlaque() +
                        " ".repeat(lenAjout1) + d.getDate() +
                        " ".repeat(lenAjout2) + d.getCategorie().getLibelle() +
                        " ".repeat(lenAjout3) + d.getDescription() +
                        " ".repeat(lenAjout4) + String.format("%.2f", d.getMontant()) + " $"
                );
            }
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("RÉSUMÉ PAR CATÉGORIE");
            pw.println("------------------------------------------------");
            pw.println("Réparations (R)      : " + String.format("%.2f", totalR) + " $");
            pw.println("Entretien (E)        : " + String.format("%.2f", totalE) + " $");
            pw.println("Carburant (C)        : " + String.format("%.2f", totalC) + " $");
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("TOTAL");
            pw.println("------------------------------------------------");
            pw.println("Total des dépenses véhicule (" + CSV.YEAR + ") : " + String.format("%.2f", total) + " $");
            pw.println("================================================");

            System.out.println("Rapport des dépenses de la voiture sauvegarder dans .../rapport/");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Génère un rapport des autres dépenses
     * @param autresDepenses la liste des autres dépenses
     */
    public static void genererRapportAutresDepenses(ArrayList<AutreDepense> autresDepenses) {
        File dir = new File(CSV.getDir("rapport"));
        if (!dir.exists()) dir.mkdirs();
        File f = new File(dir, "rapportAutresDepenses" + CSV.YEAR + ".txt");

        double total = 0;
        Map<TypeAutreDepense, Double> totals = new HashMap<>();
        totals.put(TypeAutreDepense.P, 0.0);
        totals.put(TypeAutreDepense.B, 0.0);
        totals.put(TypeAutreDepense.T, 0.0);
        totals.put(TypeAutreDepense.I, 0.0);
        totals.put(TypeAutreDepense.A, 0.0);

        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {

            pw.println("================================================");
            pw.println("       AUTO-ÉCOLE - RAPPORT DES DÉPENSES VÉHICULE");
            pw.println("       Année " + CSV.YEAR);
            pw.println("================================================");
            pw.println("Date de génération : " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("DÉTAIL DES DÉPENSES");
            pw.println("------------------------------------------------");
            pw.println("Date         Catégorie      Description                Montant");
            for (AutreDepense d : autresDepenses) {
                int lenAjout1 = 13 - (d.getDate() + "").length();
                int lenAjout2 = 15 - d.getCategorie().getLibelle().length();
                int lenAjout3 = 27 - d.getDescription().length();
                pw.println(d.getDate() +
                        " ".repeat(lenAjout1) + d.getCategorie().getLibelle() +
                        " ".repeat(lenAjout2) + d.getDescription() +
                        " ".repeat(lenAjout3) + String.format("%.2f", d.getMontant()) + " $"
                );

                TypeAutreDepense categorie = d.getCategorie();
                if (categorie != null && totals.containsKey(categorie)) {
                    totals.put(categorie, totals.get(categorie) + d.getMontant());
                } else {
                    totals.put(TypeAutreDepense.A, totals.get(TypeAutreDepense.A) + d.getMontant());
                }
                total += d.getMontant();
            }
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("RÉSUMÉ PAR CATÉGORIE");
            pw.println("------------------------------------------------");
            pw.println("Publicité (P)   : " + String.format("%.2f", totals.get(TypeAutreDepense.P)) + " $");
            pw.println("Bureau (B)      : " + String.format("%.2f", totals.get(TypeAutreDepense.B)) + " $");
            pw.println("Téléphone (T)   : " + String.format("%.2f", totals.get(TypeAutreDepense.T)) + " $");
            pw.println("Internet (I)    : " + String.format("%.2f", totals.get(TypeAutreDepense.I)) + " $");
            pw.println("Autre (A)       : " + String.format("%.2f", totals.get(TypeAutreDepense.A)) + " $");
            pw.println();
            pw.println("------------------------------------------------");
            pw.println("TOTAL");
            pw.println("------------------------------------------------");
            pw.println("Total des autres dépenses (" + CSV.YEAR + ") : " + String.format("%.2f", total) + " $");
            pw.println("================================================");

            System.out.println("Rapport des autres dépenses sauvegarder dans .../rapport/");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}