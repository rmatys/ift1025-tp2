package Controleur;

import Modele.AutoEcole;
import Modele.Eleve;
import Vue.EleveView;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import java.time.LocalDate;

public class EleveController {

    private EleveView view;
    private AutoEcole autoEcole;

    public EleveController(EleveView view, AutoEcole autoEcole) {
        this.view = view;
        this.autoEcole = autoEcole;

        rafraichirTable();

        view.getBtnAjouter().setOnAction(e -> ajouter());
        view.getBtnSupprimer().setOnAction(e -> supprimer());
    }

    private void rafraichirTable() {
        view.getTable().setItems(FXCollections.observableArrayList(autoEcole.getEleves()));
    }

    private void ajouter() {
        try {
            long num = Long.parseLong(view.getTxtNum().getText());
            String nom = view.getTxtNom().getText();
            String prenom = view.getTxtPrenom().getText();
            String adresse = view.getTxtAdresse().getText();
            String tel = view.getTxtTel().getText();

            Eleve e = new Eleve(num, prenom, nom, adresse, tel, LocalDate.now());
            autoEcole.ajouterEleve(e);
            rafraichirTable();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR).setContentText("Erreur: " + ex.getMessage());
        }
    }

    private void supprimer() {
        Eleve e = view.getTable().getSelectionModel().getSelectedItem();
        if (e != null) {
            autoEcole.supprimerEleve(e.getNumSAAQ());
            rafraichirTable();
        }
    }
}