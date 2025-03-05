package com.accenture.controller.vehicule;

import com.accenture.service.dto.vehicule.VehiculeDto;

import com.accenture.service.vehicule.VehiculeService;
import com.accenture.shared.model.CategorieVehicule;
import com.accenture.shared.model.FiltreListe;
import com.accenture.shared.model.TypeVelo;
import com.accenture.shared.model.TypeVoiture;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/vehicules")
@AllArgsConstructor
@Tag(name = "Gestion du parc de véhicule", description = "Interface de Gestion")
public class VehiculeController {

    private VehiculeService vehiculeServiceImpl;
@Operation(summary = "Afficher tous les véhicules")
    @GetMapping
    VehiculeDto rechercherTous() {
        VehiculeDto vehiculeDto = vehiculeServiceImpl.trouverTous();
        log.info("Rechercher véhicule : {} " , vehiculeDto);
        return vehiculeDto;

    }
@Operation(summary = "Afficher selon l'activité ou la présence dans le parc")
    @GetMapping("/filtre")
    VehiculeDto rechercherParFiltre(@RequestParam FiltreListe filtreListe) {
        VehiculeDto vehiculeDto = vehiculeServiceImpl.trouverParFiltre(filtreListe);
        log.info("Rechercher vehicule : {}", filtreListe + " : " + vehiculeDto);
        return vehiculeDto;
    }
@Operation(summary = "Afficher selon les critères de recherches")
    @GetMapping("/search")
    VehiculeDto rechercher(@RequestParam(required = false) LocalDate dateDeDebut, @RequestParam(required = false) LocalDate dateDeFin, @RequestParam(required = false) CategorieVehicule categorieVehicule, @RequestParam(required = false) TypeVoiture typeVoiture, @RequestParam(required = false)TypeVelo typeVelo) {
        VehiculeDto vehiculeDto = vehiculeServiceImpl.trouverParParametres(dateDeDebut, dateDeFin, categorieVehicule, typeVoiture, typeVelo);
        log.info("Rechercher disponibles : {}", vehiculeDto);
        return vehiculeDto;
    }

}


