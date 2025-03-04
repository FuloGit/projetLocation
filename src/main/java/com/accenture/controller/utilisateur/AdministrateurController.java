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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Gére les mapping pour Administrateurs
 */
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
        return ResponseEntity.created(location).build();
    }


    @Operation(summary = "Affiche un Administrateur")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Mauvaise Requete"),
        @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
})
    @GetMapping("/infos")
    ResponseEntity<AdministrateurResponseDto> afficher(@RequestParam String id,  @RequestParam String password) {
        AdministrateurResponseDto administrateurResponseDto = administrateurService.trouverParId(id, password);
        return ResponseEntity.ok(administrateurResponseDto);
    }



    @Operation(summary = "Supprime un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrateur supprimé"),
            @ApiResponse(responseCode = "404", description = "Administrateur introuvable"),
            @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
    })
    @DeleteMapping
    ResponseEntity<AdministrateurResponseDto> supprimer(@RequestParam String id, String password) {
        administrateurService.supprimerParid(id, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @Operation(summary = "Modifie un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrateur modifié"),
            @ApiResponse(responseCode = "404", description = "Administrateur Introuvable"),
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
        return ResponseEntity.ok(reponse);
    }
}
