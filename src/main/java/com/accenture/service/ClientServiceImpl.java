package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.Entity.Client;
import com.accenture.service.dto.AdresseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDtoForClient;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

/**
 * S'occupe de la vérification de CLientRequestDto, de remonter et de la transmettre sous forme de Cient Entity pour le repository
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    public static final String ID_NON_PRESENT = "Id non présent";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@\\-_§]).{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String EMAIL_OU_PASSWORD_ERRONE = "Email ou password erroné";
    ClientMapper clientMapper;
    private ClientDao clientDAO;

    /**
     * La méthode ajouter(), vérifie les informations entrées par le client, puis transforme le clientRequestDto en Entity client pour la passer à la base de donéee
     * @param clientRequestDto les informations données par l'utilisateur sont envoyés sous forme de RequestDto
     * @return remonte le client enregistré sous forme de ResponseDto
     */
    @Override
    public ClientResponseDtoForClient ajouter(ClientRequestDto clientRequestDto) {
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
    public ClientResponseDtoForClient trouver(String id, String password) throws EntityNotFoundException {
        Optional<Client> optionalClient = clientDAO.findById(id);
        if (optionalClient.isEmpty() || !optionalClient.get().getPassword().equals(password))
            throw new EntityNotFoundException(EMAIL_OU_PASSWORD_ERRONE);

        return clientMapper.toClientResponseDtoForCLient(optionalClient.get());

    }

    @Override
    public void supprimer(String id, String password)  throws ClientException {
        Optional<Client> clientOptional = clientDAO.findById(id);
        if (clientOptional.isEmpty() || !clientOptional.get().getPassword().equals(password) ) {
            throw new ClientException(EMAIL_OU_PASSWORD_ERRONE);
        }
            clientDAO.deleteById(id);
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

    private static void verifierClient(ClientRequestDto clientRequestDto) {
        if (clientRequestDto == null) {
            throw new ClientException("La requete est null");
        }
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
