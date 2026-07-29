package Vue;

import Modele.*;

/**
 * Affichage des menus textuels de l'application (titres et options).
 */
public class VueMenu {
    public static void afficherTitrePrincipal() {
        System.out.println("============================================");
        System.out.println("                AUTO-ÉCOLE");
    }

    public static void afficherOptionsPrincipal() {
        System.out.println("============================================");
        System.out.println("1. Gestion des élèves");
        System.out.println("2. Gestion des activités");
        System.out.println("3. Gestion des finances");
        System.out.println("4. Gestion des véhicules");
        System.out.println("5. Génération de rapports");
        System.out.println("6. Aide pour les types de valeurs");
        System.out.println("7. Quitter");
        System.out.println("============================================");
    }

    public static void afficherTitreEleves() {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES ÉLÈVES");
    }

    public static void afficherOptionsEleves() {
        System.out.println("--------------------------------------------");
        System.out.println("1. Inscrire un nouvel élève");
        System.out.println("2. Rechercher un élève");
        System.out.println("3. Supprimer un élève");
        System.out.println("4. Afficher la liste des élèves");
        System.out.println("5. Retour au menu principal");
        System.out.println("--------------------------------------------");
    }

    public static void afficherTitreActivites() {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES ACTIVITÉS");
    }

    public static void afficherOptionsActivites() {
        System.out.println("--------------------------------------------");
        System.out.println("1. Planifier une nouvelle activité");
        System.out.println("2. Rechercher une activité");
        System.out.println("3. Annuler une activité");
        System.out.println("4. Marquer une activité comme complétée");
        System.out.println("5. Afficher les activités d'un élève");
        System.out.println("6. Afficher toutes les activités");
        System.out.println("7. Retour au menu principal");
        System.out.println("--------------------------------------------");
    }

    public static void afficherTitreFinanciere() {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION FINANCIÈRE");
    }

    public static void afficherOptionsFinanciere() {
        System.out.println("--------------------------------------------");
        System.out.println("1. Paiements");
        System.out.println("2. Enregistrer une dépense du véhicule");
        System.out.println("3. Enregistrer une dépense autre");
        System.out.println("4. Retour au menu principal");
        System.out.println("--------------------------------------------");
    }

    public static void afficherTitrePaiements() {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES PAIEMENTS");
    }

    public static void afficherOptionsPaiements() {
        System.out.println("--------------------------------------------");
        System.out.println("1. Générer une facture pour une activité");
        System.out.println("2. Enregistrer un paiement");
        System.out.println("3. Modifier le statut d'un paiement");
        System.out.println("4. Rechercher un paiement");
        System.out.println("5. Afficher les paiements d'un élève");
        System.out.println("6. Afficher les impayés");
        System.out.println("7. Retour au menu de gestion financière");
        System.out.println("--------------------------------------------");
    }

    public static void afficherTitreVoiture() {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES VOITURES");
    }

    public static void afficherOptionsVoiture() {
        System.out.println("--------------------------------------------");
        System.out.println("1. Ajouter une voiture à l'école");
        System.out.println("2. Rechercher une voiture");
        System.out.println("3. Modifier l'état d'une voiture");
        System.out.println("4. Enregistrer une dépense");
        System.out.println("5. Afficher l'historique des dépenses pour une voiture spécifique");
        System.out.println("6. Afficher toutes les dépenses pour les voitures");
        System.out.println("7. Afficher la liste des voitures");
        System.out.println("8. Retour au menu principal");
        System.out.println("--------------------------------------------");
    }

    public static void afficherTitreRapports() {
        System.out.println("--------------------------------------------");
        System.out.println("            GESTION DES RAPPORTS");
    }

    public static void afficherOptionsRapports() {
        System.out.println("--------------------------------------------");
        System.out.println("1. Rapport des élèves");
        System.out.println("2. Rapport de revenus");
        System.out.println("3. Rapport des dépenses de voiture");
        System.out.println("4. Rapport des autres dépenses");
        System.out.println("5. Générer tous les rapports");
        System.out.println("6. Retour au menu principal");
        System.out.println("--------------------------------------------");
    }

    public static void afficherAide() {
        System.out.println("------------------------------------------------------");
        System.out.println("Voici les règles pour les différents types de valeurs");

        System.out.print("MethodePaiement: ");
        for (MethodePaiement m : MethodePaiement.values()) {
            System.out.print(m + "(" + m.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("StatutActivite: ");
        for (StatutActivite s : StatutActivite.values()) {
            System.out.print(s + "(" + s.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("StatutPaiement: ");
        for (StatutPaiement s : StatutPaiement.values()) {
            System.out.print(s + "(" + s.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("StatutVoiture: ");
        for (StatutVoiture s : StatutVoiture.values()) {
            System.out.print(s + "(" + s.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("TypeActivite: ");
        for (TypeActivite t : TypeActivite.values()) {
            System.out.print(t + "(" + t.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("TypeAutreDepense: ");
        for (TypeAutreDepense t : TypeAutreDepense.values()) {
            System.out.print(t + "(" + t.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.print("TypeDepenseVoiture: ");
        for (TypeDepenseVoiture t : TypeDepenseVoiture.values()) {
            System.out.print(t + "(" + t.getLibelle() + ") - ");
        }
        System.out.println();

        System.out.println("NumSAAQ: long");
        System.out.println("Téléphone: String");
        System.out.println("Date: format JJ-MM-AAAA");
        System.out.println("Heure: format HH:MM");
        System.out.println("Duree: int (minute)");
        System.out.println("Plaque: String (3 lettres, 3 chiffres)");
        System.out.println("ID: int");
        System.out.println("Montant: double");
        System.out.println("Prix: double");
        System.out.println("Kilomètres: int");

        System.out.println("------------------------------------------------------");
    }
}
