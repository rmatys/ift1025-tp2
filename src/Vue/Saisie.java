// package Vue;

// import java.util.Scanner;

// /**
//  * Méthodes de capture brute des entrées utilisateur et d'affichage de messages génériques.
//  * Ne contient aucune logique métier: uniquement de l'affichage et de la lecture du Scanner.
//  */
// public class Saisie {
//     public static int demanderEntier(Scanner scanner, String prompt) {
//         System.out.print(prompt);
//         try {
//             int valeur = scanner.nextInt();
//             scanner.nextLine();
//             return valeur;
//         } catch (Exception e) {
//             scanner.nextLine();
//             throw e;
//         }
//     }

//     public static long demanderLong(Scanner scanner, String prompt) {
//         System.out.print(prompt);
//         try {
//             long valeur = scanner.nextLong();
//             scanner.nextLine();
//             return valeur;
//         } catch (Exception e) {
//             scanner.nextLine();
//             throw e;
//         }
//     }

//     public static double demanderDouble(Scanner scanner, String prompt) {
//         System.out.print(prompt);
//         try {
//             double valeur = scanner.nextDouble();
//             scanner.nextLine();
//             return valeur;
//         } catch (Exception e) {
//             scanner.nextLine();
//             throw e;
//         }
//     }

//     public static String demanderLigne(Scanner scanner, String prompt) {
//         System.out.print(prompt);
//         return scanner.nextLine();
//     }

//     public static void afficherMessage(String message) {
//         System.out.println(message);
//     }
// }
