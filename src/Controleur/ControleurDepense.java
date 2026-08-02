package Controleur;

import Modele.AutoEcole;
import Modele.OperationInvalideException;
import Modele.Voiture;
import Vue.MenuDepense;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class ControleurDepense {
    private final AutoEcole autoEcole;
    private final BorderPane conteneur;
    private final MenuDepense vue;

    public ControleurDepense(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;
        this.conteneur = conteneur;
        this.vue = new MenuDepense();

        ArrayList<String> plaques = new ArrayList<>();
        for (Voiture voiture : autoEcole.getVoitures()) plaques.add(voiture.getPlaque());
        vue.setListeVehicules(plaques);

        vue.setOnAjouterDepenseVoiture(this::ajouterDepenseVoiture);
        vue.setOnAjouterAutreDepense(this::ajouterAutreDepense);
        vue.setOnFiltreVoitureChange(filtre -> rafraichirTableDepensesVoiture());
        vue.setOnFiltreAutreChange(filtre -> rafraichirTableAutresDepenses());
        vue.setOnRetour(this::retourMenuPrincipal);

        rafraichirTableDepensesVoiture();
        rafraichirTableAutresDepenses();
    }

    public Region getVue() { return vue; }

    private void ajouterDepenseVoiture() {
        try {
            if (vue.getVehiculeChoisi() == null) {
                throw new IllegalArgumentException("Sélectionnez un véhicule.");
            }
            if (vue.getDateVoiture() == null) {
                throw new IllegalArgumentException("Sélectionnez une date.");
            }
            if (vue.getCategorieVoitureChoisie() == null) {
                throw new IllegalArgumentException("Sélectionnez une catégorie.");
            }
            if (vue.getTexteDescriptionVoiture().isBlank() || vue.getTexteMontantVoiture().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            double montant = Double.parseDouble(vue.getTexteMontantVoiture().trim());

            autoEcole.creerDepenseVoiture(vue.getVehiculeChoisi(), vue.getDateVoiture(),
                                           vue.getCategorieVoitureChoisie(), vue.getTexteDescriptionVoiture(), montant);
            rafraichirTableDepensesVoiture();
            vue.viderFormulaireVoiture();
        } catch (NumberFormatException ex) {
            vue.afficherErreur("Le montant doit être un nombre valide.");
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void ajouterAutreDepense() {
        try {
            if (vue.getDateAutre() == null) {
                throw new IllegalArgumentException("Sélectionnez une date.");
            }
            if (vue.getCategorieAutreChoisie() == null) {
                throw new IllegalArgumentException("Sélectionnez une catégorie.");
            }
            if (vue.getTexteDescriptionAutre().isBlank() || vue.getTexteMontantAutre().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            double montant = Double.parseDouble(vue.getTexteMontantAutre().trim());

            autoEcole.creerAutreDepense(vue.getDateAutre(), vue.getCategorieAutreChoisie(),
                                         vue.getTexteDescriptionAutre(), montant);
            rafraichirTableAutresDepenses();
            vue.viderFormulaireAutre();
        } catch (NumberFormatException ex) {
            vue.afficherErreur("Le montant doit être un nombre valide.");
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void rafraichirTableDepensesVoiture() {
        String filtre = vue.getFiltreVoiture();
        String categorie = filtre == null || filtre.equals(MenuDepense.TOUTES_CATEGORIES) ? null : filtre;
        vue.afficherDepensesVoiture(autoEcole.rechercherDepensesVoiture(categorie));
    }

    private void rafraichirTableAutresDepenses() {
        String filtre = vue.getFiltreAutre();
        String categorie = filtre == null || filtre.equals(MenuDepense.TOUTES_CATEGORIES) ? null : filtre;
        vue.afficherAutresDepenses(autoEcole.rechercherAutresDepenses(categorie));
    }

    private void retourMenuPrincipal() {
        conteneur.setCenter(new ControleurPrincipal(autoEcole, conteneur).getVue());
    }
}
