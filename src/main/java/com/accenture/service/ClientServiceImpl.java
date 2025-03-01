package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.Entity.utilisateur.Client;
import com.accenture.service.dto.utilisateur.AdresseDto;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.accenture.service.dto.utilisateur.UtilMessage.*;
/**
 * Implémentation de l'interface Client service
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    ClientMapper clientMapper;
    private ClientDao clientDAO;


    /**
     * Renvoie un ClientResponseDto après avoir vérifier la requete et appeler save()
     * @param clientRequestDto Les informations données par l'utilisateur sont envoyés sous forme de RequestDto
     * @return Remonte le client enregistré sous forme de ResponseDto
     * @throws ClientException si l'email existe déjà dans la base de donnée
     */
    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) {
        verifierClient(clientRequestDto);
        verifierAdresse(clientRequestDto.adresse());
        Optional<Client> optionalClient = clientDAO.findById(clientRequestDto.email());
        if (optionalClient.isPresent())
            throw new ClientException("Email déjà utilisé");
        return clientMapper.toClientResponseDtoForCLient(clientDAO.save(clientMapper.toClient(clientRequestDto)));
    }

    /**
     * Renvoie le clientResponseDto correspondant à l'Id après avoir vérifier la requete utilisateur
     * @param id
     * @return ClientResponseDto
     * @throws ClientException dans le cas où l'email n'existe pas en base
     */
    @Override
    public ClientResponseDto trouverParId(String id, String password) throws ClientException {
        Optional<Client> optionalClient = verifierPasswordClient(id, password);
        return clientMapper.toClientResponseDtoForCLient(optionalClient.get());
    }



    /**
     * Appel la méthode deleteById() après vérification de la requete
     * @param id
     * @param password
     * @throws ClientException si la vérification échoue
     */
    @Override
    public void supprimerParId(String id, String password)  throws ClientException {
        verifierPasswordClient(id, password);
        clientDAO.deleteById(id);
    }

    /**
     * Méthode Patch pour le client qui modifie les attribues d'un Client Entity
     * @param id
     * @param password
     * @param clientRequestDto
     * @return ClientResponse Dto
     * @throws ClientException si la vérification échoue. Renvoie aussi si le nouveau Client Entity ne correspond pas aux vérifications
     */
    @Override
    public ClientResponseDto modifier(String id, String password, ClientRequestDto clientRequestDto) throws ClientException {
        Optional<Client> optionalClient = verifierPasswordClient(id, password);
        Client clientEnBase = optionalClient.get();
        Client clientModifier = clientMapper.toClient(clientRequestDto);
        remplace(clientModifier, clientEnBase);
        verifierClient(clientMapper.toClientRequestDto(clientEnBase));
        Client clientEnregistrer = clientDAO.save(clientEnBase);
        return clientMapper.toClientResponseDtoForCLient(clientEnregistrer);
    }

    private void remplace(Client clientModifier, Client clientEnBase){
        if (clientModifier.getPassword() != null)
            clientEnBase.setPassword(clientModifier.getPassword());
        if (clientModifier.getNom() != null)
            clientEnBase.setNom(clientModifier.getNom());
        if (clientModifier.getPrenom() != null)
            clientEnBase.setPrenom(clientModifier.getPrenom());
        if (clientModifier.getDateDeNaissance() != null)
            clientEnBase.setDateDeNaissance(clientModifier.getDateDeNaissance());
        if (clientModifier.getPermis() != null)
            clientEnBase.setPermis(clientModifier.getPermis());
        if (clientModifier.getAdresse() != null)
        {
        if (clientModifier.getAdresse().getVille() != null)
            clientEnBase.getAdresse().setVille(clientModifier.getAdresse().getVille());
        if (clientModifier.getAdresse().getRue() != null)
            clientEnBase.getAdresse().setRue(clientModifier.getAdresse().getRue());
        if (clientModifier.getAdresse().getCodePostal() != null)
            clientEnBase.getAdresse().setCodePostal(clientModifier.getAdresse().getCodePostal());}
    }
    
    private static void verifierAdresse(AdresseDto adresse) {
        if (adresse == null) {
            throw new ClientException("L'adresse est obligatoire");}
        if (adresse.rue() == null || adresse.rue().isBlank())
            throw new ClientException("La rue est obligatoire");
        if (adresse.ville() == null || adresse.ville().isBlank())
            throw new ClientException("La ville est obligatoire");
        if (adresse.codePostal() == null || adresse.codePostal().isBlank())
            throw new ClientException("Le code postal est obligatoire");
    }

  
    private static void verifierClient(ClientRequestDto clientRequestDto)  {
        if (clientRequestDto == null) {
            throw new ClientException("La requete est null");}
        if (clientRequestDto.email() == null || clientRequestDto.email().isBlank()) {
            throw new ClientException("L'adresse Email est obligatoire");
        }
        if (clientRequestDto.nom() == null || clientRequestDto.nom().isBlank()) {
            throw new ClientException("Le nom est obligatoire");
        }
        if (clientRequestDto.prenom() == null || clientRequestDto.prenom().isBlank()) {
            throw new ClientException("Le prenom est obligatoire");
        }
        if (clientRequestDto.dateDeNaissance() == null) {
            throw new ClientException("La date de naissance est obligatoire");
        }
        if ((clientRequestDto.dateDeNaissance().plusYears(18).isAfter(LocalDate.now())))
            throw new ClientException("Vous devez être majeur pour vous inscrire");

        if ((!clientRequestDto.email().matches(EMAIL_REGEX)))
            throw new ClientException("L'adresse email doit être valide");

        if (clientRequestDto.password() == null || clientRequestDto.password().isBlank() || (!clientRequestDto.password().matches(PASSWORD_REGEX)))
            throw new ClientException("Le mot de passe ne respecte pas les conditions");
    }

    private Optional<Client> verifierPasswordClient(String id, String password) {
        Optional<Client> optionalClient = clientDAO.findById(id);
        if (optionalClient.isEmpty() || !optionalClient.get().getPassword().equals(password))
            throw new EntityNotFoundException(EMAIL_OU_PASSWORD_ERRONE);
        return optionalClient;
    }
}
