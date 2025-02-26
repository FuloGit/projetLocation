package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.repository.Entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.shared.model.FiltreListe;

import java.util.List;

/**
 * Interface pour l'implémentation de Voiture service
 */
public interface VoitureService {

   VoitureResponseDto ajouter(VoitureRequestDto voitureRequestDto) throws VoitureException;
   List<VoitureResponseDto> lister();
   List<VoitureResponseDto> listerParRequete (FiltreListe filtreListe);

   VoitureResponseDto trouver(Long id);

   void supprimer(Long id);

   VoitureResponseDto modifier(VoitureRequestDto voitureRequestDto, Long id);
}
