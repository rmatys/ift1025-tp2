package Vue;

import Modele.AutoEcole;
import Modele.Eleve;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

import Autre.Util;

public class MenuEleve extends BorderPane {
    private final AutoEcole autoEcole;
    private TableView<Eleve> table;
    private TextField champRecherche;
    private TextField champNumSAAQ, champNom, champPrenom, champAdresse, champTelephone;
    private Eleve eleveSelectionne;

    public MenuEleve(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;

        // -- construction de l'interface --
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        creerColonnes();

        champRecherche = new TextField();
        champRecherche.setPromptText("Rechercher par Numéro SAAQ, nom ou prénom");

        VBox zoneTable = new VBox(10, champRecherche, table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        champNumSAAQ = new TextField();
        champNom = new TextField();
        champPrenom = new TextField();
        champAdresse = new TextField();
        champTelephone = new TextField();

        champNumSAAQ.setPromptText("Ex : 1234-56789-01");
        champNom.setPromptText("Nom de famille");
        champPrenom.setPromptText("Prénom");
        champAdresse.setPromptText("Adresse complète");
        champTelephone.setPromptText("Ex : 514-555-1234");

        Button btnAjouter = Util.creerBoutonMenu("Ajouter");
        Button btnModifier = Util.creerBoutonMenu("Modifier");
        Button btnSupprimer = Util.creerBoutonMenu("Supprimer");
        Button btnRetour = Util.creerBoutonMenu("Retour au menu principal");

        Label titreFormulaire = new Label("Fiche élève");
        titreFormulaire.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        VBox formulaire = new VBox(10, titreFormulaire,
                                    new Separator(),
                                    new Label("Numéro SAAQ :"), champNumSAAQ,
                                    new Label("Nom :"), champNom,
                                    new Label("Prénom :"), champPrenom,
                                    new Label("Adresse :"), champAdresse,
                                    new Label("Téléphone :"), champTelephone,
                                    new Separator(),
                                    btnAjouter, btnModifier, btnSupprimer, btnRetour);
        formulaire.setPadding(new Insets(20));
        formulaire.setPrefWidth(280);
        formulaire.setStyle("-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-border-color: #dcdde1; -fx-border-radius: 10;");

        BorderPane.setMargin(formulaire, new Insets(15));
        BorderPane.setMargin(zoneTable, new Insets(15, 0, 15, 15));

        setStyle("-fx-background-color: #f4f6f8;");
        setCenter(zoneTable);
        setRight(formulaire);

        rafraichirTable();

        table.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouvel) -> {
            eleveSelectionne = nouvel;
            if (nouvel != null) remplirFormulaire(nouvel);
        });

        champRecherche.textProperty().addListener((obs, ancien, nouveau) -> rafraichirTable());

        // -- gestion des événements (appelle le modèle directement) --
        btnAjouter.setOnAction(e -> ajouterEleve());
        btnModifier.setOnAction(e -> modifierEleve());
        btnSupprimer.setOnAction(e -> supprimerEleve());
        btnRetour.setOnAction(e -> conteneur.setCenter(new MenuPrincipal(autoEcole, conteneur)));
    }

    private void creerColonnes() {
        TableColumn<Eleve, Long> colNumSAAQ = new TableColumn<>("NumSAAQ");
        colNumSAAQ.setCellValueFactory(new PropertyValueFactory<>("numSAAQ"));

        TableColumn<Eleve, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Eleve, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Eleve, String> colAdresse = new TableColumn<>("Adresse");
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        TableColumn<Eleve, String> colTelephone = new TableColumn<>("Téléphone");
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableColumn<Eleve, LocalDate> colDateDebut = new TableColumn<>("Date début");
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));

        TableColumn<Eleve, LocalDate> colDateFin = new TableColumn<>("Date fin");
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colDateFin.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.toString());
            }
        });

        table.getColumns().addAll(colNumSAAQ, colNom, colPrenom, colAdresse, colTelephone, colDateDebut, colDateFin);
    }

    private void ajouterEleve() {
        try {
            if (champNumSAAQ.getText().isBlank() || champNom.getText().isBlank()
                    || champPrenom.getText().isBlank() || champAdresse.getText().isBlank()
                    || champTelephone.getText().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            long numSAAQ = Long.parseLong(champNumSAAQ.getText().trim());
            Eleve e = new Eleve(numSAAQ, champPrenom.getText(), champNom.getText(),
                                 champAdresse.getText(), champTelephone.getText(), LocalDate.now());
            autoEcole.ajouterEleve(e);
            rafraichirTable();
            viderFormulaire();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Le NumSAAQ doit être un nombre valide.").showAndWait();
        } catch (IllegalArgumentException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void modifierEleve() {
        try {
            if (eleveSelectionne == null) {
                throw new IllegalArgumentException("Sélectionnez un élève à modifier dans la liste.");
            }

            if (champNom.getText().isBlank() || champPrenom.getText().isBlank()
                    || champAdresse.getText().isBlank() || champTelephone.getText().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            autoEcole.modifierEleve(eleveSelectionne.getNumSAAQ(), champPrenom.getText(),
                                     champNom.getText(), champAdresse.getText(), champTelephone.getText());
            rafraichirTable();
            viderFormulaire();
        } catch (IllegalArgumentException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void supprimerEleve() {
        Eleve selectionne = table.getSelectionModel().getSelectedItem();
        if (selectionne != null) {
            autoEcole.supprimerEleve(selectionne.getNumSAAQ());
            rafraichirTable();
            viderFormulaire();
        }
    }

    private void remplirFormulaire(Eleve eleve) {
        champNumSAAQ.setText(String.valueOf(eleve.getNumSAAQ()));
        champNumSAAQ.setDisable(true);
        champNom.setText(eleve.getNom());
        champPrenom.setText(eleve.getPrenom());
        champAdresse.setText(eleve.getAdresse());
        champTelephone.setText(eleve.getTelephone());
    }

    private void viderFormulaire() {
        eleveSelectionne = null;
        table.getSelectionModel().clearSelection();
        champNumSAAQ.clear();
        champNumSAAQ.setDisable(false);
        champNom.clear();
        champPrenom.clear();
        champAdresse.clear();
        champTelephone.clear();
    }

    private ArrayList<Eleve> filtrerEleves() {
        String texte = champRecherche.getText() == null ? "" : champRecherche.getText().trim().toLowerCase();
        ArrayList<Eleve> resultat = new ArrayList<>();
        for (Eleve eleve : autoEcole.getEleves()) {
            if (texte.isEmpty()
                    || String.valueOf(eleve.getNumSAAQ()).contains(texte)
                    || eleve.getNom().toLowerCase().contains(texte)
                    || eleve.getPrenom().toLowerCase().contains(texte)) {
                resultat.add(eleve);
            }
        }
        return resultat;
    }

    private void rafraichirTable() {
        table.setItems(FXCollections.observableArrayList(filtrerEleves()));
    }

}
