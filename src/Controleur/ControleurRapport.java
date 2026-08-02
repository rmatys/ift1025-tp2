package Controleur;

import Modele.AutoEcole;
import Modele.CSV;
import Modele.Rapports;
import Vue.MenuRapport;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ControleurRapport {
    private final AutoEcole autoEcole;
    private final BorderPane conteneur;
    private final MenuRapport vue;
    private final List<RapportInfo> rapports;

    private record RapportInfo(String libelle, String nomFichier, Function<File, Boolean> generation) {}

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
                        destination -> Rapports.genererRapportEleves(autoEcole.getEleves(), destination)),
                new RapportInfo("Rapport des revenus", "rapportRevenus" + CSV.YEAR + ".txt",
                        destination -> Rapports.genererRapportRevenus(autoEcole.getActivites(), autoEcole.getPaiements(), destination)),
                new RapportInfo("Rapport des dépenses véhicule", "rapportDepensesVoiture" + CSV.YEAR + ".txt",
                        destination -> Rapports.genererRapportDepensesVoiture(autoEcole.getDepensesVoiture(), destination)),
                new RapportInfo("Rapport des autres dépenses", "rapportAutresDepenses" + CSV.YEAR + ".txt",
                        destination -> Rapports.genererRapportAutresDepenses(autoEcole.getAutresDepenses(), destination))
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
            File destination = new File(dossier, rapport.nomFichier());
            if (!rapport.generation().apply(destination)) {
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
     * Génère un rapport via le modèle, en l'enregistrant directement à l'emplacement
     * choisi par l'utilisateur à l'aide d'un FileChooser.
     */
    private void genererRapportUnique(String libelle) {
        RapportInfo rapport = rapports.stream().filter(r -> r.libelle().equals(libelle)).findFirst().orElse(null);
        if (rapport == null) return;

        File destination = vue.choisirFichierDestination(rapport.nomFichier());
        if (destination == null) return;

        if (rapport.generation().apply(destination)) {
            vue.afficherInformation("Rapport enregistré : " + destination.getAbsolutePath());
        } else {
            vue.afficherErreur("Impossible d'enregistrer le rapport à l'emplacement choisi.");
        }
    }

    private void retourMenuPrincipal() {
        conteneur.setCenter(new ControleurPrincipal(autoEcole, conteneur).getVue());
    }
}
