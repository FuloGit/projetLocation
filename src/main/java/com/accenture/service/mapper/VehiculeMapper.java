package com.accenture.service.mapper;


import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.service.dto.vehicule.VeloResponseDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    VeloResponseDto toVeloResponseDto (Vehicule vehicule);
    VoitureResponseDto toVoitureResponseDto (Vehicule vehicule);
}
