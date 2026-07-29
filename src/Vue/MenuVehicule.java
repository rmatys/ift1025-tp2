package Vue;

import Modele.AutoEcole;
import Modele.OperationInvalideException;
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

import Autre.Util;

public class MenuVehicule extends BorderPane {
    private final AutoEcole autoEcole;
    private TableView<Voiture> table;
    private TextField champPlaque, champMarque, champAnnee, champPrix, champKmAchat, champKm;
    private ComboBox<StatutVoiture> champEtat;
    private Voiture voitureSelectionnee;

    public MenuVehicule(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;

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

        Button btnAjouter = Util.creerBoutonMenu("Ajouter");
        Button btnModifier = Util.creerBoutonMenu("Modifier");
        Button btnSupprimer = Util.creerBoutonMenu("Supprimer");
        Button btnRetour = Util.creerBoutonMenu("Retour au menu principal");

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

        rafraichirTable();

        table.getSelectionModel().selectedItemProperty().addListener((obs, ancienne, nouvelle) -> {
            voitureSelectionnee = nouvelle;
            if (nouvelle != null) remplirFormulaire(nouvelle);
        });

        // -- gestion des événements (appelle le modèle directement) --
        btnAjouter.setOnAction(e -> ajouterVoiture());
        btnModifier.setOnAction(e -> modifierVoiture());
        btnSupprimer.setOnAction(e -> supprimerVoiture());
        btnRetour.setOnAction(e -> conteneur.setCenter(new MenuPrincipal(autoEcole, conteneur)));
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

    private void ajouterVoiture() {
        try {
            if (champPlaque.getText().isBlank() || champMarque.getText().isBlank()
                    || champAnnee.getText().isBlank() || champPrix.getText().isBlank()
                    || champKmAchat.getText().isBlank() || champKm.getText().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            if (autoEcole.rechercherVoiture(champPlaque.getText().trim()) != null) {
                throw new IllegalArgumentException("Un véhicule avec cette plaque existe déjà.");
            }

            int annee = Integer.parseInt(champAnnee.getText().trim());
            double prix = Double.parseDouble(champPrix.getText().trim());
            int kmAchat = Integer.parseInt(champKmAchat.getText().trim());
            int km = Integer.parseInt(champKm.getText().trim());

            autoEcole.creerVoiture(champMarque.getText(), champPlaque.getText(), annee, prix,
                                    kmAchat, champEtat.getValue(), km);
            rafraichirTable();
            viderFormulaire();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "L'année, le prix et les kilométrages doivent être des nombres valides.").showAndWait();
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void modifierVoiture() {
        try {
            if (voitureSelectionnee == null) {
                throw new IllegalArgumentException("Sélectionnez un véhicule à modifier dans la liste.");
            }

            if (champMarque.getText().isBlank() || champAnnee.getText().isBlank()
                    || champPrix.getText().isBlank() || champKmAchat.getText().isBlank()
                    || champKm.getText().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            int annee = Integer.parseInt(champAnnee.getText().trim());
            double prix = Double.parseDouble(champPrix.getText().trim());
            int kmAchat = Integer.parseInt(champKmAchat.getText().trim());
            int km = Integer.parseInt(champKm.getText().trim());

            autoEcole.modifierVoiture(voitureSelectionnee.getPlaque(), champMarque.getText(),
                                       annee, prix, kmAchat, champEtat.getValue(), km);
            rafraichirTable();
            viderFormulaire();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "L'année, le prix et les kilométrages doivent être des nombres valides.").showAndWait();
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void supprimerVoiture() {
        if (voitureSelectionnee != null) {
            autoEcole.supprimerVoiture(voitureSelectionnee.getPlaque());
            rafraichirTable();
            viderFormulaire();
        }
    }

    private void remplirFormulaire(Voiture voiture) {
        champPlaque.setText(voiture.getPlaque());
        champPlaque.setDisable(true);
        champMarque.setText(voiture.getMarque());
        champAnnee.setText(String.valueOf(voiture.getAnnee()));
        champPrix.setText(String.valueOf(voiture.getPrix()));
        champKmAchat.setText(String.valueOf(voiture.getKmAchat()));
        champKm.setText(String.valueOf(voiture.getKm()));
        champEtat.setValue(voiture.getEtat());
    }

    private void viderFormulaire() {
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

    private void rafraichirTable() {
        table.setItems(FXCollections.observableArrayList(autoEcole.getVoitures()));
    }

}
