package com.accenture.service.dto.vehicule;

import java.util.List;

public record VehiculeDto(

                          List<VoitureResponseDto> voitures,
List<VeloResponseDto> velos) {

}
