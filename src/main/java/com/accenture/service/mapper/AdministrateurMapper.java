package com.accenture.service.mapper;

import com.accenture.repository.Entity.Administrateur;
import com.accenture.service.dto.AdministrateurRequestDto;
import com.accenture.service.dto.AdministrateurResponseDto;
import org.mapstruct.Mapper;


@Mapper (componentModel = "spring")
public interface AdministrateurMapper {

    AdministrateurResponseDto toAdministrateurResponseDto(Administrateur administrateur);
    Administrateur toAdministrateur (AdministrateurRequestDto administrateurRequestDto);
}
