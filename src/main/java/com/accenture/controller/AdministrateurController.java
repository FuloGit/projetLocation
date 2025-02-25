package com.accenture.controller;

import com.accenture.service.AdministrateurService;
import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.utilisateur.AdministrateurResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Gére les inputs pour Administrateur
 */
@RestController
@RequestMapping("/Administrateurs")
public class AdministrateurController {

    private AdministrateurService administrateurService;

    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }


    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody AdministrateurRequestDto administrateurRequestDto) {
        AdministrateurResponseDto administrateurResponseDto = administrateurService.ajouter(administrateurRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(administrateurResponseDto.email())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/infos")
    ResponseEntity<AdministrateurResponseDto> afficherInfos(@RequestBody String id, @RequestBody String password) {
        AdministrateurResponseDto administrateurResponseDto = administrateurService.trouver(id, password);
        return ResponseEntity.ok(administrateurResponseDto);
    }

    @DeleteMapping
    ResponseEntity<AdministrateurResponseDto> suppression(@RequestParam String id, String password) {
        administrateurService.supprimer(id, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping
    ResponseEntity<AdministrateurResponseDto> modifier(@RequestParam String id,@RequestParam String password, @RequestBody AdministrateurRequestDto administrateurRequestDto) {
        AdministrateurResponseDto reponse = administrateurService.modifier(id, password, administrateurRequestDto);
        return ResponseEntity.ok(reponse);
    }
}
