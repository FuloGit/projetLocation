package com.accenture.service;

import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;

/**
 * Interface comprenant les méthodes à implémenter
 */
public interface ClientService {
    ClientResponseDto ajouter(ClientRequestDto clientRequestDto);

    ClientResponseDto trouver(String id, String password);

    void supprimer(String id, String password);

    ClientResponseDto modifier(String id, String password, ClientRequestDto clientRequestDto);

}
