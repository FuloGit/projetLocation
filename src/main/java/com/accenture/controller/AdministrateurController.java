package com.accenture.controller;

import com.accenture.service.AdministrateurService;
import com.accenture.service.dto.AdministrateurRequestDto;
import com.accenture.service.dto.AdministrateurResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * <p>Gére les inputs pour Administrateur</p>
 */
@RestController
@RequestMapping("/Administrateurs")
public class AdministrateurController {

    private AdministrateurService administrateurService;

    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }


    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody AdministrateurRequestDto administrateurRequestDto){
        AdministrateurResponseDto administrateurResponseDto = administrateurService.ajouter(administrateurRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(administrateurResponseDto.email())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
