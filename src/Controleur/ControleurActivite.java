package Controleur;

import Modele.Activite;
import Modele.AutoEcole;
import Modele.OperationInvalideException;
import Modele.PlageHoraire;
import Modele.StatutActivite;
import Modele.Voiture;
import Vue.MenuActivite;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ControleurActivite {
    private final AutoEcole autoEcole;
    private final BorderPane conteneur;
    private final MenuActivite vue;

    public ControleurActivite(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;
        this.conteneur = conteneur;
        this.vue = new MenuActivite();

        vue.setListeEleves(autoEcole.getEleves());
        ArrayList<String> plaques = new ArrayList<>();
        for (Voiture voiture : autoEcole.getVoitures()) plaques.add(voiture.getPlaque());
        vue.setListeVehicules(plaques);

        vue.setOnPlanifier(this::planifierActivite);
        vue.setOnMettreAJourStatut(this::mettreAJourStatut);
        vue.setOnDetails(this::afficherDetails);
        vue.setOnAnnuler(this::annulerActivite);
        vue.setOnRetour(this::retourMenuPrincipal);

        rafraichirTable();
    }

    public Region getVue() { return vue; }

    private void planifierActivite() {
        try {
            if (vue.getEleveChoisi() == null) {
                throw new IllegalArgumentException("Sélectionnez un élève.");
            }
            if (vue.getTypeChoisi() == null) {
                throw new IllegalArgumentException("Sélectionnez un type d'activité.");
            }
            if (vue.getDateChoisie() == null) {
                throw new IllegalArgumentException("Sélectionnez une date.");
            }
            if (vue.getTexteHeure().isBlank() || vue.getTexteDuree().isBlank()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            LocalTime heureDebut;
            try {
                heureDebut = LocalTime.parse(vue.getTexteHeure().trim(), DateTimeFormatter.ofPattern("H:mm"));
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("L'heure doit être au format H:mm (ex : 14:30).");
            }

            int duree = Integer.parseInt(vue.getTexteDuree().trim());
            String plaque = vue.getVehiculeChoisi() == null || vue.getVehiculeChoisi().equals(MenuActivite.AUCUN_VEHICULE)
                    ? "" : vue.getVehiculeChoisi();

            PlageHoraire horaire = new PlageHoraire(vue.getDateChoisie(), heureDebut, duree);
            autoEcole.creerActivite(horaire, vue.getEleveChoisi().getNumSAAQ(), plaque,
                                     vue.getTypeChoisi(), StatutActivite.NC);
            rafraichirTable();
            vue.viderFormulaire();
        } catch (NumberFormatException ex) {
            vue.afficherErreur("La durée doit être un nombre valide.");
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void mettreAJourStatut() {
        Activite selectionnee = vue.getActiviteSelectionnee();
        if (selectionnee == null) {
            vue.afficherAvertissement("Sélectionnez une activité dans la liste.");
            return;
        }

        autoEcole.changerStatutActivite(selectionnee.getId(), vue.getStatutChoisi());
        rafraichirTable();
        vue.viderFormulaire();
    }

    private void annulerActivite() {
        Activite selectionnee = vue.getActiviteSelectionnee();
        if (selectionnee == null) {
            vue.afficherAvertissement("Sélectionnez une activité dans la liste.");
            return;
        }

        autoEcole.annulerActivite(selectionnee.getId());
        rafraichirTable();
        vue.viderFormulaire();
    }

    private void afficherDetails() {
        Activite a = vue.getActiviteSelectionnee();
        if (a == null) {
            vue.afficherAvertissement("Sélectionnez une activité dans la liste.");
            return;
        }

        String details = "Élève : " + a.getEleve().getPrenom() + " " + a.getEleve().getNom()
                + " (NumSAAQ " + a.getEleve().getNumSAAQ() + ")\n"
                + "Type : " + a.getType().getLibelle() + "\n"
                + "Date : " + a.getPlageHoraire().getDate() + "\n"
                + "Heure : " + a.getPlageHoraire().getHeureDebut() + " - " + a.getPlageHoraire().getHeureFin() + "\n"
                + "Durée : " + a.getPlageHoraire().getDuree() + " minutes\n"
                + "Véhicule : " + (a.getPlaque().isBlank() ? MenuActivite.AUCUN_VEHICULE : a.getPlaque()) + "\n"
                + "Statut : " + a.getStatut().getLibelle() + "\n"
                + "Montant : " + String.format("%.2f $", a.getMontant());

        vue.afficherDetails("Détails de l'activité #" + a.getId(), details);
    }

    private void rafraichirTable() {
        vue.afficherActivites(autoEcole.getActivites());
    }

    private void retourMenuPrincipal() {
        conteneur.setCenter(new ControleurPrincipal(autoEcole, conteneur).getVue());
    }
}
