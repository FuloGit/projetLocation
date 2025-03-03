package com.accenture.service.dto.vehicule;

public record VehiculeDto(Long id,
                          String marque,
                          String modele,
                          String couleur,
                          Integer tarifJournalier,
                          Integer kilometrage,
                          Boolean actif,
                          Boolean retireDuParc) {

}
