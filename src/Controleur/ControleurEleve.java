package Controleur;

import Modele.AutoEcole;
import Modele.Eleve;
import Vue.MenuEleve;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.time.LocalDate;

public class ControleurEleve {
    private final AutoEcole autoEcole;
    private final BorderPane conteneur;
    private final MenuEleve vue;

    public ControleurEleve(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;
        this.conteneur = conteneur;
        this.vue = new MenuEleve();

        vue.setOnAjouter(this::ajouterEleve);
        vue.setOnModifier(this::modifierEleve);
        vue.setOnSupprimer(this::supprimerEleve);
        vue.setOnRecherche(texte -> rafraichirTable());
        vue.setOnRetour(this::retourMenuPrincipal);

        rafraichirTable();
    }

    public Region getVue() { return vue; }

    private void ajouterEleve() {
        try {
            if (vue.getTexteNumSAAQ().isBlank() || vue.getTexteNom().isBlank()
                    || vue.getTextePrenom().isBlank() || vue.getTexteAdresse().isBlank()
                    || vue.getTexteTelephone().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            long numSAAQ = Long.parseLong(vue.getTexteNumSAAQ().trim());
            Eleve e = new Eleve(numSAAQ, vue.getTextePrenom(), vue.getTexteNom(),
                                 vue.getTexteAdresse(), vue.getTexteTelephone(), LocalDate.now());
            autoEcole.ajouterEleve(e);
            rafraichirTable();
            vue.viderFormulaire();
        } catch (NumberFormatException ex) {
            vue.afficherErreur("Le NumSAAQ doit être un nombre valide.");
        } catch (IllegalArgumentException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void modifierEleve() {
        try {
            Eleve selectionne = vue.getEleveSelectionne();
            if (selectionne == null) {
                throw new IllegalArgumentException("Sélectionnez un élève à modifier dans la liste.");
            }

            if (vue.getTexteNom().isBlank() || vue.getTextePrenom().isBlank()
                    || vue.getTexteAdresse().isBlank() || vue.getTexteTelephone().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            autoEcole.modifierEleve(selectionne.getNumSAAQ(), vue.getTextePrenom(),
                                     vue.getTexteNom(), vue.getTexteAdresse(), vue.getTexteTelephone());
            rafraichirTable();
            vue.viderFormulaire();
        } catch (IllegalArgumentException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void supprimerEleve() {
        Eleve selectionne = vue.getEleveSelectionne();
        if (selectionne != null) {
            autoEcole.supprimerEleve(selectionne.getNumSAAQ());
            rafraichirTable();
            vue.viderFormulaire();
        }
    }

    private void rafraichirTable() {
        vue.afficherEleves(autoEcole.rechercherEleves(vue.getTexteRecherche()));
    }

    private void retourMenuPrincipal() {
        conteneur.setCenter(new ControleurPrincipal(autoEcole, conteneur).getVue());
    }
}
