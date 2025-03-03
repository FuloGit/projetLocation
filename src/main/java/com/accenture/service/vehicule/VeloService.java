package com.accenture.service.vehicule;

import com.accenture.service.dto.vehicule.VeloRequestDto;
import com.accenture.service.dto.vehicule.VeloResponseDto;
import com.accenture.shared.model.FiltreListe;
import java.util.List;

public interface VeloService {
    VeloResponseDto ajouterVelo(VeloRequestDto veloRequestDto);
    List<VeloResponseDto> trouverTous();
    List<VeloResponseDto> trouverParFiltre(FiltreListe filtreListe);
    VeloResponseDto trouverParId(Long id);
    void supprimerParId(Long id);
    VeloResponseDto modifierParId(VeloRequestDto veloRequestDto, Long id);
}
