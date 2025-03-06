package com.accenture.controller.utilisateur;

import com.accenture.service.utilisateur.AdministrateurService;
import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.utilisateur.AdministrateurResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Gére les mapping et les end point pour Administrateurs, inclus les méthodes ajouter, afficher, supprimer, modifier
 */
@Slf4j
@RestController
@RequestMapping("/administrateurs")
@Tag(name = "Gestion des Administrateurs", description = "Interface de gestion des Administrateurs")
public class AdministrateurController {

    private AdministrateurService administrateurService;

    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }



    @Operation(summary = "Ajouter un Administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrateur ajouté"),
            @ApiResponse(responseCode = "400", description = "Ajout impossible")
    })
    @PostMapping
    ResponseEntity<Void> ajouter(@Valid
                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                         description = "Administrateur Création", required = true,
                                         content = @Content(mediaType = "application/json",
                                                 schema = @Schema(implementation = AdministrateurRequestDto.class),
                                                 examples = @ExampleObject(value = """
                                                         {
                                                           "email": "Tian.Huong@Gmail.fr",
                                                           "password": "FAZDd32fsd@",
                                                           "nom": "Huong",
                                                           "prenom": "Tian",
                                                           "fonction" : "CEO"
                                                         }
                                                         """
                                                 )))@RequestBody  AdministrateurRequestDto administrateurRequestDto) {
        AdministrateurResponseDto administrateurResponseDto = administrateurService.ajouter(administrateurRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(administrateurResponseDto.email())
                .toUri();
        log.info("Ajout Administrateur : {} " , administrateurResponseDto);
        return ResponseEntity.created(location).build();
    }


    @Operation(summary = "Afficher un Administrateur")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrateur trouvé"),
        @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
})
    @GetMapping("/infos")
    ResponseEntity<AdministrateurResponseDto> afficher(@RequestParam String id,  @RequestParam String password) {
        AdministrateurResponseDto administrateurResponseDto = administrateurService.trouverParId(id, password);
        log.info("Afficher Administrateur : {} ", administrateurResponseDto);
        return ResponseEntity.ok(administrateurResponseDto);
    }



    @Operation(summary = "Supprimer un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrateur supprimé"),
            @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
    })
    @DeleteMapping
    ResponseEntity<AdministrateurResponseDto> supprimer(@RequestParam String id, String password) {
        administrateurService.supprimerParid(id, password);
        log.info("Suppression Administrateur : {} " , id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @Operation(summary = "Modifier un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrateur modifié"),
            @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
    })
    @PatchMapping
    ResponseEntity<AdministrateurResponseDto> modifier(@RequestParam String id, @RequestParam String password, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Administrateur qui modifie", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdministrateurRequestDto.class),
                    examples = @ExampleObject(value = """
                                                         {
                                                           "email": "Tian.Huong@Gmail.fr",
                                                           "password": "FAZDd32fsd@",
                                                           "nom": "Huong",
                                                           "prenom": "Tian",
                                                           "fonction" : "CEO"
                                                         }
                                                         """)))@RequestBody AdministrateurRequestDto administrateurRequestDto) {
        AdministrateurResponseDto reponse = administrateurService.modifier(id, password, administrateurRequestDto);
        log.info("Modification Administrateur : {} ", reponse);
        return ResponseEntity.ok(reponse);
    }
}
