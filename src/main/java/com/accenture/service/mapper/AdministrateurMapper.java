package com.accenture.service.mapper;

import com.accenture.repository.Entity.utilisateur.Administrateur;
import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.utilisateur.AdministrateurResponseDto;
import org.mapstruct.Mapper;


@Mapper (componentModel = "spring")
public interface AdministrateurMapper {

    AdministrateurResponseDto toAdministrateurResponseDto(Administrateur administrateur);
    Administrateur toAdministrateur (AdministrateurRequestDto administrateurRequestDto);
    AdministrateurRequestDto toAdministrateurRequestDto (Administrateur administrateur);
}
