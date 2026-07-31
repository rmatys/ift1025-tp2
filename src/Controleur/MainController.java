package tp2.src.controleur;

import tp2.src.modele.AutoEcole;
import tp2.src.vue.MainView;
import tp2.src.vue.EleveView;
import tp2.src.vue.ActiviteView;
import tp2.src.vue.PaiementView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    private MainView view;
    private AutoEcole autoEcole;

    public MainController(MainView view, AutoEcole autoEcole) {
        this.view = view;
        this.autoEcole = autoEcole;

        view.getBtnEleves().setOnAction(e -> ouvrirEleves());
        view.getBtnActivites().setOnAction(e -> ouvrirActivites());
        view.getBtnPaiements().setOnAction(e -> ouvrirPaiements());
    }

    private void ouvrirEleves() {
        Stage stage = new Stage();
        EleveView ev = new EleveView();
        new EleveController(ev, autoEcole);
        stage.setScene(new Scene(ev.getRoot(), 900, 600));
        stage.setTitle("Gestion des élèves");
        stage.show();
    }

    private void ouvrirActivites() {
        Stage stage = new Stage();
        ActiviteView av = new ActiviteView();
        new ActiviteController(av, autoEcole);
        stage.setScene(new Scene(av.getRoot(), 900, 600));
        stage.setTitle("Gestion des activités");
        stage.show();
    }

    private void ouvrirPaiements() {
        Stage stage = new Stage();
        PaiementView pv = new PaiementView();
        new PaiementController(pv, autoEcole);
        stage.setScene(new Scene(pv.getRoot(), 900, 600));
        stage.setTitle("Gestion des paiements");
        stage.show();
    }
}