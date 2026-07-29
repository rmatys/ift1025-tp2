package Vue;

import Modele.AutoEcole;
import Modele.AutreDepense;
import Modele.DepenseVoiture;
import Modele.OperationInvalideException;
import Modele.TypeAutreDepense;
import Modele.TypeDepenseVoiture;
import Modele.Voiture;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

public class MenuDepense extends BorderPane {
    private static final String TOUTES_CATEGORIES = "Toutes les catégories";

    private final AutoEcole autoEcole;

    // -- onglet "Dépenses véhicule" --
    private TableView<DepenseVoiture> tableDepensesVoiture;
    private ComboBox<String> champPlaque;
    private DatePicker champDateVoiture;
    private ComboBox<TypeDepenseVoiture> champCategorieVoiture;
    private TextField champDescriptionVoiture, champMontantVoiture;
    private ComboBox<String> champFiltreVoiture;

    // -- onglet "Autres dépenses" --
    private TableView<AutreDepense> tableAutresDepenses;
    private DatePicker champDateAutre;
    private ComboBox<TypeAutreDepense> champCategorieAutre;
    private TextField champDescriptionAutre, champMontantAutre;
    private ComboBox<String> champFiltreAutre;

    public MenuDepense(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;

        // -- construction de l'interface --
        TabPane onglets = new TabPane();
        onglets.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        onglets.getTabs().addAll(
                new Tab("Dépenses véhicule", creerOngletDepenseVoiture()),
                new Tab("Autres dépenses", creerOngletAutreDepense()));

        Button btnRetour = Util.creerBoutonMenu("Retour au menu principal");
        VBox pied = new VBox(btnRetour);
        pied.setAlignment(Pos.CENTER);
        pied.setPadding(new Insets(15));

        setStyle("-fx-background-color: #f4f6f8;");
        setCenter(onglets);
        setBottom(pied);

        rafraichirTableDepensesVoiture();
        rafraichirTableAutresDepenses();

        // -- gestion des événements (appelle le modèle directement) --
        btnRetour.setOnAction(e -> conteneur.setCenter(new MenuPrincipal(autoEcole, conteneur)));
    }

    // ------------------------------------------------------------------
    // Onglet "Dépenses véhicule"
    // ------------------------------------------------------------------

    private BorderPane creerOngletDepenseVoiture() {
        BorderPane onglet = new BorderPane();
        onglet.setStyle("-fx-background-color: #f4f6f8;");

        tableDepensesVoiture = new TableView<>();
        tableDepensesVoiture.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        creerColonnesDepensesVoiture();

        ArrayList<String> plaques = new ArrayList<>();
        for (Voiture voiture : autoEcole.getVoitures()) plaques.add(voiture.getPlaque());
        champPlaque = new ComboBox<>(FXCollections.observableArrayList(plaques));
        champPlaque.setPromptText("Sélectionnez un véhicule");

        champDateVoiture = new DatePicker(LocalDate.now());
        champCategorieVoiture = new ComboBox<>(FXCollections.observableArrayList(TypeDepenseVoiture.values()));
        champCategorieVoiture.setPromptText("Sélectionnez une catégorie");

        champDescriptionVoiture = new TextField();
        champDescriptionVoiture.setPromptText("Ex : changement d'huile");
        champMontantVoiture = new TextField();
        champMontantVoiture.setPromptText("Ex : 150.00");

        Button btnAjouterVoiture = Util.creerBoutonMenu("Ajouter la dépense");

        Label titreFormulaire = new Label("Dépense liée à un véhicule");
        titreFormulaire.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        champFiltreVoiture = new ComboBox<>(FXCollections.observableArrayList(TOUTES_CATEGORIES,
                TypeDepenseVoiture.R.getLibelle(), TypeDepenseVoiture.E.getLibelle(), TypeDepenseVoiture.C.getLibelle()));
        champFiltreVoiture.setValue(TOUTES_CATEGORIES);

        VBox formulaire = new VBox(10, titreFormulaire,
                                    new Separator(),
                                    new Label("Véhicule :"), champPlaque,
                                    new Label("Date :"), champDateVoiture,
                                    new Label("Catégorie :"), champCategorieVoiture,
                                    new Label("Description :"), champDescriptionVoiture,
                                    new Label("Montant :"), champMontantVoiture,
                                    new Separator(),
                                    btnAjouterVoiture,
                                    new Separator(),
                                    new Label("Consulter par catégorie :"), champFiltreVoiture);
        formulaire.setPadding(new Insets(20));
        formulaire.setPrefWidth(280);
        formulaire.setStyle("-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-border-color: #dcdde1; -fx-border-radius: 10;");

        BorderPane.setMargin(formulaire, new Insets(15));
        BorderPane.setMargin(tableDepensesVoiture, new Insets(15, 0, 15, 15));

        onglet.setCenter(tableDepensesVoiture);
        onglet.setRight(formulaire);

        btnAjouterVoiture.setOnAction(e -> ajouterDepenseVoiture());
        champFiltreVoiture.valueProperty().addListener((obs, ancienne, nouvelle) -> rafraichirTableDepensesVoiture());

        return onglet;
    }

