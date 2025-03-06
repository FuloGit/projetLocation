package com.accenture.service.dto.vehicule;

import java.util.List;

/**
 *  Véhiculedto pour remonter à l'end point les listes des véhicules en base.
 * @param voitures Liste de voitureResponsedto
 * @param velos Liste de voitureResponsedto
 */
public record VehiculeDto(

        List<VoitureResponseDto> voitures,
        List<VeloResponseDto> velos) {

}
