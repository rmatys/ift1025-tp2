package Vue;

import Modele.Activite;
import Modele.Eleve;
import Modele.StatutActivite;
import Modele.TypeActivite;
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
import java.util.ArrayList;
import java.util.List;

import Autre.Util;

public class MenuActivite extends BorderPane {
    public static final String AUCUN_VEHICULE = "Aucun véhicule";

    private TableView<Activite> table;
    private ComboBox<Eleve> champEleve;
    private ComboBox<TypeActivite> champType;
    private DatePicker champDate;
    private TextField champHeure, champDuree;
    private ComboBox<String> champVehicule;
    private ComboBox<StatutActivite> champStatut;
    private Button btnPlanifier, btnMettreAJourStatut, btnDetails, btnAnnuler, btnRetour;
    private Activite activiteSelectionnee;

    public MenuActivite() {
        // -- construction de l'interface --
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        creerColonnes();

        champEleve = new ComboBox<>();
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

        champVehicule = new ComboBox<>();
        champVehicule.setValue(AUCUN_VEHICULE);

        champStatut = new ComboBox<>(FXCollections.observableArrayList(StatutActivite.values()));
        champStatut.setValue(StatutActivite.NC);

        btnPlanifier = Util.creerBoutonMenu("Planifier");
        btnMettreAJourStatut = Util.creerBoutonMenu("Mettre à jour le statut");
        btnDetails = Util.creerBoutonMenu("Afficher les détails");
        btnAnnuler = Util.creerBoutonMenu("Annuler l'activité");
        btnRetour = Util.creerBoutonMenu("Retour au menu principal");

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

        table.getSelectionModel().selectedItemProperty().addListener((obs, ancienne, nouvelle) -> {
            activiteSelectionnee = nouvelle;
            if (nouvelle != null) remplirFormulaire(nouvelle);
        });
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

    // ---- câblage des événements (le contrôleur s'y abonne) ----
    public void setOnPlanifier(Runnable action) { btnPlanifier.setOnAction(e -> action.run()); }
    public void setOnMettreAJourStatut(Runnable action) { btnMettreAJourStatut.setOnAction(e -> action.run()); }
    public void setOnDetails(Runnable action) { btnDetails.setOnAction(e -> action.run()); }
    public void setOnAnnuler(Runnable action) { btnAnnuler.setOnAction(e -> action.run()); }
    public void setOnRetour(Runnable action) { btnRetour.setOnAction(e -> action.run()); }

    // ---- mise à jour de l'affichage (le contrôleur les appelle) ----
    public void setListeEleves(List<Eleve> eleves) {
        champEleve.setItems(FXCollections.observableArrayList(eleves));
    }

    public void setListeVehicules(List<String> plaques) {
        ArrayList<String> valeurs = new ArrayList<>();
        valeurs.add(AUCUN_VEHICULE);
        valeurs.addAll(plaques);
        champVehicule.setItems(FXCollections.observableArrayList(valeurs));
        champVehicule.setValue(AUCUN_VEHICULE);
    }

    public void afficherActivites(List<Activite> activites) {
        table.setItems(FXCollections.observableArrayList(activites));
        table.refresh();
    }

    public void afficherDetails(String entete, String texte) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, texte);
        alert.setHeaderText(entete);
        alert.showAndWait();
    }

    public void remplirFormulaire(Activite activite) {
        champEleve.setValue(activite.getEleve());
        champType.setValue(activite.getType());
        champDate.setValue(activite.getPlageHoraire().getDate());
        champHeure.setText(activite.getPlageHoraire().getHeureDebut().toString());
        champDuree.setText(String.valueOf(activite.getPlageHoraire().getDuree()));
        champVehicule.setValue(activite.getPlaque().isBlank() ? AUCUN_VEHICULE : activite.getPlaque());
        champStatut.setValue(activite.getStatut());
    }

    public void viderFormulaire() {
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

    public void afficherErreur(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }

    public void afficherAvertissement(String message) {
        new Alert(Alert.AlertType.WARNING, message).showAndWait();
    }

    // ---- lecture de la saisie brute (le contrôleur les lit) ----
    public Eleve getEleveChoisi() { return champEleve.getValue(); }
    public TypeActivite getTypeChoisi() { return champType.getValue(); }
    public LocalDate getDateChoisie() { return champDate.getValue(); }
    public String getTexteHeure() { return champHeure.getText(); }
    public String getTexteDuree() { return champDuree.getText(); }
    public String getVehiculeChoisi() { return champVehicule.getValue(); }
    public StatutActivite getStatutChoisi() { return champStatut.getValue(); }
    public Activite getActiviteSelectionnee() { return activiteSelectionnee; }
}
