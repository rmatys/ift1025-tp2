import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import Modele.AutoEcole;
import Vue.MenuPrincipal;
import Vue.Saisie;

import java.util.Locale;
import java.util.Scanner;

import Controleur.Gestion;
import Controleur.Test;

/**
 * Classe principale de l'application de gestion d'une auto-école
 */
public class Main extends Application {
    // Locale.US pour que Scanner.nextDouble() accepte le point comme séparateur décimal,
    // comme dans tous les exemples affichés par l'application (ex: "350.00")
    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private static final AutoEcole autoEcole = new AutoEcole();
    
    private static final boolean testing = false;
    private static final boolean textuel = false;

    /**
     * Lance l'application graphique
     */
    @Override
    public void start(Stage stage) {
        chargerDonnees();

        BorderPane racine = new BorderPane();
        racine.setCenter(new MenuPrincipal(autoEcole, racine));

        stage.setScene(new Scene(racine, 1000, 700));
        stage.setTitle("Gestion Auto-École");
        stage.show();

        stage.setOnCloseRequest(e -> sauvegarde());
    }

    /**
     * Point d'entrée de l'application
     */
    public static void main(String[] args) {
        if (testing) {
            Test.test();
            return;
        }

        if (textuel) {
            chargerDonnees();
            Gestion.gestionAutoEcole(scanner, autoEcole);
            sauvegarde();

        } else {
            System.out.println("Lancement de l'application graphique...");
            launch(args);

        }
    }

    /**
     * Charge les données de l'auto-école depuis les fichiers CSV
     */
    public static void chargerDonnees() {
        autoEcole.chargerEleves();
        autoEcole.chargerActivites();
        autoEcole.chargerPaiements();
        autoEcole.chargerDepenses();
        autoEcole.chargerVoitures();
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
