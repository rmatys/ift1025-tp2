package Vue;

import Modele.Activite;
import Modele.AutoEcole;
import Modele.Eleve;
import Modele.MethodePaiement;
import Modele.OperationInvalideException;
import Modele.Paiement;
import Modele.StatutPaiement;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;

import Autre.Util;

public class MenuPaiement extends BorderPane {
    private final AutoEcole autoEcole;
    private TableView<Paiement> table;
    private TextField champRecherche;
    private ComboBox<Eleve> champEleve;
    private ComboBox<Activite> champActivite;
    private ComboBox<MethodePaiement> champMethode;
    private ComboBox<StatutPaiement> champStatut;
    private DatePicker champDate;
    private TextField champMontantRestant;
    private Paiement paiementSelectionne;

    public MenuPaiement(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;

        // -- construction de l'interface --
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        creerColonnes();

        champRecherche = new TextField();
        champRecherche.setPromptText("Rechercher par Numéro SAAQ, nom ou prénom de l'élève");

        VBox zoneTable = new VBox(10, champRecherche, table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        champEleve = new ComboBox<>(FXCollections.observableArrayList(autoEcole.getEleves()));
        champEleve.setPromptText("Sélectionnez un élève");
        champEleve.setConverter(new StringConverter<>() {
            @Override
            public String toString(Eleve eleve) {
                return eleve == null ? "" : eleve.getPrenom() + " " + eleve.getNom() + " (" + eleve.getNumSAAQ() + ")";
            }

            @Override
            public Eleve fromString(String s) { return null; }
        });

        champActivite = new ComboBox<>();
        champActivite.setPromptText("Sélectionnez une activité");
        champActivite.setConverter(new StringConverter<>() {
            @Override
            public String toString(Activite activite) {
                return activite == null ? "" : activite.getType().getLibelle() + " du " + activite.getPlageHoraire().getDate();
            }

            @Override
            public Activite fromString(String s) { return null; }
        });

        champMethode = new ComboBox<>(FXCollections.observableArrayList(MethodePaiement.values()));
        champMethode.setPromptText("Sélectionnez une méthode");

        champStatut = new ComboBox<>(FXCollections.observableArrayList(StatutPaiement.values()));
        champStatut.setValue(StatutPaiement.P);

        champDate = new DatePicker(LocalDate.now());

        champMontantRestant = new TextField();
        champMontantRestant.setPromptText("Ex : 50.00");
        champMontantRestant.setDisable(true);

        Button btnEnregistrer = Util.creerBoutonMenu("Enregistrer le paiement");
        Button btnMettreAJourStatut = Util.creerBoutonMenu("Mettre à jour le statut");
        Button btnDetails = Util.creerBoutonMenu("Afficher les détails");
        Button btnRetour = Util.creerBoutonMenu("Retour au menu principal");

        Label titreFormulaire = new Label("Fiche paiement");
        titreFormulaire.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        VBox formulaire = new VBox(10, titreFormulaire,
                                    new Separator(),
                                    new Label("Élève :"), champEleve,
                                    new Label("Activité :"), champActivite,
                                    new Label("Méthode de paiement :"), champMethode,
                                    new Label("Date :"), champDate,
                                    new Separator(),
                                    new Label("Statut :"), champStatut,
                                    new Label("Montant restant (si partiel) :"), champMontantRestant,
                                    new Separator(),
                                    btnEnregistrer, btnMettreAJourStatut,
                                    new Separator(),
                                    btnDetails, btnRetour);
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

        table.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouveau) -> {
            paiementSelectionne = nouveau;
            if (nouveau != null) remplirFormulaire(nouveau);
        });

        champRecherche.textProperty().addListener((obs, ancien, nouveau) -> rafraichirTable());

        champEleve.valueProperty().addListener((obs, ancien, nouveau) -> mettreAJourActivites(nouveau));

        champStatut.valueProperty().addListener((obs, ancien, nouveau) ->
                champMontantRestant.setDisable(nouveau != StatutPaiement.PP));

