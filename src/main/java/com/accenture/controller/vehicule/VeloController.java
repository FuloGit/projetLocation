package com.accenture.controller.vehicule;

import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.vehicule.VeloRequestDto;
import com.accenture.service.dto.vehicule.VeloResponseDto;
import com.accenture.service.vehicule.VeloService;
import com.accenture.shared.model.FiltreListe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/velos")
@Tag(name = "Gestion des vélos", description = "Interface de gestion des vélos")
public class VeloController {
    private VeloService veloService;


    @Operation(summary = "Ajouter un Vélo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vélo ajouté"),
            @ApiResponse(responseCode = "400", description = "Ajout impossible")
    })
    @PostMapping
    ResponseEntity<VeloResponseDto> ajouter(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Velo Création", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdministrateurRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "marque": "Decathlon",
                              "modele": "234",
                              "couleur": "Noir",
                              "tarifJournalier": 107,
                              "kilometrage": 1073,
                              "actif": true,
                              "retireDuParc": false,
                              "tailleDuCadre": 10,
                              "poids": 10,
                              "electrique": false,
                              "autonomie": 1073741824,
                              "freinADisque": true,
                              "typeVelo": "ROUTE"
                            }
                            """
                    ))) @RequestBody @Valid VeloRequestDto veloRequestDto) {
        VeloResponseDto veloResponseDto = veloService.ajouterVelo(veloRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(veloResponseDto.id())
                .toUri();
        log.info("Ajout Vélo : "+ veloResponseDto);
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Afficher tous les vélos")
    @GetMapping
    List<VeloResponseDto> rechercherTous() {
        List<VeloResponseDto> liste = veloService.trouverTous();
        log.info("RechercherTous Vélo : "+ liste);
        return liste;
    }

    @Operation(summary = "Afficher tous les vélos selon leur disponibilité dans le parc")
    @GetMapping("/filtre")
    List<VeloResponseDto> rechercherParFiltre(@RequestParam FiltreListe filtreListe) {
        List<VeloResponseDto> liste = veloService.trouverParFiltre(filtreListe);
        log.info("RechercherParFiltre Vélo : " + filtreListe + " :" + liste);
        return liste;
    }

    @Operation(summary = "Affiche un vélo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Velo trouvé"),
            @ApiResponse(responseCode = "404", description = "Mauvaise Requete"),
            @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
    })
    @GetMapping("/{id}")
    ResponseEntity<VeloResponseDto> afficher(@PathVariable("id") Long id) {
        VeloResponseDto veloResponseDto = veloService.trouverParId(id);
        log.info("afficher Vélo : "+ veloResponseDto);
        return ResponseEntity.ok(veloResponseDto);
    }

    @Operation(summary = "Supprime un vélo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vélo supprimé"),
            @ApiResponse(responseCode = "404", description = "Vélo introuvable"),
            @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
    })
    @DeleteMapping("/id")
    ResponseEntity<VeloResponseDto> supprimer(@PathVariable("id") Long id) {
        veloService.supprimerParId(id);
        log.info("supprimer Vélo  : " + id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Modifie un vélo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vélo modifié "),
            @ApiResponse(responseCode = "404", description = "Vélo Introuvable"),
            @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
    })
    @PatchMapping
    ResponseEntity<VeloResponseDto> modifier(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Vélo Création", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdministrateurRequestDto.class),
                    examples = @ExampleObject(value = """
                              {
                              "marque": "Decathlon",
                              "modele": "234",
                              "couleur": "Noir",
                              "tarifJournalier": 107,
                              "kilometrage": 1073,
                              "actif": true,
                              "retireDuParc": false,
                              "tailleDuCadre": 10,
                              "poids": 10,
                              "electrique": false,
                              "autonomie": 1073741824,
                              "freinADisque": true,
                              "typeVelo": "ROUTE"
                            }
                            
                            """
                    ))) @RequestBody VeloRequestDto veloRequestDto, @RequestParam Long id) {
        VeloResponseDto veloResponseDto = veloService.modifierParId(veloRequestDto, id);
        log.info("modifier Vélo : " + veloRequestDto);
        return ResponseEntity.ok((veloResponseDto));
    }
}
