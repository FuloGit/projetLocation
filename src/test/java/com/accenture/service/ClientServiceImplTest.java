package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.Entity.Adresse;
import com.accenture.repository.Entity.Client;
import com.accenture.service.dto.AdresseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDtoForClient;
import com.accenture.service.mapper.ClientMapper;
import com.accenture.shared.model.Permis;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {


    @Mock
    ClientDao clientDAOMock;
    @Mock
    ClientMapper clientMapperMock;
    @InjectMocks
    ClientServiceImpl clientService;

    @DisplayName("""
            Test la méthode ajouter si null lui est passé""")
    @Test
    void ajouterNull() {
        assertThrows(ClientException.class, () -> clientService.ajouter(null));
    }

    @DisplayName("""
            Test Méthode ajouter si email null
            """)
    @Test
    void ajouterAvecEmailNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto(null, "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si email blank
            """)
    @Test
    void ajouterAvecEmailBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si email ne match pas la Regex
            """)
    @Test
    void ajouterAvecEmailNotMatchingRegex() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Prout", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("L'adresse email doit être valide", ex.getMessage());
    }

    @DisplayName("""
            Test Méthode ajouter si le mot de passe ne match pas la Regex
            """)
    @Test
    void ajouterAvecPasswordNotMatchingRegex() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le mot de passe ne respecte pas les conditions", ex.getMessage());
    }

    @DisplayName("""
            Test Méthode ajouter si le mot de passe est null
            """)
    @Test
    void ajouterAvecPasswordNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", null, "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si le mot de passe est null
            """)
    @Test
    void ajouterAvecPasswordBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si la date de DateDeNaissance est Null
            """)
    @Test
    void ajouterAvecNaissanceNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, null);
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("La date de naissance est absente", ex.getMessage());
    }

    @DisplayName("""
            Test méthode ajouter si la personne est mineur
            """)
    @Test
    void ajouterAvecNaissanceMineur() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(2023, 12, 12));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Vous devez être majeur pour vous inscrire", ex.getMessage());
    }

    @DisplayName("""
            Test méthode ajouter si Prenom is null
            """)
    @Test
    void ajouterAvecPrenomNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", null, adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));

    }

    @DisplayName("""
            Test méthode ajouter si Prenom is Blank
            """)
    @Test
    void ajouterAvecPrenomBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));

    }

    @DisplayName("""
            Test méthode ajouter si nom is null
            """)
    @Test
    void ajouterAvecNomNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", null, "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));

    }

    @DisplayName("""
            Test méthode ajouter si Prenom is Blank
            """)
    @Test
    void ajouterAvecNomBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", " ", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test méthode ajouter si l'adresse est Null
            """)
    @Test
    void ajouterAdresseNull() {
        AdresseDto adresseRequestDto = new AdresseDto(null, "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", null, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test méthode ajouter si la rue de l'adresse est Null
            """)
    @Test
    void ajouterAvecRueNull() {
        AdresseDto adresseRequestDto = new AdresseDto(null, "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test méthode ajouter si la rue de l'adresse est Null
            """)
    @Test
    void ajouterAvecRueBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test méthode ajouter si le code postal de l'adresse est Null
            """)
    @Test
    void ajouterAvecCodePostalNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", null, "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test méthode ajouter si le code postal de l'adresse est Blank
            """)
    @Test
    void ajouterAvecCodePostalBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test méthode ajouter si la ville de l'adresse est Null
            """)
    @Test
    void ajouterAvecVilleNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", null);
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Test méthode ajouter si la ville de l'adresse est blank
            """)
    @Test
    void ajouterAvecVilleBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
    }

    @DisplayName("""
            Si ajouter(ClientRequestDto Ok) alors save() est appelé et un ClientResponseDto renvoyé
            """)
    @Test
    void TestAjouterOK() {
        ClientRequestDto clientRequestDto = creationClientRequestDto();
        Client clientAvantEnreg = creactionClient();
        Client clientApresEnreg = creactionClient();
        ClientResponseDtoForClient clientResponseDtoForClient = creationClientResponseDtoForClient();
        Mockito.when(clientMapperMock.toClient(clientRequestDto)).thenReturn(clientAvantEnreg);
        Mockito.when(clientDAOMock.save(clientAvantEnreg)).thenReturn(clientApresEnreg);
        Mockito.when(clientMapperMock.toClientResponseDtoForCLient(clientApresEnreg)).thenReturn(clientResponseDtoForClient);
        assertSame(clientResponseDtoForClient, clientService.ajouter(clientRequestDto));
        Mockito.verify(clientDAOMock).save(clientAvantEnreg);
    }

    @DisplayName("""
            Verifie méthode trouver qui doit renvoyer une EntityNotFoundException lorsque le client n'existe pas
            """)
    @Test
    void trouverExistePas() {
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.trouver("Pierre@yahoo.fr", "Rsgfssfd2@"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie méthode trouver qui doit renvoyer une EntityNotFoundException lorsque le password est invalid
            """)
    @Test
    void trouverPassWordInvalid() {
        Client client = creactionClient();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(client));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.trouver("Pierre@yahoo.fr", "Rsgfssfd"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }


    @DisplayName("""
            Verifie méthode trouver qui doit renvoyer un Client.
            """)
    @Test
    void trouverExiste() {
        Client client = creactionClient();
        Optional<Client> optionalClient = Optional.of(client);
        ClientResponseDtoForClient clientResponseDtoForClient = creationClientResponseDtoForClient();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(optionalClient);
        Mockito.when(clientMapperMock.toClientResponseDtoForCLient(client)).thenReturn(clientResponseDtoForClient);
        assertSame((clientResponseDtoForClient), clientService.trouver("Pierre@yahoo.fr", "Rsgfssfd2@"));
    }

    @DisplayName("""
            Verifie methode supprimer si clientOptional est vide, renvoie ClientException
            """)
    @Test
    void supprimerClientOptionnalVide() {
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.empty());
        ClientException ex = assertThrows(ClientException.class, () -> clientService.supprimer("Pierre@yahoo.fr", "Rsgfssfd2@"));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie methode supprimer si Password invalid, renvoie ClientException
            """)
    @Test
    void supprimerPassWordInvalid() {
        Client pierre = creactionClient();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(pierre));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.supprimer("Pierre@yahoo.fr", "Rsgfssfd"));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie methode supprimer fonctionne correctement et passe par deleteById
            """)
    @Test
    void supprimerOk() {
        Client pierre = creactionClient();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(pierre));
        clientService.supprimer("Pierre@yahoo.fr", "Rsgfssfd2@");
        Mockito.verify(clientDAOMock).deleteById("Pierre@yahoo.fr");
    }





    private static ClientRequestDto creationClientRequestDto() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12));
        return clientRequestDto;
    }

    private static ClientResponseDtoForClient creationClientResponseDtoForClient() {
        AdresseDto adresseResponseDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        List<Permis> permis = new ArrayList<>();
        ClientResponseDtoForClient clientResponseDtoForClient = new ClientResponseDtoForClient("Pierre", "Pierre", "Pierre@yahoo.fr", adresseResponseDto, LocalDate.of(1990, 12, 12), LocalDate.of(1990, 12, 12), permis);
        return clientResponseDtoForClient;
    }


    private static Client creactionClient() {
        Client clientAvantEnreg = new Client();
        Adresse adresse = new Adresse();
        clientAvantEnreg.setAdresse(adresse);
        clientAvantEnreg.getAdresse().setCodePostal("44300");
        clientAvantEnreg.getAdresse().setVille("Nantes");
        clientAvantEnreg.getAdresse().setId(1);
        clientAvantEnreg.getAdresse().setRue("Lelasseur");
        clientAvantEnreg.setNom("Pierre");
        clientAvantEnreg.setPrenom("Pierre");
        clientAvantEnreg.setEmail("Pierre@yahoofr");
        clientAvantEnreg.setPassword("Rsgfssfd2@");
        clientAvantEnreg.setDesactive(false);
        return clientAvantEnreg;
    }


}
