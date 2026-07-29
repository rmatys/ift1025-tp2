package MenuTextuel;

import Modele.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Modifier {
    /**
     * Change l'état d'une activité en recherchant l'activité par son ID
     */
    public static void completerActivite(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'une activité par son ID");
            System.out.print("ID de l'activité: ");

            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                Activite activite = autoEcole.rechercherActivite(id);

                if (activite == null) {
                    System.out.println("Aucune activité attaché à cet identificateur.");
                    return;
                }

                autoEcole.completerActivite(id);
                System.out.println(" - " + activite);
                System.out.println("L'activité à été marqué comme complété");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }

    /**
     * Change l'état d'une voiture en recherchant la voiture par sa plaque d'immatriculation
     */
    public static void changerEtatVoiture(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'une voiture par sa plaque d'immatriculation");
            System.out.print("Plaque d'immatriculation: ");

            try {
                String plaque = scanner.nextLine();

                Voiture voiture = autoEcole.rechercherVoiture(plaque);

                if (voiture == null) {
                    System.out.println("Voiture de l'extérieur (non modifiable).");
                    return;
                }

                System.out.println("Voiture actuelle: ");
                System.out.println(" - " + voiture);
                subChangerEtatVoiture(scanner, autoEcole, voiture);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Change l'état d'une voiture en demandant à l'utilisateur le nouvel état
     */
    public static void subChangerEtatVoiture(Scanner scanner, AutoEcole autoEcole, Voiture voiture) {
        while(true) {
            System.out.println("Changement de l'état, choix disponibles  R (réparation), V (vendu), D (disponible).");
            System.out.print("Votre choix: ");

            try {
                String etat = scanner.nextLine();

                StatutVoiture statutVoiture = StatutVoiture.valueOf(etat);

                voiture.setEtat(statutVoiture);
                autoEcole.sauvegarderVoitures();
                System.out.println("Changement effectué, la voiture est maintenant dans l'état " + statutVoiture.getLibelle());

                break;

            } catch (Exception e) {
                System.out.println("Erreur: les options sont R, V et D. Réessaie");
            }
        }
    }

    /**
     * Change l'état d'un paiement en recherchant le paiement par son identifiant
     */
    public static void changerEtatPaiement(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'un paiement par son identifiant (format : F-AAAA-XXXXX)");
            System.out.print("Identifiant de paiement: ");

            try {
                String id = scanner.nextLine();

                Paiement paiement = autoEcole.rechercherPaiement(id);

                if (paiement == null) {
                    System.out.println("Aucun paiement attaché à cet identificateur.");
                    return;
                }

                System.out.println("Paiement actuel: ");
                System.out.println(" - " + paiement);
                subChangerEtatPaiement(scanner, autoEcole, paiement);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Change l'état d'un paiement en demandant à l'utilisateur le nouvel état
     */
    public static void subChangerEtatPaiement(Scanner scanner, AutoEcole autoEcole, Paiement paiement) {
        while(true) {
            System.out.println("Changement de l'état, choix disponibles  P (payé), I (impayé), PP (partiellement payé).");
            System.out.print("Votre choix: ");

            try {
                String etat = scanner.nextLine();

                StatutPaiement statutPaiement = StatutPaiement.valueOf(etat);

                if (statutPaiement.equals(StatutPaiement.P)) {
                    paiement.setMontantRestant(0);
                } else if (statutPaiement.equals(StatutPaiement.PP)) {
                    montantPaye(scanner, paiement);
                }

                paiement.setDate(LocalDate.now());
                paiement.setEtat(statutPaiement);
                autoEcole.sauvegarderPaiements();
                System.out.println("Changement effectué, le paiement est maintenant dans l'état " + statutPaiement.getLibelle());

                break;

            } catch (Exception e) {
                System.out.println("Erreur: les options sont P, I et PP. Réessaie");
            }
        }
    }

    /**
     * Change le montant restant d'un paiement en demandant à l'utilisateur le nouveau montant
     */
    public static void montantPaye(Scanner scanner, Paiement paiement) {
        while(true) {
            System.out.println("Paiement à été partiellement payé.");
            System.out.print("Montant restant: ");

            try {
                double montantRestant = scanner.nextDouble();
                scanner.nextLine();

                if (montantRestant > paiement.getMontantRestant()) {
                    System.out.println("Erreur: le montant restant doit être plus petit que ce qu'il y avait avant. Réessaie");
                    continue;
                }

                paiement.setMontantRestant(montantRestant);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: le montant restant doit être un nombre à virgule (double).");
            }
        }
    }
}
