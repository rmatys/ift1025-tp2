package Controleur;

import Modele.AutoEcole;
import Modele.OperationInvalideException;
import Modele.Voiture;
import Vue.MenuVehicule;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class ControleurVehicule {
    private final AutoEcole autoEcole;
    private final BorderPane conteneur;
    private final MenuVehicule vue;

    public ControleurVehicule(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;
        this.conteneur = conteneur;
        this.vue = new MenuVehicule();

        vue.setOnAjouter(this::ajouterVoiture);
        vue.setOnModifier(this::modifierVoiture);
        vue.setOnSupprimer(this::supprimerVoiture);
        vue.setOnRetour(this::retourMenuPrincipal);

        rafraichirTable();
    }

    public Region getVue() { return vue; }

    private void ajouterVoiture() {
        try {
            if (vue.getTextePlaque().isBlank() || vue.getTexteMarque().isBlank()
                    || vue.getTexteAnnee().isBlank() || vue.getTextePrix().isBlank()
                    || vue.getTexteKmAchat().isBlank() || vue.getTexteKm().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            int annee = Integer.parseInt(vue.getTexteAnnee().trim());
            double prix = Double.parseDouble(vue.getTextePrix().trim());
            int kmAchat = Integer.parseInt(vue.getTexteKmAchat().trim());
            int km = Integer.parseInt(vue.getTexteKm().trim());

            autoEcole.creerVoiture(vue.getTexteMarque(), vue.getTextePlaque(), annee, prix,
                                    kmAchat, vue.getEtatChoisi(), km);
            rafraichirTable();
            vue.viderFormulaire();
        } catch (NumberFormatException ex) {
            vue.afficherErreur("L'année, le prix et les kilométrages doivent être des nombres valides.");
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void modifierVoiture() {
        try {
            Voiture selectionnee = vue.getVoitureSelectionnee();
            if (selectionnee == null) {
                throw new IllegalArgumentException("Sélectionnez un véhicule à modifier dans la liste.");
            }

            if (vue.getTexteMarque().isBlank() || vue.getTexteAnnee().isBlank()
                    || vue.getTextePrix().isBlank() || vue.getTexteKmAchat().isBlank()
                    || vue.getTexteKm().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            int annee = Integer.parseInt(vue.getTexteAnnee().trim());
            double prix = Double.parseDouble(vue.getTextePrix().trim());
            int kmAchat = Integer.parseInt(vue.getTexteKmAchat().trim());
            int km = Integer.parseInt(vue.getTexteKm().trim());

            autoEcole.modifierVoiture(selectionnee.getPlaque(), vue.getTexteMarque(),
                                       annee, prix, kmAchat, vue.getEtatChoisi(), km);
            rafraichirTable();
            vue.viderFormulaire();
        } catch (NumberFormatException ex) {
            vue.afficherErreur("L'année, le prix et les kilométrages doivent être des nombres valides.");
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void supprimerVoiture() {
        Voiture selectionnee = vue.getVoitureSelectionnee();
        if (selectionnee != null) {
            autoEcole.supprimerVoiture(selectionnee.getPlaque());
            rafraichirTable();
            vue.viderFormulaire();
        }
    }

    private void rafraichirTable() {
        vue.afficherVoitures(autoEcole.getVoitures());
    }

    private void retourMenuPrincipal() {
        conteneur.setCenter(new ControleurPrincipal(autoEcole, conteneur).getVue());
    }
}
