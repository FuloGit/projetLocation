package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.repository.Entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;

import java.util.List;

public interface VoitureService {

   VoitureResponseDto ajouter(VoitureRequestDto voitureRequestDto) throws VoitureException;
   List<VoitureResponseDto> lister();
   List<VoitureResponseDto> listerActifs();
   List<VoitureResponseDto> listerInactifs();
   List<VoitureResponseDto> listerDansLeParc();
   List<VoitureResponseDto> listerRetirerDuParc();

   VoitureResponseDto trouver(Long id);

}