    private void creerColonnesDepensesVoiture() {
        TableColumn<DepenseVoiture, Integer> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<DepenseVoiture, String> colPlaque = new TableColumn<>("Véhicule");
        colPlaque.setCellValueFactory(new PropertyValueFactory<>("plaque"));

        TableColumn<DepenseVoiture, LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.toString());
            }
        });

        TableColumn<DepenseVoiture, TypeDepenseVoiture> colCategorie = new TableColumn<>("Catégorie");
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colCategorie.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(TypeDepenseVoiture item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getLibelle());
            }
        });

        TableColumn<DepenseVoiture, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<DepenseVoiture, Double> colMontant = new TableColumn<>("Montant");
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));

        tableDepensesVoiture.getColumns().addAll(colId, colPlaque, colDate, colCategorie, colDescription, colMontant);
    }

    private void ajouterDepenseVoiture() {
        try {
            if (champPlaque.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez un véhicule.");
            }
            if (champDateVoiture.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez une date.");
            }
            if (champCategorieVoiture.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez une catégorie.");
            }
            if (champDescriptionVoiture.getText().isBlank() || champMontantVoiture.getText().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            double montant = Double.parseDouble(champMontantVoiture.getText().trim());

            autoEcole.creerDepenseVoiture(champPlaque.getValue(), champDateVoiture.getValue(),
                                           champCategorieVoiture.getValue(), champDescriptionVoiture.getText(), montant);
            rafraichirTableDepensesVoiture();
            viderFormulaireVoiture();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Le montant doit être un nombre valide.").showAndWait();
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void viderFormulaireVoiture() {
        champPlaque.setValue(null);
        champDateVoiture.setValue(LocalDate.now());
        champCategorieVoiture.setValue(null);
        champDescriptionVoiture.clear();
        champMontantVoiture.clear();
    }

    private void rafraichirTableDepensesVoiture() {
        String filtre = champFiltreVoiture == null ? null : champFiltreVoiture.getValue();
        ArrayList<DepenseVoiture> toutes = autoEcole.getDepensesVoiture();

        if (filtre == null || filtre.equals(TOUTES_CATEGORIES)) {
            tableDepensesVoiture.setItems(FXCollections.observableArrayList(toutes));
            return;
        }

        ArrayList<DepenseVoiture> filtrees = new ArrayList<>();
        for (DepenseVoiture depense : toutes) {
            if (depense.getCategorie().getLibelle().equals(filtre)) filtrees.add(depense);
        }
        tableDepensesVoiture.setItems(FXCollections.observableArrayList(filtrees));
    }

    // ------------------------------------------------------------------
    // Onglet "Autres dépenses"
    // ------------------------------------------------------------------

    private BorderPane creerOngletAutreDepense() {
        BorderPane onglet = new BorderPane();
        onglet.setStyle("-fx-background-color: #f4f6f8;");

        tableAutresDepenses = new TableView<>();
        tableAutresDepenses.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        creerColonnesAutresDepenses();

        champDateAutre = new DatePicker(LocalDate.now());
        champCategorieAutre = new ComboBox<>(FXCollections.observableArrayList(TypeAutreDepense.values()));
        champCategorieAutre.setPromptText("Sélectionnez une catégorie");

        champDescriptionAutre = new TextField();
        champDescriptionAutre.setPromptText("Ex : campagne publicitaire");
        champMontantAutre = new TextField();
        champMontantAutre.setPromptText("Ex : 200.00");

        Button btnAjouterAutre = Util.creerBoutonMenu("Ajouter la dépense");

        Label titreFormulaire = new Label("Autre dépense");
        titreFormulaire.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ArrayList<String> categories = new ArrayList<>();
        categories.add(TOUTES_CATEGORIES);
        for (TypeAutreDepense type : TypeAutreDepense.values()) categories.add(type.getLibelle());
        champFiltreAutre = new ComboBox<>(FXCollections.observableArrayList(categories));
        champFiltreAutre.setValue(TOUTES_CATEGORIES);

        VBox formulaire = new VBox(10, titreFormulaire,
                                    new Separator(),
                                    new Label("Date :"), champDateAutre,
                                    new Label("Catégorie :"), champCategorieAutre,
                                    new Label("Description :"), champDescriptionAutre,
                                    new Label("Montant :"), champMontantAutre,
                                    new Separator(),
                                    btnAjouterAutre,
                                    new Separator(),
                                    new Label("Consulter par catégorie :"), champFiltreAutre);
        formulaire.setPadding(new Insets(20));
        formulaire.setPrefWidth(280);
        formulaire.setStyle("-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-border-color: #dcdde1; -fx-border-radius: 10;");

        BorderPane.setMargin(formulaire, new Insets(15));
        BorderPane.setMargin(tableAutresDepenses, new Insets(15, 0, 15, 15));

        onglet.setCenter(tableAutresDepenses);
        onglet.setRight(formulaire);

        btnAjouterAutre.setOnAction(e -> ajouterAutreDepense());
        champFiltreAutre.valueProperty().addListener((obs, ancienne, nouvelle) -> rafraichirTableAutresDepenses());

        return onglet;
    }

    private void creerColonnesAutresDepenses() {
        TableColumn<AutreDepense, Integer> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<AutreDepense, LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.toString());
            }
        });

        TableColumn<AutreDepense, TypeAutreDepense> colCategorie = new TableColumn<>("Catégorie");
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colCategorie.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(TypeAutreDepense item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getLibelle());
            }
        });

        TableColumn<AutreDepense, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<AutreDepense, Double> colMontant = new TableColumn<>("Montant");
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));

        tableAutresDepenses.getColumns().addAll(colId, colDate, colCategorie, colDescription, colMontant);
    }

    private void ajouterAutreDepense() {
        try {
            if (champDateAutre.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez une date.");
            }
            if (champCategorieAutre.getValue() == null) {
                throw new IllegalArgumentException("Sélectionnez une catégorie.");
            }
            if (champDescriptionAutre.getText().isBlank() || champMontantAutre.getText().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            double montant = Double.parseDouble(champMontantAutre.getText().trim());

            autoEcole.creerAutreDepense(champDateAutre.getValue(), champCategorieAutre.getValue(),
                                         champDescriptionAutre.getText(), montant);
            rafraichirTableAutresDepenses();
            viderFormulaireAutre();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Le montant doit être un nombre valide.").showAndWait();
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private void viderFormulaireAutre() {
        champDateAutre.setValue(LocalDate.now());
        champCategorieAutre.setValue(null);
        champDescriptionAutre.clear();
        champMontantAutre.clear();
    }

    private void rafraichirTableAutresDepenses() {
        String filtre = champFiltreAutre == null ? null : champFiltreAutre.getValue();
        ArrayList<AutreDepense> toutes = autoEcole.getAutresDepenses();

        if (filtre == null || filtre.equals(TOUTES_CATEGORIES)) {
            tableAutresDepenses.setItems(FXCollections.observableArrayList(toutes));
            return;
        }

        ArrayList<AutreDepense> filtrees = new ArrayList<>();
        for (AutreDepense depense : toutes) {
            if (depense.getCategorie().getLibelle().equals(filtre)) filtrees.add(depense);
        }
        tableAutresDepenses.setItems(FXCollections.observableArrayList(filtrees));
    }
}
