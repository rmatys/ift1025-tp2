package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainView {

    private VBox root = new VBox(20);
    private Button btnEleves = new Button("Gestion des élèves");
    private Button btnActivites = new Button("Gestion des activités");
    private Button btnPaiements = new Button("Gestion des paiements");

    public MainView() {
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(btnEleves, btnActivites, btnPaiements);
    }

    public VBox getRoot() { return root; }
    public Button getBtnEleves() { return btnEleves; }
    public Button getBtnActivites() { return btnActivites; }
    public Button getBtnPaiements() { return btnPaiements; }
}