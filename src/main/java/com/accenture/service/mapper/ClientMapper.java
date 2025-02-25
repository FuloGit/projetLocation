package com.accenture.service.mapper;

import com.accenture.repository.Entity.utilisateur.Client;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AdresseMapper.class)
public interface ClientMapper {

    Client toClient (ClientRequestDto clientRequestDto);
    ClientResponseDto toClientResponseDtoForCLient (Client client);
}
