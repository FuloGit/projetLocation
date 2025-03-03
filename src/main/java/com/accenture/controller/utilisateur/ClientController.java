package com.accenture.controller.utilisateur;

import com.accenture.service.utilisateur.ClientService;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Gére le mapping pour Clients
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/infos")
    ResponseEntity<ClientResponseDto> afficher(@RequestParam String id, @RequestParam String password) {
        ClientResponseDto trouve = clientService.trouverParId(id, password);
        return ResponseEntity.ok(trouve);
    }
    @GetMapping
    List<ClientResponseDto> rechercherTous(){
        return clientService.trouverTous();
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody @Valid ClientRequestDto clientRequestDto){
        ClientResponseDto clientEnreg = clientService.ajouter(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(clientEnreg.email())
                .toUri();
        return ResponseEntity.created(location).build();
    }
@DeleteMapping
    ResponseEntity<ClientResponseDto>  supprimer(@RequestParam String id, String password){
        //TODO changer une fois la notion de location
        clientService.supprimerParId(id,  password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}

@PatchMapping
ResponseEntity<ClientResponseDto> modifier(@RequestParam String id, @RequestParam String password, @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto reponse = clientService.modifier(id, password, clientRequestDto);
        return ResponseEntity.ok(reponse);
}
}
