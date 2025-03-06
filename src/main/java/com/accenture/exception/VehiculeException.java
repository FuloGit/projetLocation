package com.accenture.exception;

/**
 * Gére les exceptions levées lors de la gestion des Véhicules (et classes filles).
 */
public class VehiculeException extends RuntimeException {
    public VehiculeException(String message) {
        super(message);
    }
}
