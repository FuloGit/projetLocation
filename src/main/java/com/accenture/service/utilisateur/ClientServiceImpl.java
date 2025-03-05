package com.accenture.service.utilisateur;


import com.accenture.exception.UtilisateurException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.service.dto.utilisateur.AdresseDto;
import com.accenture.service.dto.utilisateur.ClientRequestDto;
import com.accenture.service.dto.utilisateur.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.accenture.service.dto.utilisateur.UtilMessage.*;

/**
 * Implémentation de l'interface Client service
 */
@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    public static final String VERIFIER_ADRESSE_ADRESSE_DTO = "verifierAdresse (adresseDto) : {} ";
    public static final String VERIFIER_CLIENT_CLIENT_REQUEST_DTO = "verifierClient (clientRequestDto) : {} ";
    ClientMapper clientMapper;
    private ClientDao clientDAO;


    /**
     * Renvoie un ClientResponseDto après avoir vérifier la requete et appeler save()
     *
     * @param clientRequestDto Les informations données par l'utilisateur sont envoyés sous forme de RequestDto
     * @return Remonte le client enregistré sous forme de ResponseDto
     * @throws UtilisateurException si l'email existe déjà dans la base de donnée
     */
    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) {
        verifierClient(clientRequestDto);
        Optional<Client> optionalClient = clientDAO.findById(clientRequestDto.email());
        if (optionalClient.isPresent()) {
            UtilisateurException utilisateurException = new UtilisateurException("Email déjà utilisé");
            log.error("ajouterClient : {} ",  utilisateurException.getMessage());
            throw utilisateurException;
        }

        return clientMapper.toClientResponseDtoForCLient(clientDAO.save(clientMapper.toClient(clientRequestDto)));
    }

    /**
     * Renvoie le clientResponseDto correspondant à l'Id après avoir vérifier la requete utilisateur
     *
     * @param id
     * @return ClientResponseDto
     * @throws UtilisateurException dans le cas où l'email n'existe pas en base
     */
    @Override
    public ClientResponseDto trouverParId(String id, String password) throws UtilisateurException {
        return clientMapper.toClientResponseDtoForCLient(verifierPasswordClient(id, password));
    }


    /**
     * Appel la méthode deleteById() après vérification de la requete
     *
     * @param id
     * @param password
     * @throws UtilisateurException si la vérification échoue
     */
    @Override
    public void supprimerParId(String id, String password) throws UtilisateurException {
        verifierPasswordClient(id, password);
        clientDAO.deleteById(id);
    }

    /**
     * Méthode Patch pour le client qui modifie les attribues d'un Client entity
     *
     * @param id
     * @param password
     * @param clientRequestDto
     * @return ClientResponse Dto
     * @throws UtilisateurException si la vérification échoue. Renvoie aussi si le nouveau Client entity ne correspond pas aux vérifications
     */
    @Override
    public ClientResponseDto modifier(String id, String password, ClientRequestDto clientRequestDto) throws UtilisateurException {
        Client clientEnBase = verifierPasswordClient(id, password);
        Client clientModifier = clientMapper.toClient(clientRequestDto);
        remplace(clientModifier, clientEnBase);
        verifierClient(clientMapper.toClientRequestDto(clientEnBase));
        Client clientEnregistrer = clientDAO.save(clientEnBase);
        return clientMapper.toClientResponseDtoForCLient(clientEnregistrer);
    }

    /**
     * @return
     */
    @Override
    public List<ClientResponseDto> trouverTous() {
        return clientDAO.findAll().stream().map(client -> clientMapper.toClientResponseDtoForCLient(client)).toList();
    }

    private void remplace(Client clientModifier, Client clientEnBase) {
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
        if (clientModifier.getAdresse() != null) {
            if (clientModifier.getAdresse().getVille() != null)
                clientEnBase.getAdresse().setVille(clientModifier.getAdresse().getVille());
            if (clientModifier.getAdresse().getRue() != null)
                clientEnBase.getAdresse().setRue(clientModifier.getAdresse().getRue());
            if (clientModifier.getAdresse().getCodePostal() != null)
                clientEnBase.getAdresse().setCodePostal(clientModifier.getAdresse().getCodePostal());
        }
    }

    private static void verifierAdresse(AdresseDto adresse) {
        if (adresse == null) {
            UtilisateurException utilisateurException = new UtilisateurException("L'adresse est obligatoire");
            log.error(VERIFIER_ADRESSE_ADRESSE_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        if (adresse.rue() == null || adresse.rue().isBlank())
        {UtilisateurException utilisateurException = new UtilisateurException("La rue est obligatoire");
            log.error(VERIFIER_ADRESSE_ADRESSE_DTO, utilisateurException.getMessage());
        throw utilisateurException;}
        if (adresse.ville() == null || adresse.ville().isBlank())
        {UtilisateurException utilisateurException = new UtilisateurException("La ville est obligatoire");
            log.error(VERIFIER_ADRESSE_ADRESSE_DTO, utilisateurException.getMessage());
            throw utilisateurException;}
        if (adresse.codePostal() == null || adresse.codePostal().isBlank())
        {UtilisateurException utilisateurException = new UtilisateurException("Le code postal est obligatoire");
            log.error(VERIFIER_ADRESSE_ADRESSE_DTO, utilisateurException.getMessage());
            throw utilisateurException;}
    }


    private static void verifierClient(ClientRequestDto clientRequestDto) {
        if (clientRequestDto == null) {
            UtilisateurException utilisateurException = new UtilisateurException("La requete est null");
            log.error(VERIFIER_CLIENT_CLIENT_REQUEST_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        if (clientRequestDto.email() == null || clientRequestDto.email().isBlank()) {
            UtilisateurException utilisateurException = new UtilisateurException("L'adresse Email est obligatoire");
            log.error(VERIFIER_CLIENT_CLIENT_REQUEST_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        if (clientRequestDto.nom() == null || clientRequestDto.nom().isBlank()) {
            UtilisateurException utilisateurException = new UtilisateurException("Le nom est obligatoire");
            log.error(VERIFIER_CLIENT_CLIENT_REQUEST_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        if (clientRequestDto.prenom() == null || clientRequestDto.prenom().isBlank()) {
            UtilisateurException utilisateurException = new UtilisateurException("Le prenom est obligatoire");
            log.error(VERIFIER_CLIENT_CLIENT_REQUEST_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        if (clientRequestDto.dateDeNaissance() == null) {
            UtilisateurException utilisateurException = new UtilisateurException("La date de naissance est obligatoire");
            log.error(VERIFIER_CLIENT_CLIENT_REQUEST_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        if ((clientRequestDto.dateDeNaissance().plusYears(18).isAfter(LocalDate.now()))) {
            UtilisateurException utilisateurException = new UtilisateurException("Vous devez être majeur pour vous inscrire");
            log.error(VERIFIER_CLIENT_CLIENT_REQUEST_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        if ((!clientRequestDto.email().matches(EMAIL_REGEX))) {
            UtilisateurException utilisateurException = new UtilisateurException("L'adresse email doit être valide");
            log.error(VERIFIER_CLIENT_CLIENT_REQUEST_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        if (clientRequestDto.password() == null || clientRequestDto.password().isBlank() || (!clientRequestDto.password().matches(PASSWORD_REGEX))) {
            UtilisateurException utilisateurException = new UtilisateurException("Le mot de passe ne respecte pas les conditions");
            log.error(VERIFIER_CLIENT_CLIENT_REQUEST_DTO, utilisateurException.getMessage());
            throw utilisateurException;
        }
        verifierAdresse(clientRequestDto.adresse());
    }

    private Client verifierPasswordClient(String id, String password) {
        Optional<Client> optionalClient = clientDAO.findById(id);
        if (optionalClient.isEmpty() || !optionalClient.get().getPassword().equals(password)) {
            log.error("verifierPasswordClient : {} ", EMAIL_OU_PASSWORD_ERRONE);
            throw new EntityNotFoundException(EMAIL_OU_PASSWORD_ERRONE);
        }
        return optionalClient.get();
    }
}
