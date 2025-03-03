package com.accenture.controller.vehicule;

import com.accenture.service.dto.vehicule.VehiculeDto;
import com.accenture.service.vehicule.VehiculeService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/vehicules")
@AllArgsConstructor
public class VehiculeController {

    private VehiculeService vehiculeServiceImpl;

    @GetMapping
    List<VehiculeDto> rechercherTous(){
        return vehiculeServiceImpl.trouverTous();
    }
}
