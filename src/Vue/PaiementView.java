package Vue;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import Modele.*;

public class PaiementView {

    private BorderPane root = new BorderPane();

    private TableView<Paiement> table = new TableView<>();
    private TextField txtIdActivite = new TextField();
    private TextField txtStatut = new TextField();
    private TextField txtMethode = new TextField();
    private Button btnAjouter = new Button("Enregistrer paiement");

    public PaiementView() {
        VBox form = new VBox(10);

        txtIdActivite.setPromptText("ID Activité");
        txtStatut.setPromptText("Statut (P/PP/I)");
        txtMethode.setPromptText("Méthode (E/C/V)");

        form.getChildren().addAll(txtIdActivite, txtStatut, txtMethode, btnAjouter);

        root.setLeft(form);
        root.setCenter(table);
    }

    public BorderPane getRoot() { return root; }
    public TableView<Paiement> getTable() { return table; }
    public TextField getTxtIdActivite() { return txtIdActivite; }
    public TextField getTxtStatut() { return txtStatut; }
    public TextField getTxtMethode() { return txtMethode; }
    public Button getBtnAjouter() { return btnAjouter; }
}