package com.accenture.service.dto.vehicule;

import com.accenture.shared.model.TypeVelo;


/**
 *
 * @param id
 * @param marque
 * @param modele
 * @param couleur
 * @param tarifJournalier
 * @param kilometrage
 * @param actif
 * @param retireDuParc
 * @param tailleDuCadre
 * @param poids
 * @param electrique
 * @param autonomie
 * @param freinADisque
 * @param typeVelo
 */
public record VeloResponseDto(
        Long id,
        String marque,
        String modele,
        String couleur,
        Integer tarifJournalier,
        Integer kilometrage,
        Boolean actif,
        Boolean retireDuParc,
        Integer tailleDuCadre,
        Integer poids,
        Boolean electrique,
        Integer autonomie,
        Boolean freinADisque,
        TypeVelo typeVelo) {
}
