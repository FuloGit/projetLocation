package com.accenture.controller.utilisateur;

import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.utilisateur.ClientService;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
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
import java.util.List;

/**
 * Gére le mapping et les end point pour Clients, inclus les méthodes ajouter, afficher, rechercherTous, modifier, supprimer
 */
@Slf4j
@RestController
@RequestMapping("/clients")
@Tag(name = "Gestions des Clients", description = "Interface de gestion des Clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Afficher un Client par email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrateur supprimer"),
            @ApiResponse(responseCode = "404", description = "Mauvaise Requete")
    })
    @GetMapping("/infos")
    ResponseEntity<ClientResponseDto> afficher(@RequestParam String id, @RequestParam String password) {
        ClientResponseDto trouve = clientService.trouverParId(id, password);
        log.info("afficher" + trouve);
        return ResponseEntity.ok(trouve);
    }

    @Operation(summary = "Afficher les clients en base")
    @GetMapping
    List<ClientResponseDto> rechercherTous() {
        List<ClientResponseDto> liste = clientService.trouverTous();
        log.info("rechercherTous() {} ", liste);
        return liste;
    }


    @Operation(summary = "Ajouter un Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client ajouté"),
            @ApiResponse(responseCode = "400", description = "Validation erreur")
    })
    @PostMapping
    ResponseEntity<Void> ajouter(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Création Client", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdministrateurRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "email": "Maleck.Dylan@gmail.com",
                              "password": "FGDGfsdf34sd@",
                              "nom": "Maleck",
                              "prenom": "Dylan",
                              "adresse": {
                                "rue": "du Marche",
                                "codePostal": "44000",
                                "ville": "Nantes"
                              },
                              "dateDeNaissance": "1999-02-24",
                              "permis": [
                                "AM", "A1"
                              ]
                            }
                            """
                    ))) @RequestBody @Valid ClientRequestDto clientRequestDto) {
        ClientResponseDto clientEnreg = clientService.ajouter(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(clientEnreg.email())
                .toUri();
        log.info("Nouveau client en base : {} ", clientEnreg);
        return ResponseEntity.created(location).build();
    }
    @Operation(summary = "Supprimer un Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client supprimé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable"),
            @ApiResponse(responseCode = "400", description = "Erreur Fonctionnelle"),
    })
    @DeleteMapping
    ResponseEntity<ClientResponseDto> supprimer(@RequestParam String id, String password) {
        clientService.supprimerParId(id, password);
        log.info("Suppression CLient : {} ", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(summary = "Modifier un Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client modifié"),
            @ApiResponse(responseCode = "400", description = "modification impossible")
    })
    @PatchMapping
    ResponseEntity<ClientResponseDto> modifier(@RequestParam String id, @RequestParam String password,@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Modification Client", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdministrateurRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "email": "Maleck.Dylan@gmail.com",
                              "password": "FGDGfsdf34sd@",
                              "nom": "Maleck",
                              "prenom": "Dylan",
                              "adresse": {
                                "rue": "du Marche",
                                "codePostal": "44000",
                                "ville": "Nantes"
                              },
                              "dateDeNaissance": "1999-02-24",
                              "permis": [
                                "AM", "A1"
                              ]
                            }
                            """
                    ))) @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto reponse = clientService.modifier(id, password, clientRequestDto);
        log.info("Nouveau client en base : {} " , reponse);
        return ResponseEntity.ok(reponse);
    }
}
