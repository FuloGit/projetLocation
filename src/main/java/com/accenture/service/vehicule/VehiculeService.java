package com.accenture.service.vehicule;

import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.service.dto.vehicule.VehiculeDto;
import com.accenture.shared.model.FiltreListe;

import java.time.LocalDate;
import java.util.List;

public interface VehiculeService {
    VehiculeDto trouverTous();
    VehiculeDto trouverParFiltre(FiltreListe filtreListe);
    VehiculeDto trouverParDate(LocalDate dateDedebut, LocalDate dateDeFin);
}
