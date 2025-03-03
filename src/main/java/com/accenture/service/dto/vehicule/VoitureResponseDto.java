package com.accenture.service.dto.vehicule;

import com.accenture.shared.model.*;

import java.lang.reflect.Type;

public record VoitureResponseDto(
        Long id,
        String marque,
        String modele,
        String couleur,
        Integer nombreDePlaces,
        Carburant carburant,
        TypeVoiture typeVoiture,
        NombresDePortes nombresDePortes,
        Transmission transmission,
        Boolean climatisation,
        Integer nombredeBagages,
        Permis permis
) {
}
