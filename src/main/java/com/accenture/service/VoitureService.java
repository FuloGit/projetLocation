package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.shared.model.FiltreListe;

import java.util.List;

/**
 * Interface pour l'implémentation de Voiture service
 */
public interface VoitureService {

   VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto) throws VoitureException;
   List<VoitureResponseDto> TrouverToutes();
   List<VoitureResponseDto> trouverParFiltre(FiltreListe filtreListe);

   VoitureResponseDto trouverParId(Long id);

   void supprimerParId(Long id);

   VoitureResponseDto modifier(VoitureRequestDto voitureRequestDto, Long id);
}
