package tp2.src.controleur;

import tp2.src.vue.Gestion;
import tp2.src.modele.AutoEcole;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Classe principale de l'application de gestion d'une auto-école
 */
public class Main extends Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AutoEcole autoEcole = new AutoEcole();
    private static final boolean testing = false;
    private static final boolean menuTextuel = true;

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

        if (menuTextuel) {
            Gestion.gestionAutoEcole(scanner, autoEcole);
        } else {
            launch(args);
        }

        sauvegarde();
    }

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, JavaFX!");
        stage.setScene(new Scene(label, 300, 200));
        stage.show();
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
        System.out.println("Sauvegarde terminée");
    }


}
