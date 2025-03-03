package com.accenture.service.utilisateur;

import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;

import java.util.List;

/**
 * Interface pour l'implémentation de CLient service.
 */
public interface ClientService {
    ClientResponseDto ajouter(ClientRequestDto clientRequestDto);

    ClientResponseDto trouverParId(String id, String password);

    void supprimerParId(String id, String password);

    ClientResponseDto modifier(String id, String password, ClientRequestDto clientRequestDto);

    List<ClientResponseDto> trouverTous();
}
