package com.accenture.controller.vehicule;

import com.accenture.service.dto.vehicule.VehiculeDto;

import com.accenture.service.vehicule.VehiculeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vehicules")
@AllArgsConstructor
@Tag(name = "Gestion du parc de véhicule", description = "Interface de Gestion")
public class VehiculeController {

    private VehiculeService vehiculeServiceImpl;

    @GetMapping
    VehiculeDto rechercherTous() {

        return vehiculeServiceImpl.trouverTous();

    }
}
