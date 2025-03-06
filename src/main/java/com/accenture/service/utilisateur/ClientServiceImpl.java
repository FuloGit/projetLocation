package com.accenture.service.utilisateur;


import com.accenture.exception.UtilisateurException;
import com.accenture.repository.utilisateur.ClientDao;
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

import static com.accenture.service.utilisateur.UtilisateurUtil.*;

/**
 * Implémentation de l'interface Client service
 */
@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    ClientMapper clientMapper;
    private ClientDao clientDAO;


    /**
     * Renvoie un ClientResponseDto après avoir vérifié la requete et appeler save()
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
            log.error("ajouterClient : {} ", utilisateurException.getMessage());
            throw utilisateurException;
        }

        return clientMapper.toClientResponseDto(clientDAO.save(clientMapper.toClient(clientRequestDto)));
    }

    /**
     * Renvoie le clientResponseDto correspondant à l'Id après avoir vérifié la requête utilisateur
     * @param id transmis depuis le controller
     * @return ClientResponseDto, si tout se passe bien
     * @throws UtilisateurException dans le cas où l'email n'existe pas en base
     */
    @Override
    public ClientResponseDto trouverParId(String id, String password) throws UtilisateurException {
        return clientMapper.toClientResponseDto(verifierPasswordClient(id, password));
    }


    /**
     * Appel la méthode deleteById() après vérification de la requête
     * @param id transmis depuis le controller
     * @param password transmis depuis le controller, sert à la vérification
     * @throws UtilisateurException si la vérification échoue
     */
    @Override
    public void supprimerParId(String id, String password) throws UtilisateurException {
        verifierPasswordClient(id, password);
        clientDAO.deleteById(id);
    }

    /**
     * Méthode Patch pour le client qui modifie les attributs d'un Client entity
     * @param id transmis depuis le controller
     * @param password transmis depuis le controller, sert à la vérification
     * @param clientRequestDto Les informations transmises depuis le controller pour modifier le client en base.
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
        return clientMapper.toClientResponseDto(clientEnregistrer);
    }

    /**
     * Rechercher les clients en base de données puis les map sous forme de liste.
     * @return List<ClientResponse
     */
    @Override
    public List<ClientResponseDto> trouverTous() {
        return clientDAO.findAll().stream().map(client -> clientMapper.toClientResponseDto(client)).toList();
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
        UtilisateurUtil.verifierObjectNotNull(adresse, "L'adresse est obligatoire");
        UtilisateurUtil.verifierStringNotNullNotEmpty(adresse.rue(), "La rue est obligatoire");
        UtilisateurUtil.verifierStringNotNullNotEmpty(adresse.ville(), "La ville est obligatoire");
        UtilisateurUtil.verifierStringNotNullNotEmpty(adresse.codePostal(), "Le code postal est obligatoire");


    }


    private static void verifierClient(ClientRequestDto clientRequestDto) {
        UtilisateurUtil.verifierObjectNotNull(clientRequestDto, "La requete est null");
        UtilisateurUtil.verifierStringNotNullNotEmpty(clientRequestDto.email(), "L'adresse Email est obligatoire");
        UtilisateurUtil.verifierStringNotNullNotEmpty(clientRequestDto.nom(), "Le nom est obligatoire");
        UtilisateurUtil.verifierStringNotNullNotEmpty(clientRequestDto.prenom(), "Le prenom est obligatoire");
        UtilisateurUtil.verifierObjectNotNull(clientRequestDto.dateDeNaissance(), "La date de naissance est obligatoire");
        UtilisateurUtil.verifierEmailMatchingRegex(clientRequestDto.email(), "L'adresse email doit être valide");
        UtilisateurUtil.verifierPassWordMatchingRegex(clientRequestDto.password(), "Le mot de passe ne respecte pas les conditions");
        if ((clientRequestDto.dateDeNaissance().plusYears(18).isAfter(LocalDate.now()))) {
            UtilisateurException utilisateurException = new UtilisateurException("Vous devez être majeur pour vous inscrire");
            log.error("Utilisateur vérifier {} :", utilisateurException.getMessage());
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
