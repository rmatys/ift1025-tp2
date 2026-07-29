package Controleur;

import Modele.AutoEcole;
import Vue.Saisie;

import java.util.Locale;
import java.util.Scanner;

/**
 * Classe principale de l'application de gestion d'une auto-école
 */
public class Main {
    // Locale.US pour que Scanner.nextDouble() accepte le point comme séparateur décimal,
    // comme dans tous les exemples affichés par l'application (ex: "350.00")
    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private static final AutoEcole autoEcole = new AutoEcole();
    private static final boolean testing = false;

    /**
     * Point d'entrée de l'application
     */
    public static void main(String[] args) {
        if (testing) {
            Test.test();
            return;
        }

        autoEcole.chargerEleves();
        autoEcole.chargerActivites();
        autoEcole.chargerPaiements();
        autoEcole.chargerDepenses();
        autoEcole.chargerVoitures();

        Gestion.gestionAutoEcole(scanner, autoEcole);

        sauvegarde();
    }

    /**
     * Sauvegarde toutes les données de l'auto-école dans les fichiers CSV
     */
    public static void sauvegarde() {
        autoEcole.sauvegarderEleves();
        autoEcole.sauvegarderActivites();
        autoEcole.sauvegarderPaiements();
        autoEcole.sauvegarderDepensesVoiture();
        autoEcole.sauvegarderAutresDepenses();
        autoEcole.sauvegarderVoitures();
        Saisie.afficherMessage("Sauvegarde terminée");
    }
}
