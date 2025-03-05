package com.accenture.service.vehicule;

import com.accenture.service.dto.vehicule.VehiculeDto;
import com.accenture.shared.model.CategorieVehicule;
import com.accenture.shared.model.FiltreListe;
import com.accenture.shared.model.TypeVelo;
import com.accenture.shared.model.TypeVoiture;

import java.time.LocalDate;

/**
 * Interface pour l'implémentation de Vehicule service
 */
public interface VehiculeService {
    VehiculeDto trouverTous();
    VehiculeDto trouverParFiltre(FiltreListe filtreListe);
    VehiculeDto trouverParParametres(LocalDate dateDeDebut, LocalDate dateDeFin, CategorieVehicule categorieVehicule, TypeVoiture typeVehicule, TypeVelo typeVelo);

}
