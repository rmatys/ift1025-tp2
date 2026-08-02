package Vue;

import Modele.StatutVoiture;
import Modele.Voiture;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;

import Autre.Util;

public class MenuVehicule extends BorderPane {
    private TableView<Voiture> table;
    private TextField champPlaque, champMarque, champAnnee, champPrix, champKmAchat, champKm;
    private ComboBox<StatutVoiture> champEtat;
    private Button btnAjouter, btnModifier, btnSupprimer, btnRetour;
    private Voiture voitureSelectionnee;

    public MenuVehicule() {
        // -- construction de l'interface --
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        creerColonnes();

        champPlaque = new TextField();
        champMarque = new TextField();
        champAnnee = new TextField();
        champPrix = new TextField();
        champKmAchat = new TextField();
        champKm = new TextField();
        champEtat = new ComboBox<>(FXCollections.observableArrayList(StatutVoiture.values()));
        champEtat.setValue(StatutVoiture.D);

        champPlaque.setPromptText("Ex : ABC-1234");
        champMarque.setPromptText("Ex : Toyota Corolla");
        champAnnee.setPromptText("Ex : 2020");
        champPrix.setPromptText("Ex : 15000.00");
        champKmAchat.setPromptText("Ex : 10000");
        champKm.setPromptText("Ex : 25000");

        btnAjouter = Util.creerBoutonMenu("Ajouter");
        btnModifier = Util.creerBoutonMenu("Modifier");
        btnSupprimer = Util.creerBoutonMenu("Supprimer");
        btnRetour = Util.creerBoutonMenu("Retour au menu principal");

        Label titreFormulaire = new Label("Fiche véhicule");
        titreFormulaire.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        VBox formulaire = new VBox(10, titreFormulaire,
                                    new Separator(),
                                    new Label("Plaque :"), champPlaque,
                                    new Label("Marque :"), champMarque,
                                    new Label("Année :"), champAnnee,
                                    new Label("Prix d'achat :"), champPrix,
                                    new Label("Km à l'achat :"), champKmAchat,
                                    new Label("Km actuel :"), champKm,
                                    new Label("État :"), champEtat,
                                    new Separator(),
                                    btnAjouter, btnModifier, btnSupprimer, btnRetour);
        formulaire.setPadding(new Insets(20));
        formulaire.setPrefWidth(280);
        formulaire.setStyle("-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-border-color: #dcdde1; -fx-border-radius: 10;");

        BorderPane.setMargin(formulaire, new Insets(15));
        BorderPane.setMargin(table, new Insets(15, 0, 15, 15));

        setStyle("-fx-background-color: #f4f6f8;");
        setCenter(table);
        setRight(formulaire);

        table.getSelectionModel().selectedItemProperty().addListener((obs, ancienne, nouvelle) -> {
            voitureSelectionnee = nouvelle;
            if (nouvelle != null) remplirFormulaire(nouvelle);
        });
    }

    private void creerColonnes() {
        TableColumn<Voiture, String> colPlaque = new TableColumn<>("Plaque");
        colPlaque.setCellValueFactory(new PropertyValueFactory<>("plaque"));

        TableColumn<Voiture, String> colMarque = new TableColumn<>("Marque");
        colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));

        TableColumn<Voiture, Integer> colAnnee = new TableColumn<>("Année");
        colAnnee.setCellValueFactory(new PropertyValueFactory<>("annee"));

        TableColumn<Voiture, Double> colPrix = new TableColumn<>("Prix d'achat");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<Voiture, Integer> colKmAchat = new TableColumn<>("Km à l'achat");
        colKmAchat.setCellValueFactory(new PropertyValueFactory<>("kmAchat"));

        TableColumn<Voiture, Integer> colKm = new TableColumn<>("Km actuel");
        colKm.setCellValueFactory(new PropertyValueFactory<>("km"));

        TableColumn<Voiture, StatutVoiture> colEtat = new TableColumn<>("État");
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colEtat.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(StatutVoiture item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getLibelle());
            }
        });

        table.getColumns().addAll(colPlaque, colMarque, colAnnee, colPrix, colKmAchat, colKm, colEtat);
    }

    // ---- câblage des événements (le contrôleur s'y abonne) ----
    public void setOnAjouter(Runnable action) { btnAjouter.setOnAction(e -> action.run()); }
    public void setOnModifier(Runnable action) { btnModifier.setOnAction(e -> action.run()); }
    public void setOnSupprimer(Runnable action) { btnSupprimer.setOnAction(e -> action.run()); }
    public void setOnRetour(Runnable action) { btnRetour.setOnAction(e -> action.run()); }

    // ---- mise à jour de l'affichage (le contrôleur les appelle) ----
    public void afficherVoitures(List<Voiture> voitures) {
        table.setItems(FXCollections.observableArrayList(voitures));
    }

    public void remplirFormulaire(Voiture voiture) {
        champPlaque.setText(voiture.getPlaque());
        champPlaque.setDisable(true);
        champMarque.setText(voiture.getMarque());
        champAnnee.setText(String.valueOf(voiture.getAnnee()));
        champPrix.setText(String.valueOf(voiture.getPrix()));
        champKmAchat.setText(String.valueOf(voiture.getKmAchat()));
        champKm.setText(String.valueOf(voiture.getKm()));
        champEtat.setValue(voiture.getEtat());
    }

    public void viderFormulaire() {
        voitureSelectionnee = null;
        table.getSelectionModel().clearSelection();
        champPlaque.clear();
        champPlaque.setDisable(false);
        champMarque.clear();
        champAnnee.clear();
        champPrix.clear();
        champKmAchat.clear();
        champKm.clear();
        champEtat.setValue(StatutVoiture.D);
    }

    public void afficherErreur(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }

    // ---- lecture de la saisie brute (le contrôleur les lit) ----
    public String getTextePlaque() { return champPlaque.getText(); }
    public String getTexteMarque() { return champMarque.getText(); }
    public String getTexteAnnee() { return champAnnee.getText(); }
    public String getTextePrix() { return champPrix.getText(); }
    public String getTexteKmAchat() { return champKmAchat.getText(); }
    public String getTexteKm() { return champKm.getText(); }
    public StatutVoiture getEtatChoisi() { return champEtat.getValue(); }
    public Voiture getVoitureSelectionnee() { return voitureSelectionnee; }
}
