package com.accenture.service.vehicule;

import com.accenture.exception.VehiculeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VerificationVehiculeUtil {

    static void verifierStringNotNullNotEmpty(String string, String message){
        if (string == null || string.isBlank()){
            log.error("vérifier Véhicule : {} ", message);
            throw new VehiculeException(message);}
    }
    static void verifierIntegerNotNullNotZeroOrInferior(Integer integer, String message){
        if (integer == null || integer <= 0){
            log.error("vérifier Véhicule : {} ", message);
            throw new VehiculeException((message));}
    }
    static void verifierObjectNotNull(Object object, String message){
        if (object == null){
            log.error("vérifier Véhicule : {} ", message);
            throw new VehiculeException(message);
        }
    }

}
