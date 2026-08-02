package Vue;

import javafx.scene.control.*;
import javafx.scene.layout.*;

import Modele.Activite;

public class ActiviteView {

    private BorderPane root = new BorderPane();

    private TableView<Activite> table = new TableView<>();
    private TextField txtType = new TextField();
    private TextField txtNum = new TextField();
    private TextField txtDate = new TextField();
    private TextField txtHeure = new TextField();
    private TextField txtDuree = new TextField();
    private TextField txtStatut = new TextField();
    private TextField txtPlaque = new TextField();
    private Button btnAjouter = new Button("Planifier");
    private Button btnSupprimer = new Button("Annuler");
    private Button btnCompleter = new Button("Compléter");

    public ActiviteView() {
        VBox form = new VBox(10);

        txtType.setPromptText("Type (LPA, LPZ...)");
        txtNum.setPromptText("Num SAAQ");
        txtDate.setPromptText("Date (dd-MM-yyyy)");
        txtHeure.setPromptText("Heure (H:mm)");
        txtDuree.setPromptText("Durée (min)");
        txtStatut.setPromptText("Statut (C/NC)");
        txtPlaque.setPromptText("Plaque");

        form.getChildren().addAll(txtType, txtNum, txtDate, txtHeure, txtDuree, txtStatut, txtPlaque,
                btnAjouter, btnCompleter, btnSupprimer);

        root.setLeft(form);
        root.setCenter(table);
    }

    public BorderPane getRoot() { return root; }
    public TableView<Activite> getTable() { return table; }
    public TextField getTxtType() { return txtType; }
    public TextField getTxtNum() { return txtNum; }
    public TextField getTxtDate() { return txtDate; }
    public TextField getTxtHeure() { return txtHeure; }
    public TextField getTxtDuree() { return txtDuree; }
    public TextField getTxtStatut() { return txtStatut; }
    public TextField getTxtPlaque() { return txtPlaque; }
    public Button getBtnAjouter() { return btnAjouter; }
    public Button getBtnSupprimer() { return btnSupprimer; }
    public Button getBtnCompleter() { return btnCompleter; }
}