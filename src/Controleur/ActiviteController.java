package Controleur;

import Modele.*;
import Vue.ActiviteView;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ActiviteController {

    private ActiviteView view;
    private AutoEcole autoEcole;

    public ActiviteController(ActiviteView view, AutoEcole autoEcole) {
        this.view = view;
        this.autoEcole = autoEcole;

        rafraichirTable();

        view.getBtnAjouter().setOnAction(e -> ajouter());
        view.getBtnSupprimer().setOnAction(e -> supprimer());
        view.getBtnCompleter().setOnAction(e -> completer());
    }

    private void rafraichirTable() {
        view.getTable().setItems(FXCollections.observableArrayList(
            autoEcole.getActivites() == null
                ? java.util.Collections.emptyList()
                : autoEcole.getActivites()));
    }

    private void ajouter() {
        try {
            TypeActivite type = TypeActivite.valueOf(view.getTxtType().getText());
            long num = Long.parseLong(view.getTxtNum().getText());
            LocalDate date = LocalDate.parse(view.getTxtDate().getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalTime heure = LocalTime.parse(view.getTxtHeure().getText(), DateTimeFormatter.ofPattern("H:mm"));
            int duree = Integer.parseInt(view.getTxtDuree().getText());
            StatutActivite statut = StatutActivite.valueOf(view.getTxtStatut().getText());
            String plaque = view.getTxtPlaque().getText();

            Eleve eleve = autoEcole.rechercherEleve(num);
            if (eleve == null) throw new Exception("Élève introuvable");

            PlageHoraire horaire = new PlageHoraire(date, heure, duree);
            int id = autoEcole.prochainIdActivite();

            autoEcole.ajouterActivite(new Activite(id, horaire, eleve, plaque, type, statut));
            rafraichirTable();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR).setContentText("Erreur: " + ex.getMessage());
        }
    }

    private void supprimer() {
        Activite a = view.getTable().getSelectionModel().getSelectedItem();
        if (a != null) {
            autoEcole.annulerActivite(a.getId());
            rafraichirTable();
        }
    }

    private void completer() {
        Activite a = view.getTable().getSelectionModel().getSelectedItem();
        if (a != null) {
            autoEcole.completerActivite(a.getId());
            rafraichirTable();
        }
    }
}