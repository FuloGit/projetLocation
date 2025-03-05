package com.accenture.service.dto.vehicule;

import com.accenture.shared.model.TypeVelo;
import com.accenture.shared.model.TypeVoiture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeloRequestDto(
        @NotBlank(message = "La marque est obligatoire")
        String marque,
        @NotBlank (message = "Le modèle est obligatoire")
        String modele,
        @NotBlank (message = "La couleur est obligatoire")
        String couleur,
        @NotNull (message = "le tarif est obligatoire")
        Integer tarifJournalier,
        @NotNull (message = "Le kilomètrage est obligatoire")
        Integer kilometrage,
        @NotNull (message = "Le véhicule doit être actif ou inactif")
        Boolean actif,
        @NotNull (message = "Précisez si le véhicule est retiré ou non du parc")
        Boolean retireDuParc,
        @NotNull (message = "La taille du cadre est obligatoire")
        Integer tailleDuCadre,
        @NotNull (message = "Le poids est obligatoire")
        Integer poids,
        @NotNull (message = "Précisez si le véhicule est électrique ou non")
        Boolean electrique,
        Integer autonomie,
        @NotNull (message = " Précisez si les freins sont à disque")
        Boolean freinADisque,
        @NotNull( message = "Précisez le type du velo")
        TypeVelo typeVelo
) {
}
