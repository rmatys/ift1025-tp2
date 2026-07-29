package Modele;

/**
 * Exception levée lorsqu'une règle métier de l'auto-école n'est pas respectée
 * (ex: conflit d'horaire, montant négatif, véhicule non disponible)
 */
public class OperationInvalideException extends Exception {
    public OperationInvalideException(String message) {
        super(message);
    }
}
