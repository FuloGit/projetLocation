package com.accenture.controller.vehicule;

import com.accenture.service.dto.vehicule.VehiculeDto;

import com.accenture.service.vehicule.VehiculeService;
import com.accenture.shared.model.FiltreListe;
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

    @GetMapping
    VehiculeDto rechercherTous() {
        VehiculeDto vehiculeDto = vehiculeServiceImpl.trouverTous();
        log.info("Rechercher véhicule : " + vehiculeDto);
        return vehiculeDto;

    }

    @GetMapping("/filtre")
    VehiculeDto rechercherParFiltre(@RequestParam FiltreListe filtreListe) {
        VehiculeDto vehiculeDto = vehiculeServiceImpl.trouverParFiltre(filtreListe);
        log.info("Rechercher vehicule : " + filtreListe + " : " + vehiculeDto);
        return vehiculeDto;
    }

    @GetMapping("/disponible")
    VehiculeDto rechercherParDisponibilite(@RequestParam LocalDate dateDeDebut, @RequestParam LocalDate dateDeFin) {
        VehiculeDto vehiculeDto = vehiculeServiceImpl.trouverParDate(dateDeDebut, dateDeFin);
        log.info("Rechercher disponibles : debut : " + dateDeDebut + "fin : " + dateDeFin + " : " + vehiculeDto);
        return vehiculeDto;
    }
}


