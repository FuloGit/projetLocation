package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    ResponseEntity<ClientResponseDto> afficherInfos(@RequestParam String id, @RequestParam String password) {
        ClientResponseDto trouve = clientService.trouver(id, password);
        return ResponseEntity.ok(trouve);
    }

    @PostMapping
    ResponseEntity<Void> creer(@RequestBody ClientRequestDto clientRequestDto){
        ClientResponseDto clientEnreg = clientService.ajouter(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(clientEnreg.email())
                .toUri();
        return ResponseEntity.created(location).build();
    }
@DeleteMapping
    ResponseEntity<ClientResponseDto>  suppression(@RequestParam String id, String password){
        clientService.supprimer(id,  password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}

@PatchMapping
ResponseEntity<ClientResponseDto> modifier(@RequestParam String id, @RequestParam String password, @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto reponse = clientService.modifier(id, password, clientRequestDto);
        return ResponseEntity.ok(reponse);
}
}
