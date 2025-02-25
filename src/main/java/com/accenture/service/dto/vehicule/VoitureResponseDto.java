package com.accenture.service.dto.vehicule;

import com.accenture.shared.model.Carburant;
import com.accenture.shared.model.NombresDePortes;
import com.accenture.shared.model.Permis;
import com.accenture.shared.model.Transmission;

public record VoitureResponseDto(
        Long id,
        String marque,
        String modele,
        String couleur,
        int nombreDePlaces,
        Carburant carburant,
        NombresDePortes nombresDePortes,
        Transmission transmission,
        Boolean climatisation,
        int nombredeBagages,
        Permis permis
) {
}
