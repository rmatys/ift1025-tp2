# ift1025-tp2
# TP2 – Migration vers une Interface Graphique JavaFX avec Architecture MVC
  
Session Été 2026
Date de remise 2/08/2026 avant minuit
Directives
  • Une pénalité de 33 % par jour de retard sera appliquée (maximum 3 jours de retard).
  • Les contestations des notes seront acceptées pendant les deux semaines suivant l'affichage de celles-ci sur le StudiUM.

Objectif général
  À partir du programme développé dans le cadre du TP1 (Système de Gestion pour Auto-École), vous devez remplacer l'interface textuelle par une interface graphique réalisée avec JavaFX. Cette refonte devra suivre rigoureusement l'architecture MVC (Modèle – Vue – Contrôleur).

Le but du TP2 est :
  • Offrir une expérience utilisateur plus intuitive et ergonomique ;
  • Structurer le code selon une séparation claire des responsabilités ;
  • Conserver l'ensemble des fonctionnalités du TP1 tout en les adaptant à une interface graphique.

Contexte et prérequis
  Ce travail s'appuie sur le TP1 déjà réalisé. Vous devez donc :
    • Reprendre le code source existant (classes de modélisation de l'auto-école, gestion des fichiers CSV, menu textuel, etc.) ;
    • Conserver le modèle « École » (classes représentant les élèves, activités, paiements, voitures, dépenses) ;
    • Conserver la persistance des données (lecture/écriture dans les fichiers CSV selon le format défini) ;
    • Remplacer uniquement la couche interface utilisateur : le menu textuel est abandonné au profit d'une interface graphique JavaFX.
    Important : Vous ne réécrivez pas l'application complète. Vous effectuez une migration ciblée de l'interface et de l'architecture.

Utilisation de l'intelligence artificielle (IA)
  L'utilisation de l'IA est autorisée pour ce travail. Toutefois, les conditions suivantes s'appliquent :
  1. Déclaration obligatoire – Vous devez inclure dans votre rapport une auto-déclaration précisant que le travail a été réalisé avec l'aide de l'IA. Vous devez décrire brièvement comment vous avez utilisé l'IA (outils utilisés, prompts, etc.).
  2. Code non fonctionnel – Si le code généré par l'IA n'est pas fonctionnel, vous recevrez la note 0 pour les parties concernées. Aucune note partielle ne sera attribuée, car avec l'IA, vous êtes en mesure de générer automatiquement et rapidement des blocs de code fonctionnels.
  3. Compréhension et adaptation – Vous devez adapter le code généré pour qu'il intègre les éléments vus en cours. Cela vous permettra de comprendre et d'expliquer les concepts mis en œuvre. Vous devez être capable de justifier vos choix et d'expliquer le fonctionnement du code produit.
  4. Étudiants n'utilisant pas l'IA – Pour ceux qui choisissent de ne pas utiliser l'IA, des notes partielles seront attribuées pour toutes les parties du TP, même pour le code non fonctionnel, selon le barème fourni.

Architecture MVC obligatoire
  Vous devez structurer votre application selon le patron MVC :
  1. Modèle (Model)
    • Représente les données de l'auto-école et la logique de gestion.
    • Inclut les classes « École » (élève, activité, paiement, voiture, dépense, etc.).
    • Inclut les services de gestion (lecture/écriture CSV, calculs de tarifs, génération de rapports).
    • Ne dépend pas de l'interface utilisateur.
    • Doit exposer des méthodes permettant au contrôleur d'accéder et de modifier les données.
  2. Vue (View)
    • Composée des classes construisant l'interface graphique à l'aide des composants JavaFX (Stage, Scene, Pane, Button, Label, TextField, TableView, etc.).
    • Gère l'affichage des données et la présentation.
    • Se contente d'afficher les informations fournies par le contrôleur et de transmettre les actions utilisateur à celui-ci.
  3. Contrôleur (Controller)
    • Fait le lien entre le Modèle et la Vue.
    • Capture les événements utilisateur.
    • Appelle les méthodes du Modèle pour effectuer les traitements demandés.
    • Met à jour la Vue en fonction des résultats obtenus.
    • Contient la logique de navigation entre les différentes fenêtres/panneaux de l'application.

Fonctionnalités à implémenter dans l'interface graphique
  Vous devez offrir une interface complète permettant de réaliser toutes les opérations suivantes (issues du TP1) :

Gestion des élèves
  • Afficher la liste complète des élèves.
  • Ajouter un nouvel élève.
  • Modifier les informations d'un élève existant.
  • Supprimer un élève.
  • Rechercher un élève par numéro SAAQ, nom ou prénom.

Gestion des activités
  • Planifier une nouvelle activité (sélection de l'élève, type, date, heure, durée, véhicule).
  • Afficher la liste des activités (avec leur statut).
  • Mettre à jour le statut d'une activité (complétée / non complétée).
  • Annuler une activité.
  • Afficher les détails d'une activité.

Gestion des paiements
  • Enregistrer un paiement.
  • Consulter l'historique des paiements par élève.
  • Afficher le statut des paiements (Payé, Impayé, Partiellement payé).

Gestion des véhicules
  • Afficher la liste des véhicules.
  • Ajouter/modifier/supprimer un véhicule.
  • Suivre le kilométrage et l'état du véhicule (disponible, en réparation, vendu).

Gestion des dépenses
  • Ajouter une dépense liée à un véhicule (réparation, entretien, carburant).
  • Ajouter une autre dépense (publicité, bureau, téléphone, internet, etc.).
  • Consulter les dépenses par catégorie.

Génération de rapports
  • Permettre à l'utilisateur de générer les 4 rapports (élèves, revenus, dépenses voiture, autres dépenses).
  • La génération doit produire un fichier texte dans un répertoire choisi par l'utilisateur.

Liberté de conception
  L'énoncé est volontairement général en ce qui concerne l'interface graphique. Vous avez l'entière liberté de :
  • Choisir l'agencement des composants (fenêtres, panneaux, conteneurs) ;
  • Déterminer la navigation entre les différentes parties de l'application ;
  • Sélectionner les composants graphiques les plus appropriés pour chaque fonctionnalité.
  C'est à vous de concevoir l'interface utilisateur de manière ergonomique et cohérente, en justifiant vos choix dans le rapport.

Contenu du rapport à remettre
  Votre rapport doit obligatoirement contenir les sections suivantes :
    a) Schéma arborescent de l'interface graphique
      • Présenter, sous forme d'un arbre structuré, l'organisation de votre interface.
      • Indiquer clairement pour chaque nœud :
        o S'il s'agit d'un nœud conteneur ;
        o S'il s'agit d'un nœud feuille (objet graphique).
    b) Schéma détaillé de l'architecture MVC
      • Présenter un schéma clair (UML ou diagramme de blocs) montrant :
        o Les classes appartenant au bloc Modèle ;
        o Les classes appartenant au bloc Vue ;
        o Les classes appartenant au bloc Contrôleur.
      • Décrire les interactions entre ces trois blocs (schématiquement) :
        o Comment le Contrôleur accède-t-il au Modèle ?
        o Comment la Vue est-elle mise à jour après une action ?
        o Comment les événements utilisateur sont-ils transmis au Contrôleur ?
    c) Compromis ou adaptations par rapport au TP1
      • Décrire les éventuels compromis ou adaptations par rapport au TP1.
    d) Mode d'emploi
      • Expliquer comment utiliser l'interface graphique :
        o Navigation, Fonctionnalités disponibles (captures d’écran);
        o Instructions pour les opérations courantes.
    e) Bilan du travail (1/2 page maximum)
      • Difficultés rencontrées.
      • Critiques et suggestions d'amélioration.
    f) Auto-déclaration d'utilisation de l'IA (obligatoire, 1/2 page maximum)
      • Indiquer si l'IA a été utilisée ou non.
      • Si oui, décrire les outils utilisés.
      • Indiquer dans quelles parties du TP2 l'IA a été utilisée (développement de la structure, choix des algorithmes, codage de quels éléments, génération du rapport, etc.). 

