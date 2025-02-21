package com.accenture.service;

import com.accenture.repository.Entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDtoForClient;

import java.util.List;

/**
 * <p>Interface comprenant les méthodes à implémenter</p>
 */
public interface ClientService {
    ClientResponseDtoForClient ajouter(ClientRequestDto clientRequestDto);

    ClientResponseDtoForClient trouver(String id);

}
