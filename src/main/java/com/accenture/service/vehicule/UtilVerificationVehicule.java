package com.accenture.service.vehicule;

import com.accenture.exception.VehiculeException;

public class UtilVerificationVehicule {

    static void verifierStringNotNullNotEmpty(String string, String message){
        if (string == null || string.isBlank())
            throw new VehiculeException(message);
    }
    static void verifierIntegerNotNullNotZeroOrInferior(Integer integer, String message){
        if (integer == null || integer <= 0)
            throw new VehiculeException((message));
    }

    static void verifierObjectNotNull(Object object, String message){
        if (object == null){
            throw new VehiculeException(message);
        }
    }

}
