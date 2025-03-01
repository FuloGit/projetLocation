package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.Entity.utilisateur.Adresse;
import com.accenture.repository.Entity.utilisateur.Client;
import com.accenture.service.dto.utilisateur.*;
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


import static com.accenture.shared.model.Permis.A1;
import static com.accenture.shared.model.Permis.B1;
import static org.junit.jupiter.api.Assertions.*;

//TODO relire et vérifier synthaxe
@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {


    @Mock
    ClientDao clientDAOMock;
    @Mock
    ClientMapper clientMapperMock;
    @InjectMocks
    ClientServiceImpl clientService;

    @DisplayName("""
            Test de la méthode ajouter si null lui est passé""")
    @Test
    void ajouterNull() {

        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(null));
        assertEquals("La requete est null", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut email est null
            """)
    @Test
    void ajouterAvecEmailNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto(null, "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("L'adresse Email est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'excepetion levée, de la méthode ajouter si l'eamil préciser sert déjà d'id en base
            """)
    @Test
    void ajouterAvecEmailDejaEnBase() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        Client client = creactionClient();
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(client));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Email déjà utilisé", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée, de la Méthode ajouter si l'attribut email est blank
            """)
    @Test
    void ajouterAvecEmailBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("L'adresse Email est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'expection levée,si la Méthode ajouter si l'attribut email de correspond pas à la RegexEmail
            """)
    @Test
    void ajouterAvecEmailNotMatchingRegex() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Prout", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("L'adresse email doit être valide", ex.getMessage());
    }

    @DisplayName("""
            Test l'excepetion levée de la méthode ajouter si l'attribut password ne match pas la Regex
            """)
    @Test
    void ajouterAvecPasswordNotMatchingRegex() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le mot de passe ne respecte pas les conditions", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut mot de pass est null
            """)
    @Test
    void ajouterAvecPasswordNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", null, "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le mot de passe ne respecte pas les conditions", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de  Méthode ajouter si l'attribut mot de pass est blank
            """)
    @Test
    void ajouterAvecPasswordBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le mot de passe ne respecte pas les conditions", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la  Méthode ajouter si la date de DateDeNaissance est Null
            """)
    @Test
    void ajouterAvecNaissanceNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, null, List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("La date de naissance est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut date de naissance est valorisé en sorte que la client soit mineur
            """)
    @Test
    void ajouterAvecNaissanceMineur() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(2023, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Vous devez être majeur pour vous inscrire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut prenom is null
            """)
    @Test
    void ajouterAvecPrenomNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", null, adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le prenom est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut prénom est Blank
            """)
    @Test
    void ajouterAvecPrenomBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le prenom est obligatoire", ex.getMessage());

    }

    @DisplayName("""
            Test l'exception levée de la  méthode ajouter si l'attribut nom est null
            """)
    @Test
    void ajouterAvecNomNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", null, "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le nom est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut nom est blank
            """)
    @Test
    void ajouterAvecNomBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", " ", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le nom est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée méthode ajouter si l'attribut adresse est null
            """)
    @Test
    void ajouterAdresseNull() {
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", null, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("L'adresse est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de méthode ajouter si l'attribut rue de Adresse est null
            """)
    @Test
    void ajouterAvecRueNull() {
        AdresseDto adresseRequestDto = new AdresseDto(null, "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("La rue est obligatoire", ex.getMessage());

    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut rue de Adresse est blank
            """)
    @Test
    void ajouterAvecRueBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("La rue est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception lévée de la méthode ajouter si l'attribut code postal de Adresse est null
            """)
    @Test
    void ajouterAvecCodePostalNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", null, "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le code postal est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'excption levée de la méthode ajouter si l'attribut  code postal de l'Adresse est blank
            """)
    @Test
    void ajouterAvecCodePostalBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("Le code postal est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut ville de Adresse est null
            """)
    @Test
    void ajouterAvecVilleNull() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", null);
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
       ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
       assertEquals("La ville est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut ville de Adresse est blank
            """)
    @Test
    void ajouterAvecVilleBlank() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1));
        ClientException ex = assertThrows(ClientException.class, () -> clientService.ajouter(clientRequestDto));
        assertEquals("La ville est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Vérifie Si ajouter(ClientRequestDto Ok) alors save() est appelé et un ClientResponseDto renvoyé
            """)
    @Test
    void TestAjouterOK() {
        ClientRequestDto clientRequestDto = creationClientRequestDto();
        Client clientAvantEnreg = creactionClient();
        Client clientApresEnreg = creactionClient();
        ClientResponseDto clientResponseDtoForClient = creationClientResponseDto();
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
    void trouverParidExistePas() {
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.trouverParId("Pierre@yahoo.fr", "Rsgfssfd2@"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie méthode trouver(id) qui doit renvoyer une EntityNotFoundException lorsque le password est invalid
            """)
    @Test
    void trouverParIdPassWordInvalid() {
        Client client = creactionClient();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(client));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.trouverParId("Pierre@yahoo.fr", "Rsgfssfd"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }


    @DisplayName("""
            Verifie trouver(id) renvoie un ClientResponseDto
            """)
    @Test
    void trouverParIdExiste() {
        Client client = creactionClient();
        Optional<Client> optionalClient = Optional.of(client);
        ClientResponseDto clientResponseDtoForClient = creationClientResponseDto();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(optionalClient);
        Mockito.when(clientMapperMock.toClientResponseDtoForCLient(client)).thenReturn(clientResponseDtoForClient);
        assertSame((clientResponseDtoForClient), clientService.trouverParId("Pierre@yahoo.fr", "Rsgfssfd2@"));
    }

    @DisplayName("""
            Test l'exception levée de la méthode supprimerParId(id) si l'id ne corresponde à aucun client
            """)
    @Test
    void supprimerParIdOptionnalVide() {
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.supprimerParId("Pierre@yahoo.fr", "Rsgfssfd2@"));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie methode supprimer si Password invalid, renvoie ClientException
            """)
    @Test
    void supprimerParIdPassWordInvalid() {
        Client pierre = creactionClient();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(pierre));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.supprimerParId("Pierre@yahoo.fr", "Rsgfssfd"));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie methode supprimer fonctionne correctement et passe par deleteById
            """)
    @Test
    void supprimerOk() {
        Client pierre = creactionClient();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(pierre));
        clientService.supprimerParId("Pierre@yahoo.fr", "Rsgfssfd2@");
        Mockito.verify(clientDAOMock).deleteById("Pierre@yahoo.fr");
    }

    @DisplayName("""
            Vérifie si la méthode modifie renvoie une ClientException si l'Email n'est pas en base
            """)
    @Test
    void modifierAvecEmailNull() {
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.empty());
        ClientRequestDto clientRequestDto = creationClientRequestDto();
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.modifier("Pierre@yahoo.fr", "Rsgfssfd2@", clientRequestDto));
        assertEquals("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Vérifie si la méthode modifie renvoie une CLientException si le password est invalide
            """)
    @Test
    void modifierAvecPasswordInvalid() {
        Client client = creactionClient();
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(client));
        ClientRequestDto clientRequestDto = creationClientRequestDto();
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.modifier("Pierre@yahoo.fr", "Rsfssfd2", clientRequestDto));
        assertEquals("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Vérifie si la méthode modifier passe bien par save() si tout se passe correctement.
            """)
    @Test
    void modifierSuccess() {
        ClientRequestDto clientRequestDto = creationClientRequestDto();
        Client clientAvantEnreg = creactionClient();
        Client clientApresEnreg = creactionClient();
        AdresseDto adresseResponseDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        List<Permis> permis = new ArrayList<>();
        ClientResponseDto clientResponseDto = new ClientResponseDto("Pierre", "Pierre", "Pierre@yahoo.fr", adresseResponseDto, LocalDate.of(1990, 12, 12), LocalDate.of(1990, 12, 12), permis);
        Mockito.when(clientDAOMock.findById("Pierre@yahoo.fr")).thenReturn(Optional.of(clientAvantEnreg));
        Mockito.when(clientMapperMock.toClient(clientRequestDto)).thenReturn(clientAvantEnreg);
        Mockito.when(clientDAOMock.save(clientAvantEnreg)).thenReturn(clientApresEnreg);
        Mockito.when(clientMapperMock.toClientRequestDto(clientAvantEnreg)).thenReturn(clientRequestDto);
        Mockito.when(clientMapperMock.toClientResponseDtoForCLient(clientApresEnreg)).thenReturn(clientResponseDto);
        assertSame(clientResponseDto, clientService.modifier("Pierre@yahoo.fr", "Rsgfssfd2@", clientRequestDto));
        Mockito.verify(clientDAOMock).save(clientAvantEnreg);
    }


    private static ClientRequestDto creationClientRequestDto() {
        AdresseDto adresseRequestDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        ClientRequestDto clientRequestDto = new ClientRequestDto("Pierre@yahoo.fr", "Rsgfssfd2@", "Pierre", "Pierre", adresseRequestDto, LocalDate.of(1990, 12, 12), List.of(A1, B1));
        return clientRequestDto;
    }

    private static ClientResponseDto creationClientResponseDto() {
        AdresseDto adresseResponseDto = new AdresseDto("Lelasseur", "44300", "Nantes");
        List<Permis> permis = new ArrayList<>();
        ClientResponseDto clientResponseDto = new ClientResponseDto("Pierre", "Pierre", "Pierre@yahoo.fr", adresseResponseDto, LocalDate.of(1990, 12, 12), LocalDate.of(1990, 12, 12), permis);
        return clientResponseDto;
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
