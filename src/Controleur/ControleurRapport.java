package Controleur;

import Modele.AutoEcole;
import Modele.CSV;
import Modele.Rapports;
import Vue.MenuRapport;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ControleurRapport {
    private final AutoEcole autoEcole;
    private final BorderPane conteneur;
    private final MenuRapport vue;
    private final List<RapportInfo> rapports;

    private record RapportInfo(String libelle, String nomFichier, BooleanSupplier generation) {}

    public ControleurRapport(AutoEcole autoEcole, BorderPane conteneur) {
        this.autoEcole = autoEcole;
        this.conteneur = conteneur;
        this.vue = new MenuRapport();
        this.rapports = listeRapports();

        vue.setLibellesRapports(rapports.stream().map(RapportInfo::libelle).toList());
        vue.setOnGenererTous(this::genererTousLesRapports);
        vue.setOnGenererUnique(this::genererRapportUnique);
        vue.setOnRetour(this::retourMenuPrincipal);
    }

    public Region getVue() { return vue; }

    private List<RapportInfo> listeRapports() {
        return List.of(
                new RapportInfo("Rapport des élèves", "rapportEleves" + CSV.YEAR + ".txt",
                        () -> Rapports.genererRapportEleves(autoEcole.getEleves())),
                new RapportInfo("Rapport des revenus", "rapportRevenus" + CSV.YEAR + ".txt",
                        () -> Rapports.genererRapportRevenus(autoEcole.getActivites(), autoEcole.getPaiements())),
                new RapportInfo("Rapport des dépenses véhicule", "rapportDepensesVoiture" + CSV.YEAR + ".txt",
                        () -> Rapports.genererRapportDepensesVoiture(autoEcole.getDepensesVoiture())),
                new RapportInfo("Rapport des autres dépenses", "rapportAutresDepenses" + CSV.YEAR + ".txt",
                        () -> Rapports.genererRapportAutresDepenses(autoEcole.getAutresDepenses()))
        );
    }

    /**
     * Génère les 4 rapports d'un coup et les enregistre dans un unique répertoire
     * choisi par l'utilisateur à l'aide d'un DirectoryChooser.
     */
    private void genererTousLesRapports() {
        File dossier = vue.choisirDossierDestination();
        if (dossier == null) return;

        List<String> echecs = new ArrayList<>();
        for (RapportInfo rapport : rapports) {
            if (!rapport.generation().getAsBoolean()) {
                echecs.add(rapport.libelle());
                continue;
            }

            try {
                File source = new File(CSV.getDir("rapport"), rapport.nomFichier());
                File destination = new File(dossier, rapport.nomFichier());
                Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                echecs.add(rapport.libelle());
            }
        }

        if (echecs.isEmpty()) {
            vue.afficherInformation("Les 4 rapports ont été enregistrés dans : " + dossier.getAbsolutePath());
        } else {
            vue.afficherErreur("Échec de la génération pour : " + String.join(", ", echecs));
        }
    }

    /**
     * Génère un rapport via le modèle, puis laisse l'utilisateur choisir où enregistrer
     * le fichier texte produit à l'aide d'un FileChooser.
     */
    private void genererRapportUnique(String libelle) {
        RapportInfo rapport = rapports.stream().filter(r -> r.libelle().equals(libelle)).findFirst().orElse(null);
        if (rapport == null) return;

        if (!rapport.generation().getAsBoolean()) {
            vue.afficherErreur("La génération du rapport a échoué.");
            return;
        }

        File destination = vue.choisirFichierDestination(rapport.nomFichier());
        if (destination == null) return;

        try {
            File source = new File(CSV.getDir("rapport"), rapport.nomFichier());
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            vue.afficherInformation("Rapport enregistré : " + destination.getAbsolutePath());
        } catch (IOException ex) {
            vue.afficherErreur("Impossible d'enregistrer le rapport à l'emplacement choisi.");
        }
    }

    private void retourMenuPrincipal() {
        conteneur.setCenter(new ControleurPrincipal(autoEcole, conteneur).getVue());
    }
}
