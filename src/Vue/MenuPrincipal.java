package Vue;

import Autre.Util;
import Modele.AutoEcole;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuPrincipal extends VBox {

    public MenuPrincipal(AutoEcole autoEcole, BorderPane conteneur) {
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

        Button btnEleves = Util.creerBoutonMenu("Gestion des élèves");
        Button btnActivites = Util.creerBoutonMenu("Gestion des activités");
        Button btnVoitures = Util.creerBoutonMenu("Gestion des véhicules");
        Button btnPaiements = Util.creerBoutonMenu("Gestion des paiements");
        Button btnDepenses = Util.creerBoutonMenu("Gestion des dépenses");
        Button btnRapports = Util.creerBoutonMenu("Rapports");

        btnEleves.setOnAction(e -> conteneur.setCenter(new MenuEleve(autoEcole, conteneur)));
        // btnActivites.setOnAction(e -> conteneur.setCenter(new MenuActivite(autoEcole, conteneur)));
        // btnVoitures.setOnAction(e -> conteneur.setCenter(new MenuVoiture(autoEcole, conteneur)));
        // btnPaiements.setOnAction(e -> conteneur.setCenter(new MenuPaiement(autoEcole, conteneur)));
        // btnDepenses.setOnAction(e -> conteneur.setCenter(new MenuDepense(autoEcole, conteneur)));
        // btnRapports.setOnAction(e -> conteneur.setCenter(new MenuRapport(autoEcole, conteneur)));

        getChildren().addAll(entete, btnEleves, btnActivites, btnVoitures,
                              btnPaiements, btnDepenses, btnRapports);
    }
}