Contraintes techniques
  1. JavaFX obligatoire – L'interface graphique doit être développée avec JavaFX.
  2. Respect de MVC – La séparation entre Modèle, Vue et Contrôleur doit être présente.
  3. Conservation de la persistance CSV – Les fichiers CSV définis dans le TP1 doivent être utilisés sans modification de leur format.
  4. Gestion des erreurs – L'interface doit gérer les erreurs de saisie (champs vides, formats invalides, dates incorrectes, etc.) via des messages appropriés.
  5. Réutilisation maximale du code du TP1 – Le code « École » (classes, services, utilitaires) doit être réutilisé tel quel ou avec des adaptations mineures. L'effort de développement doit porter sur la nouvelle couche graphique et l'architecture MVC.

Critères d'évaluation (TP2)
Critère Pondération
  Respect de l'énoncé – Fonctionnalités toutes implantées 10 %
  Architecture MVC – Séparation claire Modèle/Vue/Contrôleur 20 %
  Qualité de l'interface graphique – Ergonomie, organisation, clarté 15 %
  Qualité du code – Encapsulation, nommage, commentaires, indentation 10 %
  Fonctionnalité complète – Code exécutable et opérationnel 25 %
  Rapport – Schémas, explications, justifications, mode d'emploi, bilan, auto-déclaration IA 20 %
  Total 100 %

Livrables attendus
  • Code source complet du projet (répertoire contenant toutes les classes Java).
  • Rapport de projet (PDF) contenant les sections demandées ci-dessus, incluant l'auto-déclaration d'utilisation de l'IA.
  • Fichiers CSV d'exemple pour tester l'application.
