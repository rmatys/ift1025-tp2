package tp2.src.controleur;

import tp2.src.modele.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Test {
    /**
     * Tests internes pour l'auto-école
     * <p>
     * Résultats:
     * ============================================
     *               TESTS INTERNES
     * ============================================
     * <p>
     * --- Test 1 : Ajout élève ---
     * OK: élève ajouté
     * <p>
     * --- Test 2 : Ajout voiture ---
     * OK: voiture ajoutée
     * <p>
     * --- Test 3 : Ajout activité ---
     * OK: activité ajoutée
     * <p>
     * --- Test 4 : Conflit horaire ---
     * OK: conflit détecté
     * <p>
     * --- Test 5 : Ajout dépense voiture ---
     * OK: dépense ajoutée
     * <p>
     * --- Test 6 : Ajout paiement ---
     * OK: paiement ajouté
     * <p>
     * ============================================
     * Tests terminés.
     * ============================================
     */
    public static void test() {
        System.out.println("============================================");
        System.out.println("              TESTS INTERNES");
        System.out.println("============================================");

        AutoEcole testAE = new AutoEcole();

        System.out.println("\n--- Test 1 : Ajout élève ---");
        Eleve e = new Eleve(111111111L, "Test", "Eleve", "adr", "tel", LocalDate.now());
        testAE.ajouterEleve(e);
        System.out.println(testAE.rechercherEleve(111111111L) != null
                ? "OK: élève ajouté"
                : "ERREUR: élève non trouvé");

        System.out.println("\n--- Test 2 : Ajout voiture ---");
        String plaque = "ABC123";
        Voiture v = new Voiture(plaque, "Toyota", 2020, 25000, 15000, StatutVoiture.D, 45000);
        testAE.ajouterVoiture(v);
        System.out.println(testAE.rechercherVoiture(plaque) != null
                ? "OK: voiture ajoutée"
                : "ERREUR: voiture non trouvée");

        System.out.println("\n--- Test 3 : Ajout activité ---");
        PlageHoraire ph = new PlageHoraire(LocalDate.now(), LocalTime.of(9, 0), 60);
        Activite a = new Activite(1, ph, e, plaque, TypeActivite.LPA, StatutActivite.C);
        testAE.ajouterActivite(a);
        System.out.println(testAE.rechercherActivite(1) != null
                ? "OK: activité ajoutée"
                : "ERREUR: activité non trouvée");

        System.out.println("\n--- Test 4 : Conflit horaire ---");
        PlageHoraire ph2 = new PlageHoraire(LocalDate.now(), LocalTime.of(9, 30), 60);
        ArrayList<PlageHoraire> plagesHoraire = new ArrayList<>();
        for (Activite activite : testAE.getActivites()) {
            plagesHoraire.add(activite.getPlageHoraire());
        }
        boolean conflit = ph2.estEnConflitHoraire(plagesHoraire);
        System.out.println(conflit
                ? "OK: conflit détecté"
                : "ERREUR: conflit NON détecté");

        System.out.println("\n--- Test 5 : Ajout dépense voiture ---");
        DepenseVoiture d = new DepenseVoiture(1, "ABC123", LocalDate.now(), TypeDepenseVoiture.R, "Freins", 350.0);
        testAE.ajouterDepenseVoiture(d);
        v.updateDepenses(testAE.getDepensesVoiture());
        System.out.println(v.getDepensesVoiture().size() == 1
                ? "OK: dépense ajoutée"
                : "ERREUR: dépense non ajoutée");

        System.out.println("\n--- Test 6 : Ajout paiement ---");
        Paiement p = new Paiement(1, LocalDate.now(), StatutPaiement.I, a, MethodePaiement.E, e);
        testAE.ajouterPaiement(p);
        System.out.println(testAE.rechercherPaiement(p.getId()) != null
                ? "OK: paiement ajouté"
                : "ERREUR: paiement non trouvé");

        System.out.println("\n============================================");
        System.out.println("Tests terminés.");
        System.out.println("============================================");
    }
}
