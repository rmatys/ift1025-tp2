package Autre;
import javafx.scene.control.Button;

public class Util {
    /**
     * Applique un style uniforme (taille, couleurs, effet au survol) à un bouton de menu.
     */
    public static Button creerBoutonMenu(String texte) {
        Button bouton = new Button(texte);
        bouton.setPrefSize(280, 45);
        bouton.setMaxWidth(280);

        String styleNormal = "-fx-background-color: #3498db; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-background-radius: 8; -fx-cursor: hand;";
        String styleSurvol = "-fx-background-color: #2980b9; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-background-radius: 8; -fx-cursor: hand;";

        bouton.setStyle(styleNormal);
        bouton.setOnMouseEntered(e -> bouton.setStyle(styleSurvol));
        bouton.setOnMouseExited(e -> bouton.setStyle(styleNormal));

        return bouton;
    }
}
