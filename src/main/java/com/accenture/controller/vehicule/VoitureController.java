package com.accenture.controller.vehicule;

import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.vehicule.VoitureService;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
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

/**
 * Gére le mapping et les end point pour la classe vélo, inclus, l'ajout, la modification, rechercherParId, rechercherTous, rechercherParFiltre, supprimer.
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/voitures")
@Tag(name = "Gestion des voitures", description = "Interface de gestion des voitures")
public class VoitureController {

    private VoitureService voitureService;

    @Operation(summary = "Ajouter une voiture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voiture ajoutée"),
            @ApiResponse(responseCode = "400", description = "Validation Erreur")
    })
    @PostMapping
    ResponseEntity<VoitureResponseDto> ajouter(@RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Voiture Création", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdministrateurRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "marque": "Ford",
                              "modele": "201",
                              "couleur": "Rouge",
                              "nombreDePlaces": 9,
                              "carburant": "DIESEL",
                              "nombresDePortes": "TROIS",
                              "transmission": "AUTO",
                              "climatisation": true,
                              "nombredeBagages": 4,
                              "tarifJournalier": 40,
                              "kilometrage": 107000,
                              "actif": true,
                              "retireDuParc": false,
                            "typeVoiture": "CITADINE"
                            }
                            """
                    ))) VoitureRequestDto voitureRequestDto) {
        VoitureResponseDto voitureResponseDto = voitureService.ajouterVoiture(voitureRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(voitureResponseDto.id())
                .toUri();
        log.info("ajouter Voiture : "+ voitureRequestDto);
        return ResponseEntity.created(location).build();
    }
    @Operation(summary = "Afficher toutes les voitures")
    @GetMapping
    List<VoitureResponseDto> rechercherToutes() {
        List<VoitureResponseDto> liste = voitureService.trouverToutes();
       log.info("rechercherToutes : " + liste);
       return liste;
    }

    @Operation(summary = "Afficher toutes les voitures selon leur disponibilité dans le parc")
    @GetMapping("/filtre")
    List<VoitureResponseDto> rechercherParFiltre(@RequestParam FiltreListe filtreListe) {
        List<VoitureResponseDto> list = voitureService.trouverParFiltre(filtreListe);
        log.info("rechercherParFiltre Voiture : " + filtreListe + ": "+ list);
        return list;
    }


    @Operation(summary = "Affiche une voiture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voiture trouvée"),
            @ApiResponse(responseCode = "404", description = "Mauvaise Requete")
    })
    @GetMapping("/{id}")
    ResponseEntity<VoitureResponseDto> afficher(@PathVariable("id") Long id) {
        VoitureResponseDto voitureResponseDto = voitureService.trouverParId(id);
        log.info("Afficher Voiture : "+ voitureResponseDto);
        return ResponseEntity.ok(voitureResponseDto);
    }


    @Operation(summary = "Supprime une voiture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voiture supprimée"),
            @ApiResponse(responseCode = "404", description = "Mauvaise Requete"),
    })
    @DeleteMapping("/{id}")
    ResponseEntity<VoitureResponseDto> supprimer(@PathVariable("id") Long id) {
        voitureService.supprimerParId(id);
        log.info("Supprimer Voiture  : "+ id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Modifie une voiture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voiture modifié "),
            @ApiResponse(responseCode = "404", description = "Voiture Introuvable"),
            @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
    })
    @PatchMapping
    ResponseEntity<VoitureResponseDto> modifier(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Voiture Création", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdministrateurRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "marque": "Ford",
                              "modele": "201",
                              "couleur": "Rouge",
                              "nombreDePlaces": 9,
                              "carburant": "DIESEL",
                              "nombresDePortes": "TROIS",
                              "transmission": "AUTO",
                              "climatisation": true,
                              "nombredeBagages": 4,
                              "tarifJournalier": 40,
                              "kilometrage": 107000,
                              "actif": true,
                              "retireDuParc": false,
                            "typeVoiture": "CITADINE"
                            }
                            """
                    ))) VoitureRequestDto voitureRequestDto, @RequestParam Long id) {
        VoitureResponseDto voitureResponseDto = voitureService.modifier(voitureRequestDto, id);
        log.info("modifier Voiture : "+ voitureResponseDto);
        return ResponseEntity.ok(voitureResponseDto);
    }


}
