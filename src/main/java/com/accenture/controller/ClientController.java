package com.accenture.controller;

import com.accenture.repository.Entity.Client;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDtoForClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Gére les clients
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/infos")
    ResponseEntity<ClientResponseDtoForClient> afficherInfos(@RequestParam String id, @RequestParam String password) {
        ClientResponseDtoForClient trouve = clientService.trouver(id, password);
        return ResponseEntity.ok(trouve);
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDto clientRequestDto){
        ClientResponseDtoForClient clientEnreg = clientService.ajouter(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(clientEnreg.email())
                .toUri();
        return ResponseEntity.created(location).build();
    }
@DeleteMapping
    ResponseEntity<ClientResponseDtoForClient>  suppression(@RequestParam String id, String password){
        clientService.supprimer(id,  password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

}

}
