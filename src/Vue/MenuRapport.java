package Vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import Autre.Util;

public class MenuRapport extends BorderPane {
    private VBox menu;
    private Button btnTous, btnRetour;
    private Runnable actionGenererTous;
    private Consumer<String> actionGenererUnique;

    public MenuRapport() {
        // -- construction de l'interface --
        Label titre = new Label("Rapports");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label sousTitre = new Label("Sélectionnez le rapport à générer");
        sousTitre.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        btnTous = Util.creerBoutonMenu("Générer tous les rapports");
        btnRetour = Util.creerBoutonMenu("Retour au menu principal");

        menu = new VBox(12, titre, sousTitre, new Separator(), btnTous, new Separator());
        menu.getChildren().add(btnRetour);

        menu.setAlignment(Pos.TOP_CENTER);
        menu.setPadding(new Insets(30));
        menu.setMaxWidth(320);
        menu.setStyle("-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-border-color: #dcdde1; -fx-border-radius: 10;");

        BorderPane.setMargin(menu, new Insets(40));
        setStyle("-fx-background-color: #f4f6f8;");
        setCenter(menu);

        btnTous.setOnAction(e -> { if (actionGenererTous != null) actionGenererTous.run(); });
    }

    // ---- câblage des événements (le contrôleur s'y abonne) ----
    public void setLibellesRapports(List<String> libelles) {
        int indexInsertion = menu.getChildren().indexOf(btnTous) + 1;
        for (String libelle : libelles) {
            Button bouton = Util.creerBoutonMenu(libelle);
            bouton.setOnAction(e -> { if (actionGenererUnique != null) actionGenererUnique.accept(libelle); });
            menu.getChildren().add(indexInsertion, bouton);
            indexInsertion++;
        }
    }

    public void setOnGenererTous(Runnable action) { this.actionGenererTous = action; }
    public void setOnGenererUnique(Consumer<String> action) { this.actionGenererUnique = action; }
    public void setOnRetour(Runnable action) { btnRetour.setOnAction(e -> action.run()); }

    // ---- dialogues JavaFX (préoccupation UI, appelés par le contrôleur) ----
    public File choisirDossierDestination() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choisir le répertoire de destination");
        return directoryChooser.showDialog(getScene().getWindow());
    }

    public File choisirFichierDestination(String nomParDefaut) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le rapport");
        fileChooser.setInitialFileName(nomParDefaut);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier texte", "*.txt"));
        return fileChooser.showSaveDialog(getScene().getWindow());
    }

    // ---- affichage de messages ----
    public void afficherInformation(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }

    public void afficherErreur(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}
