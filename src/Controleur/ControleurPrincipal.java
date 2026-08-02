package Controleur;

import Modele.AutoEcole;
import Vue.MenuPrincipal;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class ControleurPrincipal {
    private final MenuPrincipal vue;

    public ControleurPrincipal(AutoEcole autoEcole, BorderPane conteneur) {
        this.vue = new MenuPrincipal();

        vue.setOnEleves(() -> conteneur.setCenter(new ControleurEleve(autoEcole, conteneur).getVue()));
        vue.setOnActivites(() -> conteneur.setCenter(new ControleurActivite(autoEcole, conteneur).getVue()));
        vue.setOnVoitures(() -> conteneur.setCenter(new ControleurVehicule(autoEcole, conteneur).getVue()));
        vue.setOnPaiements(() -> conteneur.setCenter(new ControleurPaiement(autoEcole, conteneur).getVue()));
        vue.setOnDepenses(() -> conteneur.setCenter(new ControleurDepense(autoEcole, conteneur).getVue()));
        vue.setOnRapports(() -> conteneur.setCenter(new ControleurRapport(autoEcole, conteneur).getVue()));
    }

    public Region getVue() { return vue; }
}
