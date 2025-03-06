package com.accenture.service.dto.vehicule;

import com.accenture.shared.model.*;

/**
 *
 * @param id
 * @param marque
 * @param modele
 * @param couleur
 * @param nombreDePlaces
 * @param carburant
 * @param typeVoiture
 * @param nombresDePortes
 * @param transmission
 * @param climatisation
 * @param nombredeBagages
 * @param permis
 */
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
