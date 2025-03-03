package com.accenture.service.dto.vehicule;

import com.accenture.shared.model.Carburant;
import com.accenture.shared.model.NombresDePortes;
import com.accenture.shared.model.Transmission;
import com.accenture.shared.model.TypeVoiture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoitureRequestDto(
        @NotBlank (message = "La marque est obligatoire")
        String marque,
        @NotBlank (message = "Le modèle est obligatoire")
        String modele,
        @NotBlank (message = "La couleur est obligatoire")
        String couleur,
        @NotNull (message = "Le nombre de places est obligatoire")
        Integer nombreDePlaces,
        @NotNull (message = "Le carburant est obligatoire")
        Carburant carburant,
        @NotNull (message = "Le type de voiture est obligatoire")
        TypeVoiture typeVoiture,
        @NotNull (message = "Le nombre de portes est obligatoire")
        NombresDePortes nombresDePortes,
        @NotNull (message = "Le type de transmission est obligatoire")
        Transmission transmission,
        @NotNull (message = "Le statut de la climatisation est obligatoire")
        Boolean climatisation,
        @NotNull (message = "Le nombre de bagages est obligatoire")
        Integer nombredeBagages,
        @NotNull (message = "le tarif est obligatoire")
        Integer tarifJournalier,
        @NotNull (message = "Le kilomètrage est obligatoire")
        Integer kilometrage,
        @NotNull (message = "Le véhicule doit être actif ou inactif")
        Boolean actif,
        @NotNull (message = "Précisez si le véhicule est retiré ou non du parc")
        Boolean retireDuParc
) {
}
