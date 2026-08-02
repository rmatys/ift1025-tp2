package Vue;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import Modele.*;

public class EleveView {

    private BorderPane root = new BorderPane();

    private TableView<Eleve> table = new TableView<>();
    private TextField txtNum = new TextField();
    private TextField txtNom = new TextField();
    private TextField txtPrenom = new TextField();
    private TextField txtAdresse = new TextField();
    private TextField txtTel = new TextField();
    private Button btnAjouter = new Button("Ajouter");
    private Button btnSupprimer = new Button("Supprimer");

    public EleveView() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        txtNum.setPromptText("Numéro SAAQ");
        txtNom.setPromptText("Nom");
        txtPrenom.setPromptText("Prénom");
        txtAdresse.setPromptText("Adresse");
        txtTel.setPromptText("Téléphone");

        form.getChildren().addAll(txtNum, txtNom, txtPrenom, txtAdresse, txtTel, btnAjouter, btnSupprimer);

        root.setLeft(form);
        root.setCenter(table);
    }

    public BorderPane getRoot() { return root; }
    public TableView<Eleve> getTable() { return table; }
    public TextField getTxtNum() { return txtNum; }
    public TextField getTxtNom() { return txtNom; }
    public TextField getTxtPrenom() { return txtPrenom; }
    public TextField getTxtAdresse() { return txtAdresse; }
    public TextField getTxtTel() { return txtTel; }
    public Button getBtnAjouter() { return btnAjouter; }
    public Button getBtnSupprimer() { return btnSupprimer; }
}