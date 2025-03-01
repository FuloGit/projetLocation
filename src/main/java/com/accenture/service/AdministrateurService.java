package com.accenture.service;

import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.utilisateur.AdministrateurResponseDto;

/**
 * Interface pour l'implémentation d'Administrateur Service
 *
 */
public interface AdministrateurService {

    AdministrateurResponseDto ajouter(AdministrateurRequestDto adminRequestDto);
   AdministrateurResponseDto trouverParId(String id, String password);
   void supprimerParid(String id, String password);

    AdministrateurResponseDto modifier(String id, String password, AdministrateurRequestDto administrateurRequestDto);

}
