package Vue;

import Modele.AutoEcole;
import Modele.CSV;
import Modele.Rapports;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import Autre.Util;

public class MenuRapport extends BorderPane {
    private final AutoEcole autoEcole;

    private record RapportInfo(String libelle, String nomFichier, BooleanSupplier generation) {}

    public MenuRapport(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;
        List<RapportInfo> rapports = listeRapports();

        // -- construction de l'interface --
        Label titre = new Label("Rapports");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label sousTitre = new Label("Sélectionnez le rapport à générer");
        sousTitre.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        Button btnTous = Util.creerBoutonMenu("Générer tous les rapports");
        Button btnRetour = Util.creerBoutonMenu("Retour au menu principal");

        VBox menu = new VBox(12, titre, sousTitre, new Separator(), btnTous, new Separator());
        for (RapportInfo rapport : rapports) {
            Button bouton = Util.creerBoutonMenu(rapport.libelle());
            bouton.setOnAction(e -> genererRapportUnique(rapport));
            menu.getChildren().add(bouton);
        }
        menu.getChildren().addAll(new Separator(), btnRetour);

        menu.setAlignment(Pos.TOP_CENTER);
        menu.setPadding(new Insets(30));
        menu.setMaxWidth(320);
        menu.setStyle("-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-border-color: #dcdde1; -fx-border-radius: 10;");

        BorderPane.setMargin(menu, new Insets(40));
        setStyle("-fx-background-color: #f4f6f8;");
        setCenter(menu);

        // -- gestion des événements (appelle le modèle directement) --
        btnTous.setOnAction(e -> genererTousLesRapports(rapports));
        btnRetour.setOnAction(e -> conteneur.setCenter(new MenuPrincipal(autoEcole, conteneur)));
    }

    private List<RapportInfo> listeRapports() {
        return List.of(
                new RapportInfo("Rapport des élèves", "rapportEleves" + CSV.YEAR + ".txt",
                        () -> Rapports.genererRapportEleves(autoEcole.getEleves())),
                new RapportInfo("Rapport des revenus", "rapportRevenus" + CSV.YEAR + ".txt",
                        () -> Rapports.genererRapportRevenus(autoEcole.getActivites(), autoEcole.getPaiements())),
                new RapportInfo("Rapport des dépenses véhicule", "rapportDepensesVoiture" + CSV.YEAR + ".txt",
                        () -> Rapports.genererRapportDepensesVoiture(autoEcole.getDepensesVoiture())),
                new RapportInfo("Rapport des autres dépenses", "rapportAutresDepenses" + CSV.YEAR + ".txt",
                        () -> Rapports.genererRapportAutresDepenses(autoEcole.getAutresDepenses()))
        );
    }

    /**
     * Génère les 4 rapports d'un coup et les enregistre dans un unique répertoire
     * choisi par l'utilisateur à l'aide d'un DirectoryChooser.
     */
    private void genererTousLesRapports(List<RapportInfo> rapports) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choisir le répertoire de destination");
        File dossier = directoryChooser.showDialog(getScene().getWindow());
        if (dossier == null) return;

        List<String> echecs = new ArrayList<>();
        for (RapportInfo rapport : rapports) {
            if (!rapport.generation().getAsBoolean()) {
                echecs.add(rapport.libelle());
                continue;
            }

            try {
                File source = new File(CSV.getDir("rapport"), rapport.nomFichier());
                File destination = new File(dossier, rapport.nomFichier());
                Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                echecs.add(rapport.libelle());
            }
        }

        if (echecs.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Les 4 rapports ont été enregistrés dans : " + dossier.getAbsolutePath()).showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Échec de la génération pour : " + String.join(", ", echecs)).showAndWait();
        }
    }

    /**
     * Génère un rapport via le modèle, puis laisse l'utilisateur choisir où enregistrer
     * le fichier texte produit à l'aide d'un FileChooser.
     */
    private void genererRapportUnique(RapportInfo rapport) {
        if (!rapport.generation().getAsBoolean()) {
            new Alert(Alert.AlertType.ERROR, "La génération du rapport a échoué.").showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le rapport");
        fileChooser.setInitialFileName(rapport.nomFichier());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier texte", "*.txt"));
        File destination = fileChooser.showSaveDialog(getScene().getWindow());
        if (destination == null) return;

        try {
            File source = new File(CSV.getDir("rapport"), rapport.nomFichier());
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            new Alert(Alert.AlertType.INFORMATION, "Rapport enregistré : " + destination.getAbsolutePath()).showAndWait();
        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, "Impossible d'enregistrer le rapport à l'emplacement choisi.").showAndWait();
        }
    }
}
