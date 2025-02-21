package com.accenture.service.mapper;

import com.accenture.repository.Entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDtoForClient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AdresseMapper.class)
public interface ClientMapper {

    Client toClient (ClientRequestDto clientRequestDto);
    ClientResponseDtoForClient toClientResponseDtoForCLient (Client client);
}
