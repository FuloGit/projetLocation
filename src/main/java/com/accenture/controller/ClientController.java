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
 * <p>Gére les clients</p>
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDtoForClient> client(@PathVariable("id") String id) {
        ClientResponseDtoForClient trouve = clientService.trouver(id);
        return ResponseEntity.ok(trouve);
    }

    @PostMapping("/")
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDto clientRequestDto){
        ClientResponseDtoForClient clientEnreg = clientService.ajouter(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(clientEnreg.email())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
