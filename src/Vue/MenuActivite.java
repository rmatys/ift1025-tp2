package Vue;

import Modele.Activite;
import Modele.AutoEcole;
import Modele.Eleve;
import Modele.OperationInvalideException;
import Modele.PlageHoraire;
import Modele.StatutActivite;
import Modele.TypeActivite;
import Modele.Voiture;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import Autre.Util;

public class MenuActivite extends BorderPane {
    private static final String AUCUN_VEHICULE = "Aucun véhicule";

    private final AutoEcole autoEcole;
    private TableView<Activite> table;
    private ComboBox<Eleve> champEleve;
    private ComboBox<TypeActivite> champType;
    private DatePicker champDate;
    private TextField champHeure, champDuree;
    private ComboBox<String> champVehicule;
    private ComboBox<StatutActivite> champStatut;
    private Activite activiteSelectionnee;

    public MenuActivite(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;

        // -- construction de l'interface --
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        creerColonnes();

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

        champType = new ComboBox<>(FXCollections.observableArrayList(TypeActivite.values()));
        champType.setPromptText("Sélectionnez un type");

        champDate = new DatePicker(LocalDate.now());

        champHeure = new TextField();
        champHeure.setPromptText("Ex : 14:30");

        champDuree = new TextField();
        champDuree.setPromptText("Ex : 60");

        ArrayList<String> vehicules = new ArrayList<>();
        vehicules.add(AUCUN_VEHICULE);
        for (Voiture voiture : autoEcole.getVoitures()) vehicules.add(voiture.getPlaque());
        champVehicule = new ComboBox<>(FXCollections.observableArrayList(vehicules));
        champVehicule.setValue(AUCUN_VEHICULE);

        champStatut = new ComboBox<>(FXCollections.observableArrayList(StatutActivite.values()));
        champStatut.setValue(StatutActivite.NC);

        Button btnPlanifier = Util.creerBoutonMenu("Planifier");
        Button btnMettreAJourStatut = Util.creerBoutonMenu("Mettre à jour le statut");
        Button btnDetails = Util.creerBoutonMenu("Afficher les détails");
        Button btnAnnuler = Util.creerBoutonMenu("Annuler l'activité");
        Button btnRetour = Util.creerBoutonMenu("Retour au menu principal");

        Label titreFormulaire = new Label("Fiche activité");
        titreFormulaire.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        VBox formulaire = new VBox(10, titreFormulaire,
                                    new Separator(),
                                    new Label("Élève :"), champEleve,
                                    new Label("Type :"), champType,
                                    new Label("Date :"), champDate,
                                    new Label("Heure de début :"), champHeure,
                                    new Label("Durée (minutes) :"), champDuree,
                                    new Label("Véhicule :"), champVehicule,
                                    new Separator(),
                                    btnPlanifier,
                                    new Separator(),
                                    new Label("Statut :"), champStatut,
                                    btnMettreAJourStatut,
                                    new Separator(),
                                    btnDetails, btnAnnuler, btnRetour);
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
            activiteSelectionnee = nouvelle;
            if (nouvelle != null) remplirFormulaire(nouvelle);
        });

        // -- gestion des événements (appelle le modèle directement) --
        btnPlanifier.setOnAction(e -> planifierActivite());
        btnMettreAJourStatut.setOnAction(e -> mettreAJourStatut());
        btnDetails.setOnAction(e -> afficherDetails());
        btnAnnuler.setOnAction(e -> annulerActivite());
        btnRetour.setOnAction(e -> conteneur.setCenter(new MenuPrincipal(autoEcole, conteneur)));
    }

    private void creerColonnes() {
        TableColumn<Activite, Integer> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Activite, Eleve> colEleve = new TableColumn<>("Élève");
        colEleve.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEleve()));
        colEleve.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Eleve item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getPrenom() + " " + item.getNom());
            }
        });

        TableColumn<Activite, TypeActivite> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colType.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(TypeActivite item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getLibelle());
            }
        });

        TableColumn<Activite, LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPlageHoraire().getDate()));

        TableColumn<Activite, LocalTime> colHeureDebut = new TableColumn<>("Heure début");
        colHeureDebut.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPlageHoraire().getHeureDebut()));

        TableColumn<Activite, LocalTime> colHeureFin = new TableColumn<>("Heure fin");
        colHeureFin.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPlageHoraire().getHeureFin()));

        TableColumn<Activite, Integer> colDuree = new TableColumn<>("Durée (min)");
        colDuree.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPlageHoraire().getDuree()));

        TableColumn<Activite, String> colVehicule = new TableColumn<>("Véhicule");
        colVehicule.setCellValueFactory(new PropertyValueFactory<>("plaque"));
        colVehicule.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null || item.isBlank() ? AUCUN_VEHICULE : item);
            }
        });

        TableColumn<Activite, StatutActivite> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colStatut.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(StatutActivite item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getLibelle());
            }
        });

        TableColumn<Activite, Double> colMontant = new TableColumn<>("Montant");
        colMontant.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMontant()));
        colMontant.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : String.format("%.2f $", item));
            }
        });

        table.getColumns().addAll(colId, colEleve, colType, colDate, colHeureDebut, colHeureFin,
                                   colDuree, colVehicule, colStatut, colMontant);
    }

    private void planifierActivite() {
        try {
            if (champEleve.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez un élève.");
            }
            if (champType.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez un type d'activité.");
            }
            if (champDate.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez une date.");
            }
            if (champHeure.getText().isBlank() || champDuree.getText().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            LocalTime heureDebut;
            try {
                heureDebut = LocalTime.parse(champHeure.getText().trim(), DateTimeFormatter.ofPattern("H:mm"));
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("L'heure doit être au format H:mm (ex : 14:30).");
            }

            int duree = Integer.parseInt(champDuree.getText().trim());
            String plaque = champVehicule.getValue() == null || champVehicule.getValue().equals(AUCUN_VEHICULE)
                    ? "" : champVehicule.getValue();

            PlageHoraire horaire = new PlageHoraire(champDate.getValue(), heureDebut, duree);
            autoEcole.creerActivite(horaire, champEleve.getValue().getNumSAAQ(), plaque,
                                     champType.getValue(), StatutActivite.NC);
            rafraichirTable();
            viderFormulaire();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "La durée doit être un nombre valide.").showAndWait();
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void mettreAJourStatut() {
        if (activiteSelectionnee == null) {
            new Alert(Alert.AlertType.WARNING, "Sélectionnez une activité dans la liste.").showAndWait();
            return;
        }

        StatutActivite nouveauStatut = champStatut.getValue();
        if (nouveauStatut == StatutActivite.C) {
            autoEcole.completerActivite(activiteSelectionnee.getId());
        } else {
            activiteSelectionnee.setStatut(nouveauStatut);
            autoEcole.sauvegarderActivites();
        }
        rafraichirTable();
        viderFormulaire();
    }

    private void annulerActivite() {
        if (activiteSelectionnee == null) {
            new Alert(Alert.AlertType.WARNING, "Sélectionnez une activité dans la liste.").showAndWait();
            return;
        }

        autoEcole.annulerActivite(activiteSelectionnee.getId());
        rafraichirTable();
        viderFormulaire();
    }

    private void afficherDetails() {
        if (activiteSelectionnee == null) {
            new Alert(Alert.AlertType.WARNING, "Sélectionnez une activité dans la liste.").showAndWait();
            return;
        }

        Activite a = activiteSelectionnee;
        String details = "Élève : " + a.getEleve().getPrenom() + " " + a.getEleve().getNom()
                + " (NumSAAQ " + a.getEleve().getNumSAAQ() + ")\n"
                + "Type : " + a.getType().getLibelle() + "\n"
                + "Date : " + a.getPlageHoraire().getDate() + "\n"
                + "Heure : " + a.getPlageHoraire().getHeureDebut() + " - " + a.getPlageHoraire().getHeureFin() + "\n"
                + "Durée : " + a.getPlageHoraire().getDuree() + " minutes\n"
                + "Véhicule : " + (a.getPlaque().isBlank() ? AUCUN_VEHICULE : a.getPlaque()) + "\n"
                + "Statut : " + a.getStatut().getLibelle() + "\n"
                + "Montant : " + String.format("%.2f $", a.getMontant());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, details);
        alert.setHeaderText("Détails de l'activité #" + a.getId());
        alert.showAndWait();
    }

    private void remplirFormulaire(Activite activite) {
        champEleve.setValue(activite.getEleve());
        champType.setValue(activite.getType());
        champDate.setValue(activite.getPlageHoraire().getDate());
        champHeure.setText(activite.getPlageHoraire().getHeureDebut().toString());
        champDuree.setText(String.valueOf(activite.getPlageHoraire().getDuree()));
        champVehicule.setValue(activite.getPlaque().isBlank() ? AUCUN_VEHICULE : activite.getPlaque());
        champStatut.setValue(activite.getStatut());
    }

    private void viderFormulaire() {
        activiteSelectionnee = null;
        table.getSelectionModel().clearSelection();
        champEleve.setValue(null);
        champType.setValue(null);
        champDate.setValue(LocalDate.now());
        champHeure.clear();
        champDuree.clear();
        champVehicule.setValue(AUCUN_VEHICULE);
        champStatut.setValue(StatutActivite.NC);
    }

    private void rafraichirTable() {
        table.setItems(FXCollections.observableArrayList(autoEcole.getActivites()));
    }

}
