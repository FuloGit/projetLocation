package com.accenture.service.dto.vehicule;

import com.accenture.shared.model.TypeVelo;
import com.accenture.shared.model.TypeVoiture;

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
