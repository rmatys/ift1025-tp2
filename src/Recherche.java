import java.util.Scanner;

public class Recherche {
    /**
     * Recherche un élève par son numéro SAAQ et affiche ses informations
     */
    public static void rechercheEleve(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'un élève par son numéro SAAQ");
            System.out.print("Numéro SAAQ: ");

            try {
                long numSAAQ = scanner.nextLong();
                scanner.nextLine();

                Eleve eleve = autoEcole.rechercherEleve(numSAAQ);

                if (eleve == null) {
                    System.out.println("Aucun élève attaché à ce numéro.");
                    return;
                }

                System.out.println(" - " + eleve);
                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Recherche une activité par son ID et affiche ses informations
     */
    public static void rechercheActivite(Scanner scanner, AutoEcole autoEcole) {
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

                System.out.println(" - " + activite);

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }

    /**
     * Recherche un paiement par son ID et affiche ses informations
     */
    public static void recherchePaiement(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'un paiement par son ID (format \"F-AAAA-XXXXX\")");
            System.out.print("ID: ");

            try {
                String id = scanner.nextLine();

                Paiement paiement = autoEcole.rechercherPaiement(id);

                if (paiement == null) {
                    System.out.println("Aucun paiement attaché à cet identifiant.");
                    return;
                }

                System.out.println(" - " + paiement);
                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un identifiant (String format \"F-AAAA-XXXXX\"). Réessaie");
            }
        }
    }

    /**
     * Recherche une voiture par sa plaque d'immatriculation et affiche ses informations
     */
    public static void rechercheVoiture(Scanner scanner, AutoEcole autoEcole) {
        while(true) {
            System.out.println("Recherche d'une voiture par sa plaque d'immatriculation");
            System.out.print("Plaque d'immatriculation: ");

            try {
                String plaque = scanner.nextLine();

                Voiture voiture = autoEcole.rechercherVoiture(plaque);

                if (voiture == null) {
                    System.out.println("Voiture de l'extérieur.");
                    return;
                }

                System.out.println(" - " + voiture);
                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }
}
