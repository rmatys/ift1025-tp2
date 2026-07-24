import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe utilitaire pour la lecture et l'écriture de fichiers CSV
 */
public class CSV {
    public static final int YEAR = LocalDate.now().getYear();

    /**
     * Lit les élèves à partir d'un fichier CSV
     * @return la liste des élèves
     */
    public static ArrayList<Eleve> lireEleves() {
        int bonneLongueur = 7;
        ArrayList<Eleve> eleves = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getDir("data") + "eleves" + YEAR + ".csv"))) {
            String ligne = br.readLine();

            while ((ligne = br.readLine()) != null) {
                String[] infosEleve = ligne.split(",", -1);
                for (int i=0; i<bonneLongueur; i++) {
                    infosEleve[i] = infosEleve[i].trim();
                }

                long numSaaq = Long.parseLong(infosEleve[0]);
                String nom = infosEleve[1];
                String prenom = infosEleve[2];
                String adresse = infosEleve[3];
                String tel = infosEleve[4];
                LocalDate dateDebut = LocalDate.parse(infosEleve[5], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalDate dateFin = null;
                if (!infosEleve[6].isBlank()) dateFin = LocalDate.parse(infosEleve[6], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                eleves.add(new Eleve(numSaaq, nom, prenom, adresse, tel, dateDebut, dateFin));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return eleves;
    }

    /**
     * Écrit les élèves dans un fichier CSV
     * @param eleves la liste des élèves
     */
    public static void ecrireEleves(ArrayList<Eleve> eleves) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(getDir("data") + "eleves" + YEAR + ".csv"))) {

            pw.println("NumSAAQ,Nom,Prenom,Adresse,Telephone,Date,DateFin");

            for (Eleve e : eleves) {
                pw.println(e.getNumSAAQ() + "," +
                           e.getNom() + "," +
                           e.getPrenom() + "," +
                           e.getAdresse() + "," +
                           e.getTelephone() + "," +
                           e.getDateDebut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "," +
                           (e.getDateFin() == null ? "" : e.getDateFin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lit les activités à partir d'un fichier CSV
     * @param eleves la liste des élèves
     * @return la liste des activités
     */
    public static ArrayList<Activite> lireActivites(ArrayList<Eleve> eleves) {
        int bonneLongueur = 9;
        ArrayList<Activite> activites = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getDir("data") + "activites" + YEAR + ".csv"))) {
            String ligne = br.readLine();

            while ((ligne = br.readLine()) != null) {
                String[] t = ligne.split(",", -1);
                for (int i=0; i<bonneLongueur; i++) {
                    t[i] = t[i].trim();
                }

                int idActivite = Integer.parseInt(t[0]);
                String type = t[1];
                long numSAAQ = Long.parseLong(t[2]);
                String date = t[3];
                String heure = t[4];
                int duree = Integer.parseInt(t[5]);
                String statut = t[7];
                String plaque = t[8];

                PlageHoraire horaire = new PlageHoraire(date, heure, duree);
                Eleve eleve = chercherEleve(eleves, numSAAQ);
                TypeActivite typeActivite = TypeActivite.valueOf(type);
                StatutActivite statutActivite = StatutActivite.valueOf(statut);

                activites.add(new Activite(idActivite, horaire, eleve, plaque, typeActivite, statutActivite));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return activites;
    }

    /**
     * Écrit les activités dans un fichier CSV
     * @param activites la liste des activités à écrire
     */
    public static void ecrireActivites(ArrayList<Activite> activites) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(getDir("data") + "activites" + YEAR + ".csv"))) {

            pw.println("ID_Activite,Type,NumSAAQ,Date,Heure,Duree,Montant,Statut,Plaque");

            for (Activite activite : activites) {
                pw.println(activite.getId() + "," +
                        activite.getType() + "," +
                        activite.getEleve().getNumSAAQ() + "," +
                        activite.getPlageHoraire().getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "," +
                        activite.getPlageHoraire().getHeureDebut().format(DateTimeFormatter.ofPattern("H:mm")) + "," +
                        activite.getPlageHoraire().getDuree() + "," +
                        activite.getMontant() + "," +
                        activite.getStatut() + "," +
                        activite.getPlaque());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lit les paiements à partir d'un fichier CSV
     * @param activites la liste des activités
     * @param eleves la liste des élèves
     * @return la liste des paiements
     */
    public static ArrayList<Paiement> lirePaiements(ArrayList<Activite> activites, ArrayList<Eleve> eleves) {
        int bonneLongueur = 8;
        ArrayList<Paiement> paiements = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getDir("data") + "paiements" + YEAR + ".csv"))) {
            String ligne = br.readLine();

            while ((ligne = br.readLine()) != null) {
                String[] t = ligne.split(",", -1);
                for (int i=0; i<bonneLongueur; i++) {
                    t[i] = t[i].trim();
                }

                String idPaiement = t[0];
                int idActivite = Integer.parseInt(t[1]);
                long numSAAQ = Long.parseLong(t[2]);
                double montant = Double.parseDouble(t[3]);
                double montantRestant = Double.parseDouble(t[4]);
                LocalDate date = LocalDate.parse(t[5], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                StatutPaiement statut = StatutPaiement.valueOf(t[6]);
                MethodePaiement methode = MethodePaiement.valueOf(t[7]);

                Activite activite = null;
                for (Activite a : activites) {
                    if (a.getId() == idActivite) activite = a;
                }

                Eleve eleve = null;
                for (Eleve e : eleves) {
                    if (e.getNumSAAQ() == numSAAQ) eleve = e;
                }

                if (activite == null || eleve == null) {
                    System.err.println("Erreur: chaque paiement devrait avoir une activité et un élève. Skipping ID: " + idPaiement);
                    continue;
                }

                paiements.add(new Paiement(idPaiement, montant, date, statut, montantRestant, activite, methode, eleve));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return paiements;
    }

    /**
     * Écrit les paiements dans un fichier CSV
     * @param paiements la liste des paiements à écrire
     */
    public static void ecrirePaiements(ArrayList<Paiement> paiements) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(getDir("data") + "paiements" + YEAR + ".csv"))) {

            pw.println("ID_Paiement,ID_Activite,NumSAAQ,Montant,MontantRestant,Date,Statut,Methode,Motif");

            for (Paiement paiement : paiements) {
                pw.println(paiement.getId() + "," +
                        paiement.getActivite().getId() + "," +
                        paiement.getEleve().getNumSAAQ() + "," +
                        paiement.getMontant() + "," +
                        paiement.getMontantRestant() + "," +
                        paiement.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "," +
                        paiement.getStatutPaiement() + "," +
                        paiement.getMethodePaiement() + "," +
                        paiement.getTypeActivite());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lit les voitures à partir d'un fichier CSV
     * @param depensesVoiture la liste des dépenses de voiture  
     * @return la liste des voitures
     */
    public static ArrayList<Voiture> lireVoitures(ArrayList<DepenseVoiture> depensesVoiture) {
        int bonneLongueur = 7;
        ArrayList<Voiture> voitures = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getDir("data") + "voitures" + YEAR + ".csv"))) {
            String ligne = br.readLine();

            while ((ligne = br.readLine()) != null) {
                String[] t = ligne.split(",", -1);
                for (int i=0; i<bonneLongueur; i++) {
                    t[i] = t[i].trim();
                }

                String marque = t[0];
                String plaque = t[1];
                int annee = Integer.parseInt(t[2]);
                double prix = Double.parseDouble(t[3]);
                int kmAchat = Integer.parseInt(t[4]);
                String etat = t[5];
                int km = Integer.parseInt(t[6]);

                StatutVoiture statut = StatutVoiture.valueOf(etat);
                ArrayList<DepenseVoiture> depensesVoiturePlaque = filtrerParPlaque(depensesVoiture, plaque);

                voitures.add(new Voiture(plaque, marque, annee, prix, kmAchat, statut, km, depensesVoiturePlaque));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return voitures;
    }

    /**
     * Écrit les voitures dans un fichier CSV
     * @param voitures la liste des voitures à écrire
     */
    public static void ecrireVoitures(ArrayList<Voiture> voitures) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(getDir("data") + "voitures" + YEAR + ".csv"))) {

            pw.println("Marque,Plaque,Annee,Prix,KmAchat,Etat,Km");

            for (Voiture v : voitures) {
                pw.println(v.getMarque() + "," +
                           v.getPlaque() + "," +
                           v.getAnnee() + "," +
                           v.getPrix() + "," +
                           v.getKmAchat() + "," +
                           v.getEtat() + "," +
                           v.getKm());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lit les dépenses de voiture à partir d'un fichier CSV
     * @return la liste des dépenses de voiture
     */
    public static ArrayList<DepenseVoiture> lireDepensesVoiture() {
        int bonneLongueur = 6;
        ArrayList<DepenseVoiture> depenses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getDir("data") + "depenses_voiture" + YEAR + ".csv"))) {
            String ligne = br.readLine();

            while ((ligne = br.readLine()) != null) {
                String[] t = ligne.split(",", -1);
                for (int i=0; i<bonneLongueur; i++) {
                    t[i] = t[i].trim();
                }

                int id = Integer.parseInt(t[0]);
                String plaque = t[1];
                String date = t[2];
                String categorie = t[3];
                String description = t[4];
                double montant = Double.parseDouble(t[5]);

                LocalDate dateDepense = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                TypeDepenseVoiture categorieVoiture = TypeDepenseVoiture.valueOf(categorie);

                depenses.add(new DepenseVoiture(id, plaque, dateDepense, categorieVoiture, description, montant));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return depenses;
    }

    /**
     * Écrit les dépenses de voiture dans un fichier CSV
     * @param depenses la liste des dépenses de voiture à écrire
     */
    public static void ecrireDepensesVoiture(ArrayList<DepenseVoiture> depenses) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(getDir("data") + "depenses_voiture" + YEAR + ".csv"))) {

            pw.println("ID_Depense,Plaque,Date,Categorie,Description,Montant");

            for (DepenseVoiture d : depenses) {
                pw.println(d.getId() + "," +
                           d.getPlaque() + "," +
                           d.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "," +
                           d.getCategorie() + "," +
                           d.getDescription() + "," +
                           d.getMontant());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lit les autres dépenses à partir d'un fichier CSV
     * @return la liste des autres dépenses
     */
    public static ArrayList<AutreDepense> lireAutresDepenses() {
        int bonneLongueur = 5;
        ArrayList<AutreDepense> depenses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getDir("data") + "autres_depenses" + YEAR + ".csv"))) {
            String ligne = br.readLine(); // skip header

            while ((ligne = br.readLine()) != null) {
                String[] t = ligne.split(",", -1);
                for (int i=0; i<bonneLongueur; i++) {
                    t[i] = t[i].trim();
                }

                int id = Integer.parseInt(t[0]);
                String date = t[1];
                String categorie = t[2];
                String description = t[3];
                double montant = Double.parseDouble(t[4]);

                LocalDate dateDepense = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                TypeAutreDepense typeDepense = TypeAutreDepense.valueOf(categorie);

                depenses.add(new AutreDepense(id, dateDepense, typeDepense, description, montant));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return depenses;
    }

    /**
     * Écrit les autres dépenses dans un fichier CSV
     * @param depenses la liste des autres dépenses à écrire
     */
    public static void ecrireAutresDepenses(ArrayList<AutreDepense> depenses) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(getDir("data") + "autres_depenses" + YEAR + ".csv"))) {

            pw.println("ID_Depense,Date,Categorie,Description,Montant");

            for (AutreDepense d : depenses) {
                pw.println(d.getId() + "," +
                           d.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "," +
                           d.getCategorie() + "," +
                           d.getDescription() + "," +
                           d.getMontant());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cherche un élève dans une liste par son numéro SAAQ
     * @param eleves la liste des élèves
     * @param num le numéro SAAQ à rechercher
     * @return l'élève trouvé ou null s'il n'est pas trouvé
     */
    private static Eleve chercherEleve(ArrayList<Eleve> eleves, long numSAAQ) {
        for (Eleve e : eleves) {
            if (e.getNumSAAQ() == numSAAQ) {
                return e;
            }
        }
        return null;
    }

    /**
     * Filtre les dépenses de voiture par plaque d'immatriculation
     * @param depenses la liste des dépenses de voiture
     * @param plaque la plaque d'immatriculation à filtrer
     * @return la liste des dépenses de voiture correspondant à la plaque
     */
    private static ArrayList<DepenseVoiture> filtrerParPlaque(ArrayList<DepenseVoiture> depenses, String plaque) {
        ArrayList<DepenseVoiture> temp = new ArrayList<>();
        for (DepenseVoiture depense : depenses) {
            if (depense.getPlaque().equals(plaque)) temp.add(depense);
        }

        return temp;
    }

    /**
     * Retourne le chemin du dossier spécifié
     * @param folder le nom du dossier
     * @return le chemin complet du dossier
     */
    public static String getDir(String folder) {
        return System.getProperty("user.dir") + "\\" + folder + "\\";
    }
}