package Controleur;

import Modele.AutoEcole;
import Modele.OperationInvalideException;
import Modele.Paiement;
import Modele.StatutPaiement;
import Vue.MenuPaiement;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class ControleurPaiement {
    private final AutoEcole autoEcole;
    private final BorderPane conteneur;
    private final MenuPaiement vue;

    public ControleurPaiement(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;
        this.conteneur = conteneur;
        this.vue = new MenuPaiement();

        vue.setListeEleves(autoEcole.getEleves());

        vue.setOnEnregistrer(this::enregistrerPaiement);
        vue.setOnMettreAJourStatut(this::mettreAJourStatut);
        vue.setOnDetails(this::afficherDetails);
        vue.setOnRecherche(texte -> rafraichirTable());
        vue.setOnEleveChoisi(eleve ->
                vue.setActivitesDisponibles(eleve == null ? new ArrayList<>() : autoEcole.getActivitesEleve(eleve)));
        vue.setOnRetour(this::retourMenuPrincipal);

        rafraichirTable();
    }

    public Region getVue() { return vue; }

    private void enregistrerPaiement() {
        try {
            if (vue.getEleveChoisi() == null) {
                throw new IllegalArgumentException("Sélectionnez un élève.");
            }
            if (vue.getActiviteChoisie() == null) {
                throw new IllegalArgumentException("Sélectionnez une activité.");
            }
            if (vue.getMethodeChoisie() == null) {
                throw new IllegalArgumentException("Sélectionnez une méthode de paiement.");
            }
            if (vue.getStatutChoisi() == null) {
                throw new IllegalArgumentException("Sélectionnez un statut.");
            }
            if (vue.getDateChoisie() == null) {
                throw new IllegalArgumentException("Sélectionnez une date.");
            }

            Paiement paiement = new Paiement(autoEcole.prochainNumeroPaiement(), vue.getDateChoisie(),
                    vue.getStatutChoisi(), vue.getActiviteChoisie(), vue.getMethodeChoisie(), vue.getEleveChoisi());
            autoEcole.ajouterPaiement(paiement);
            rafraichirTable();
            vue.viderFormulaire();
        } catch (IllegalArgumentException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void mettreAJourStatut() {
        Paiement selectionne = vue.getPaiementSelectionne();
        if (selectionne == null) {
            vue.afficherAvertissement("Sélectionnez un paiement dans la liste.");
            return;
        }

        try {
            double montantRestant = 0;
            if (vue.getStatutChoisi() == StatutPaiement.PP) {
                if (vue.getTexteMontantRestant().isBlank()) {
                    throw new IllegalArgumentException("Le montant restant est obligatoire pour un paiement partiel.");
                }
                montantRestant = Double.parseDouble(vue.getTexteMontantRestant().trim());
            }

            autoEcole.changerEtatPaiement(selectionne.getId(), vue.getStatutChoisi(), montantRestant);
            rafraichirTable();
            vue.viderFormulaire();
        } catch (NumberFormatException ex) {
            vue.afficherErreur("Le montant restant doit être un nombre valide.");
        } catch (IllegalArgumentException | OperationInvalideException ex) {
            vue.afficherErreur(ex.getMessage());
        }
    }

    private void afficherDetails() {
        Paiement p = vue.getPaiementSelectionne();
        if (p == null) {
            vue.afficherAvertissement("Sélectionnez un paiement dans la liste.");
            return;
        }

        String details = "Élève : " + p.getEleve().getPrenom() + " " + p.getEleve().getNom()
                + " (NumSAAQ " + p.getEleve().getNumSAAQ() + ")\n"
                + "Activité : " + p.getTypeActivite().getLibelle() + " du " + p.getActivite().getPlageHoraire().getDate() + "\n"
                + "Montant : " + String.format("%.2f $", p.getMontant()) + "\n"
                + "Montant restant : " + String.format("%.2f $", p.getMontantRestant()) + "\n"
                + "Méthode de paiement : " + p.getMethodePaiement().getLibelle() + "\n"
                + "Date : " + p.getDate() + "\n"
                + "Statut : " + p.getStatutPaiement().getLibelle();

        vue.afficherDetails("Détails du paiement " + p.getId(), details);
    }

    private void rafraichirTable() {
        vue.afficherPaiements(autoEcole.rechercherPaiements(vue.getTexteRecherche()));
    }

    private void retourMenuPrincipal() {
        conteneur.setCenter(new ControleurPrincipal(autoEcole, conteneur).getVue());
    }
}