        // -- gestion des événements (appelle le modèle directement) --
        btnEnregistrer.setOnAction(e -> enregistrerPaiement());
        btnMettreAJourStatut.setOnAction(e -> mettreAJourStatut());
        btnDetails.setOnAction(e -> afficherDetails());
        btnRetour.setOnAction(e -> conteneur.setCenter(new MenuPrincipal(autoEcole, conteneur)));
    }

    private void creerColonnes() {
        TableColumn<Paiement, String> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Paiement, Eleve> colEleve = new TableColumn<>("Élève");
        colEleve.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEleve()));
        colEleve.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Eleve item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getPrenom() + " " + item.getNom());
            }
        });

        TableColumn<Paiement, String> colActivite = new TableColumn<>("Activité");
        colActivite.setCellValueFactory(data -> new SimpleObjectProperty<>(
                data.getValue().getTypeActivite().getLibelle() + " du " + data.getValue().getActivite().getPlageHoraire().getDate()));

        TableColumn<Paiement, Double> colMontant = new TableColumn<>("Montant");
        colMontant.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMontant()));
        colMontant.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : String.format("%.2f $", item));
            }
        });

        TableColumn<Paiement, Double> colMontantRestant = new TableColumn<>("Montant restant");
        colMontantRestant.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMontantRestant()));
        colMontantRestant.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : String.format("%.2f $", item));
            }
        });

        TableColumn<Paiement, MethodePaiement> colMethode = new TableColumn<>("Méthode");
        colMethode.setCellValueFactory(new PropertyValueFactory<>("methodePaiement"));
        colMethode.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(MethodePaiement item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getLibelle());
            }
        });

        TableColumn<Paiement, LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Paiement, StatutPaiement> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statutPaiement"));
        colStatut.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(StatutPaiement item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                    setStyle("");
                    return;
                }
                setText(item.getLibelle());
                setStyle(switch (item) {
                    case P -> "-fx-text-fill: #27ae60; -fx-font-weight: bold;";
                    case I -> "-fx-text-fill: #e74c3c; -fx-font-weight: bold;";
                    case PP -> "-fx-text-fill: #f39c12; -fx-font-weight: bold;";
                });
            }
        });

        table.getColumns().addAll(colId, colEleve, colActivite, colMontant, colMontantRestant,
                                   colMethode, colDate, colStatut);
    }

    private void enregistrerPaiement() {
        try {
            if (champEleve.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez un élève.");
            }
            if (champActivite.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez une activité.");
            }
            if (champMethode.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez une méthode de paiement.");
            }
            if (champStatut.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez un statut.");
            }
            if (champDate.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez une date.");
            }

            Paiement paiement = new Paiement(autoEcole.prochainNumeroPaiement(), champDate.getValue(),
                    champStatut.getValue(), champActivite.getValue(), champMethode.getValue(), champEleve.getValue());
            autoEcole.ajouterPaiement(paiement);
            rafraichirTable();
            viderFormulaire();
        } catch (IllegalArgumentException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void mettreAJourStatut() {
        if (paiementSelectionne == null) {
            new Alert(Alert.AlertType.WARNING, "Sélectionnez un paiement dans la liste.").showAndWait();
            return;
        }

        try {
            double montantRestant = 0;
            if (champStatut.getValue() == StatutPaiement.PP) {
                if (champMontantRestant.getText().isBlank()) {
                    throw new IllegalArgumentException("Le montant restant est obligatoire pour un paiement partiel.");
                }
                montantRestant = Double.parseDouble(champMontantRestant.getText().trim());
            }

            autoEcole.changerEtatPaiement(paiementSelectionne.getId(), champStatut.getValue(), montantRestant);
            rafraichirTable();
            viderFormulaire();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Le montant restant doit être un nombre valide.").showAndWait();
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void afficherDetails() {
        if (paiementSelectionne == null) {
            new Alert(Alert.AlertType.WARNING, "Sélectionnez un paiement dans la liste.").showAndWait();
            return;
        }

        Paiement p = paiementSelectionne;
        String details = "Élève : " + p.getEleve().getPrenom() + " " + p.getEleve().getNom()
                + " (NumSAAQ " + p.getEleve().getNumSAAQ() + ")\n"
                + "Activité : " + p.getTypeActivite().getLibelle() + " du " + p.getActivite().getPlageHoraire().getDate() + "\n"
                + "Montant : " + String.format("%.2f $", p.getMontant()) + "\n"
                + "Montant restant : " + String.format("%.2f $", p.getMontantRestant()) + "\n"
                + "Méthode de paiement : " + p.getMethodePaiement().getLibelle() + "\n"
                + "Date : " + p.getDate() + "\n"
                + "Statut : " + p.getStatutPaiement().getLibelle();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, details);
        alert.setHeaderText("Détails du paiement " + p.getId());
        alert.showAndWait();
    }

    private void remplirFormulaire(Paiement paiement) {
        champEleve.setValue(paiement.getEleve());
        champEleve.setDisable(true);
        champActivite.setValue(paiement.getActivite());
        champActivite.setDisable(true);
        champMethode.setValue(paiement.getMethodePaiement());
        champMethode.setDisable(true);
        champDate.setValue(paiement.getDate());
        champDate.setDisable(true);
        champStatut.setValue(paiement.getStatutPaiement());
        champMontantRestant.setText(String.valueOf(paiement.getMontantRestant()));
    }

    private void viderFormulaire() {
        paiementSelectionne = null;
        table.getSelectionModel().clearSelection();
        champEleve.setValue(null);
        champEleve.setDisable(false);
        champActivite.setValue(null);
        champActivite.setDisable(false);
        champMethode.setValue(null);
        champMethode.setDisable(false);
        champDate.setValue(LocalDate.now());
        champDate.setDisable(false);
        champStatut.setValue(StatutPaiement.P);
        champMontantRestant.clear();
    }

    private void mettreAJourActivites(Eleve eleve) {
        champActivite.setItems(FXCollections.observableArrayList(
                eleve == null ? new ArrayList<>() : autoEcole.getActivitesEleve(eleve)));
    }

    private ArrayList<Paiement> filtrerPaiements() {
        String texte = champRecherche.getText() == null ? "" : champRecherche.getText().trim().toLowerCase();
        ArrayList<Paiement> resultat = new ArrayList<>();
        for (Paiement paiement : autoEcole.getPaiements()) {
            Eleve eleve = paiement.getEleve();
            if (texte.isEmpty()
                    || String.valueOf(eleve.getNumSAAQ()).contains(texte)
                    || eleve.getNom().toLowerCase().contains(texte)
                    || eleve.getPrenom().toLowerCase().contains(texte)) {
                resultat.add(paiement);
            }
        }
        return resultat;
    }

    private void rafraichirTable() {
        table.setItems(FXCollections.observableArrayList(filtrerPaiements()));
    }

}
