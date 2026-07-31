package ift1025-tp2.src.Controleur;

package controller;

import model.*;
import view.PaiementView;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import java.time.LocalDate;

public class PaiementController {

    private PaiementView view;
    private AutoEcole autoEcole;

    public PaiementController(PaiementView view, AutoEcole autoEcole) {
        this.view = view;
        this.autoEcole = autoEcole;

        rafraichirTable();

        view.getBtnAjouter().setOnAction(e -> ajouter());
    }

    private void rafraichirTable() {
        view.getTable().setItems(FXCollections.observableArrayList(autoEcole.getPaiements()));
    }

    private void ajouter() {
        try {
            int idAct = Integer.parseInt(view.getTxtIdActivite().getText());
            StatutPaiement statut = StatutPaiement.valueOf(view.getTxtStatut().getText());
            MethodePaiement methode = MethodePaiement.valueOf(view.getTxtMethode().getText());

            Activite activite = autoEcole.rechercherActivite(idAct);
            if (activite == null) throw new Exception("Activité introuvable");

            int seq = autoEcole.prochainNumeroPaiement();
            Paiement p = new Paiement(seq, LocalDate.now(), statut, activite, methode, activite.getEleve());

            autoEcole.ajouterPaiement(p);
            rafraichirTable();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR).setContentText("Erreur: " + ex.getMessage());
        }
    }
}