package com.accenture.service.vehicule;

import com.accenture.exception.VehiculeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VerificationVehiculeUtil {

    public static final String VERIFIER_VEHICULE = "vérifier Véhicule : {} ";

    static void verifierStringNotNullNotEmpty(String string, String message){
        if (string == null || string.isBlank()){
            log.error(VERIFIER_VEHICULE, message);
            throw new VehiculeException(message);}
    }
    static void verifierIntegerNotNullNotZeroOrInferior(Integer integer, String message){
        if (integer == null || integer <= 0){
            log.error(VERIFIER_VEHICULE, message);
            throw new VehiculeException((message));}
    }
    static void verifierObjectNotNull(Object object, String message){
        if (object == null){
            log.error(VERIFIER_VEHICULE, message);
            throw new VehiculeException(message);
        }
    }

    private VerificationVehiculeUtil() {}
}
