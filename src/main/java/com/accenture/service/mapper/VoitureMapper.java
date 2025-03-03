package com.accenture.service.mapper;

import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring")
public interface VoitureMapper {

    VoitureResponseDto toVoitureResponseDto (Voiture voiture);
    Voiture toVoiture (VoitureRequestDto voitureRequestDto);
    VoitureRequestDto toVoitureRequestDto(Voiture voiture);
}
