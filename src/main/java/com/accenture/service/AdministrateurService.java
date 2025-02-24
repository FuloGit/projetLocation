package com.accenture.service;

import com.accenture.service.dto.AdministrateurRequestDto;
import com.accenture.service.dto.AdministrateurResponseDto;

/**
 * Interface comprenant les méthodes à implémenter
 *
 */
public interface AdministrateurService {
   AdministrateurResponseDto ajouter(AdministrateurRequestDto adminRequestDto);
   public AdministrateurResponseDto trouver(String id, String password);
    void supprimer(String id, String password);

    AdministrateurResponseDto modifier(String id, String password, AdministrateurRequestDto administrateurRequestDto);

}
