package com.accenture.service.dto.vehicule;

import com.accenture.shared.model.Carburant;
import com.accenture.shared.model.NombresDePortes;
import com.accenture.shared.model.Transmission;

public record VoitureRequestDto(
        String marque,
        String modele,
        String couleur,
        Integer nombreDePlaces,
        Carburant carburant,
        NombresDePortes nombresDePortes,

        Transmission transmission,
        Boolean climatisation,
        Integer nombredeBagages,
        Integer tarifJournalier,
        Integer kilometrage,
        Boolean actif,
        Boolean retireDuParc
) {
}
