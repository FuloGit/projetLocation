package com.accenture.service;

import com.accenture.service.dto.AdministrateurRequestDto;
import com.accenture.service.dto.AdministrateurResponseDto;

/**
 * <p>Interface comprenant les méthodes à implémenter</p>
 *
 */
public interface AdministrateurService {
   AdministrateurResponseDto ajouter(AdministrateurRequestDto adminRequestDto);
}
