package com.accenture.service.mapper;


import com.accenture.repository.entity.vehicule.Velo;
import com.accenture.service.dto.vehicule.VeloRequestDto;
import com.accenture.service.dto.vehicule.VeloResponseDto;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface VeloMapper {

    Velo toVelo (VeloRequestDto veloRequestDto);
    VeloResponseDto toVeloResponseDto (Velo velo);
    VeloRequestDto toVeloRequestDto (Velo velo);
}
