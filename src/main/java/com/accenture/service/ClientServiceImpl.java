package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.Entity.Client;
import com.accenture.service.dto.AdresseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.accenture.shared.model.UtilMessage.*;

/**
 * S'occupe de la vérification de ClientRequestDto, de remonter et de la transmettre sous forme de Cient Entity pour le repository
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    ClientMapper clientMapper;
    private ClientDao clientDAO;

    /**
     * La méthode ajouter(), vérifie les informations entrées par le client, puis transforme le clientRequestDto en Entity client pour la passer à la base de donéee
     * @param clientRequestDto les informations données par l'utilisateur sont envoyés sous forme de RequestDto
     * @return remonte le client enregistré sous forme de ResponseDto
     */
    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) {
        if (clientRequestDto == null) {
            throw new ClientException("La requete est null");}
        Optional<Client> optionalClient = clientDAO.findById(clientRequestDto.email());
        if (optionalClient.isPresent())
            throw new ClientException("Email déjà utilisé");
        verifierClient(clientRequestDto);
        verifierAdresse(clientRequestDto.adresse());
        return clientMapper.toClientResponseDtoForCLient(clientDAO.save(clientMapper.toClient(clientRequestDto)));
    }

    /**
     * Cherche un client par son email
     * @param id l'adresse email qui lui sert d'id
     * @return remonte au client la réponse sous forme de ResponseDto
     * @throws EntityNotFoundException dans le cas où l'email n'existe pas en base
     */
    @Override
    public ClientResponseDto trouver(String id, String password) throws EntityNotFoundException {
        Optional<Client> optionalClient = clientDAO.findById(id);
        if (optionalClient.isEmpty() || !optionalClient.get().getPassword().equals(password))
            throw new EntityNotFoundException(EMAIL_OU_PASSWORD_ERRONE);
        return clientMapper.toClientResponseDtoForCLient(optionalClient.get());
    }

    /**
     * Supprime un client après vérification du mot de passe
     * @param id
     * @param password
     * @throws ClientException
     */
    @Override
    public void supprimer(String id, String password)  throws ClientException {
        Optional<Client> clientOptional = clientDAO.findById(id);
        if (clientOptional.isEmpty() || !clientOptional.get().getPassword().equals(password) ) {
            throw new ClientException(EMAIL_OU_PASSWORD_ERRONE);
        }
            clientDAO.deleteById(id);}

    /**
     * Méthode Patch pour le client
     * @param id
     * @param password
     * @param clientRequestDto
     * @return
     * @throws ClientException
     */
    @Override
    public ClientResponseDto modifier(String id, String password, ClientRequestDto clientRequestDto) throws ClientException {
        Optional<Client> optionalClient = clientDAO.findById(id);
        if (optionalClient.isEmpty() || !optionalClient.get().getPassword().equals(password))
            throw new ClientException(EMAIL_OU_PASSWORD_ERRONE);
        Client clientEnBase = optionalClient.get();
        Client clientModifier = clientMapper.toClient(clientRequestDto);
        remplace(clientModifier, clientEnBase);
        Client clientEnregistrer = clientDAO.save(clientEnBase);
        return clientMapper.toClientResponseDtoForCLient(clientEnregistrer);
    }

    private void remplace(Client clientModifier, Client clientEnBase){
        if (clientModifier.getPassword() != null && clientModifier.getPassword().matches(PASSWORD_REGEX))
            clientEnBase.setPassword(clientModifier.getPassword());
        if (clientModifier.getNom() != null && !clientModifier.getNom().isBlank())
            clientEnBase.setNom(clientModifier.getNom());
        if (clientModifier.getPrenom() != null && !clientModifier.getPrenom().isBlank())
            clientEnBase.setPrenom(clientModifier.getPrenom());
        if (clientModifier.getDateDeNaissance() != null)
            clientEnBase.setDateDeNaissance(clientModifier.getDateDeNaissance());
        if (clientModifier.getPermis() != null)
            clientEnBase.setPermis(clientModifier.getPermis());
        if (clientModifier.getAdresse() != null)
        {
        if (clientModifier.getAdresse().getVille() != null && !clientModifier.getAdresse().getVille().isBlank())
            clientEnBase.getAdresse().setVille(clientModifier.getAdresse().getVille());
        if (clientModifier.getAdresse().getRue() != null && !clientModifier.getAdresse().getRue().isBlank())
            clientEnBase.getAdresse().setRue(clientModifier.getAdresse().getRue());
        if (clientModifier.getAdresse().getCodePostal() != null && !clientModifier.getAdresse().getCodePostal().isBlank())
            clientEnBase.getAdresse().setCodePostal(clientModifier.getAdresse().getCodePostal());}
    }

    /**
     * Vérifie les attributs de l'adresseDto, sert lors de l'ajout d'un client
     * @param adresse adresseDto du ClientRequestDto
     * @throws ClientException dans le cas qu'un des attributs ne soit pas ou mal renseignés, stop l'ajout.
     */

    private static void verifierAdresse(AdresseDto adresse) {
        if (adresse == null) {
            throw new ClientException("L'adresse est absente");
        }
        if (adresse.rue() == null || adresse.rue().isBlank())
            throw new ClientException("Vous devez renseignez la rue");
        if (adresse.ville() == null || adresse.ville().isBlank())
            throw new ClientException("La ville est obligatoire");
        if (adresse.codePostal() == null || adresse.codePostal().isBlank())
            throw new ClientException("Le code postal est obligatoire");
    }

    /**
     * Vérifier les différents attributs du clientRequestDto avant l'ajout en base
     * @param clientRequestDto
     * @throws ClientException en cas où un attribut est mal renseigné, stop l'ajout.
     */

    private static void verifierClient(ClientRequestDto clientRequestDto)  {
        if (clientRequestDto.email() == null || clientRequestDto.email().isBlank()) {
            throw new ClientException("L'adresse Email est absente");
        }
        if (clientRequestDto.nom() == null || clientRequestDto.nom().isBlank()) {
            throw new ClientException("Le nom est absent");
        }
        if (clientRequestDto.prenom() == null || clientRequestDto.prenom().isBlank()) {
            throw new ClientException("Le prenom est absent");
        }
        if (clientRequestDto.dateDeNaissance() == null) {
            throw new ClientException("La date de naissance est absente");
        }
        if ((clientRequestDto.dateDeNaissance().plusYears(18).isAfter(LocalDate.now())))
            throw new ClientException("Vous devez être majeur pour vous inscrire");

        if ((!clientRequestDto.email().matches(EMAIL_REGEX)))
            throw new ClientException("L'adresse email doit être valide");

        if (clientRequestDto.password() == null || clientRequestDto.password().isBlank() || (!clientRequestDto.password().matches(PASSWORD_REGEX)))
            throw new ClientException("Le mot de passe ne respecte pas les conditions");
    }

}
