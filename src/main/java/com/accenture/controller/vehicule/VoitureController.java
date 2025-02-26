package com.accenture.controller.vehicule;

import com.accenture.service.VoitureService;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.shared.model.FiltreListe;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Gère les mapping pour Voitures
 */
@AllArgsConstructor
@RestController
@RequestMapping("/voitures")
public class VoitureController {

    private VoitureService voitureService;

    @PostMapping
    ResponseEntity<VoitureResponseDto> ajouter(@RequestBody @Valid VoitureRequestDto voitureRequestDto){
        VoitureResponseDto voitureResponseDto = voitureService.ajouter(voitureRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(voitureResponseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    List<VoitureResponseDto> rechercherToutes(){
        return voitureService.lister();
    }
    @GetMapping("/filtre")
    List<VoitureResponseDto> rechercherParFiltre(@RequestParam FiltreListe filtreListe) {
        return voitureService.listerParRequete(filtreListe);
    }

    @GetMapping("/{id}")
    ResponseEntity<VoitureResponseDto> afficher(@PathVariable("id") Long id){
        VoitureResponseDto voitureResponseDto = voitureService.trouver(id);
        return ResponseEntity.ok(voitureResponseDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<VoitureResponseDto> supprimer(@PathVariable ("id") Long id){
        voitureService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping
    ResponseEntity<VoitureResponseDto> modifier(@RequestBody VoitureRequestDto voitureRequestDto, @RequestParam Long id){
        VoitureResponseDto voitureResponseDto = voitureService.modifier(voitureRequestDto, id);
        return ResponseEntity.ok(voitureResponseDto);
    }



}
