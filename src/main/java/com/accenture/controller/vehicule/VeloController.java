package com.accenture.controller.vehicule;

import com.accenture.service.dto.vehicule.VeloRequestDto;
import com.accenture.service.dto.vehicule.VeloResponseDto;
import com.accenture.service.vehicule.VeloService;
import com.accenture.shared.model.FiltreListe;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/velos")
public class VeloController {
    private VeloService veloService;

    @PostMapping
    ResponseEntity<VeloResponseDto> ajouter(@RequestBody @Valid VeloRequestDto veloRequestDto){
        VeloResponseDto veloResponseDto = veloService.ajouterVelo(veloRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(veloResponseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    List<VeloResponseDto> rechercherTous(){return  veloService.trouverTous();}
    @GetMapping("/filtre")
    List<VeloResponseDto> rechercherParFiltre(@RequestParam FiltreListe filtreListe){
        return veloService.trouverParFiltre(filtreListe);
    }
    @GetMapping("/{id}")
    ResponseEntity<VeloResponseDto> afficher (@PathVariable("id") Long id){
        VeloResponseDto veloResponseDto = veloService.trouverParId(id);
        return ResponseEntity.ok(veloResponseDto);
    }

    @DeleteMapping("/id")
    ResponseEntity<VeloResponseDto> supprimer (@PathVariable("id") Long id){
        veloService.supprimerParId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping
    ResponseEntity<VeloResponseDto> modifier(@RequestBody VeloRequestDto veloRequestDto, @RequestParam Long id){
        VeloResponseDto veloResponseDto = veloService.modifierParId(veloRequestDto, id);
        return ResponseEntity.ok((veloResponseDto));
    }

}
