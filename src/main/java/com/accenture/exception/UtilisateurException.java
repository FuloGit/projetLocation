package com.accenture.exception;

/**
 * Gére les exceptions levées lors de la gestion des Utilisateurs (et classes filles).
 */
public class UtilisateurException extends RuntimeException {
    public UtilisateurException(String message) {
        super(message);
    }
}
