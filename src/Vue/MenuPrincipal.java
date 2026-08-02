package Vue;

import Autre.Util;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuPrincipal extends VBox {
    private Button btnEleves, btnActivites, btnVoitures, btnPaiements, btnDepenses, btnRapports;

    public MenuPrincipal() {
        setSpacing(15);
        setPadding(new Insets(40));
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: #f4f6f8;");

        Label titre = new Label("Auto-École");
        titre.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label sousTitre = new Label("Menu principal");
        sousTitre.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        VBox entete = new VBox(4, titre, sousTitre);
        entete.setAlignment(Pos.CENTER);
        entete.setPadding(new Insets(0, 0, 25, 0));

        btnEleves = Util.creerBoutonMenu("Gestion des élèves");
        btnActivites = Util.creerBoutonMenu("Gestion des activités");
        btnVoitures = Util.creerBoutonMenu("Gestion des véhicules");
        btnPaiements = Util.creerBoutonMenu("Gestion des paiements");
        btnDepenses = Util.creerBoutonMenu("Gestion des dépenses");
        btnRapports = Util.creerBoutonMenu("Génération de rapports");

        getChildren().addAll(entete, btnEleves, btnActivites, btnVoitures,
                              btnPaiements, btnDepenses, btnRapports);
    }

    public void setOnEleves(Runnable action) { btnEleves.setOnAction(e -> action.run()); }
    public void setOnActivites(Runnable action) { btnActivites.setOnAction(e -> action.run()); }
    public void setOnVoitures(Runnable action) { btnVoitures.setOnAction(e -> action.run()); }
    public void setOnPaiements(Runnable action) { btnPaiements.setOnAction(e -> action.run()); }
    public void setOnDepenses(Runnable action) { btnDepenses.setOnAction(e -> action.run()); }
    public void setOnRapports(Runnable action) { btnRapports.setOnAction(e -> action.run()); }
}
