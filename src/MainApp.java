package tp2.src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp2.src.vue.MainView;
import tp2.src.controleur.MainController;
import tp2.src.modele.AutoEcole;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        AutoEcole autoEcole = new AutoEcole();
        autoEcole.chargerEleves();
        autoEcole.chargerActivites();
        autoEcole.chargerPaiements();
        autoEcole.chargerDepenses();
        autoEcole.chargerVoitures();

        MainView mainView = new MainView();
        MainController controller = new MainController(mainView, autoEcole);

        Scene scene = new Scene(mainView.getRoot(), 900, 600);
        stage.setTitle("Auto-École - Gestion");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}