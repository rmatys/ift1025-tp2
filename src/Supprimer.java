import java.util.Scanner;

public class Supprimer {
    /**
     * Supprime un élève par son numéro SAAQ
     */
    public static void supprimerEleve(Scanner scanner, AutoEcole autoEcole) {
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

                autoEcole.supprimerEleve(numSAAQ);
                System.out.println(" - " + eleve);
                System.out.println("L'élève à été supprimé");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (long). Réessaie");
            }
        }
    }

    /**
     * Supprime une activité par son ID
     */
    public static void supprimerActivite(Scanner scanner, AutoEcole autoEcole) {
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

                autoEcole.annulerActivite(id);
                System.out.println(" - " + activite);
                System.out.println("L'activité à été annulé");

                break;

            } catch (Exception e) {
                System.out.println("Erreur: il faut un numéro (int). Réessaie");
            }
        }
    }
}
