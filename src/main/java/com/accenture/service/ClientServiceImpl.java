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

import java.time.LocalDate;
import java.util.Optional;

/**
 * <p>S'occupe de la vérification de CLientRequestDto, de remonter et de la transmettre sous forme de Cient Entity pour le repository</p>
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    public static final String ID_NON_PRESENT = "Id non présent";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@\\-_§]).{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    ClientMapper clientMapper;
    private ClientDao clientDAO;

    /**
     * <p> La méthode ajouter(), vérifie les informations entrées par le client, puis transforme le clientRequestDto en Entity client pour la passer à la base de donéee</p>
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
     * <p>Cherche un client par son email</p>
     * @param id l'adresse email qui lui sert d'id
     * @return remonte au client la réponse sous forme de ResponseDto
     * @throws EntityNotFoundException dans le cas où l'email n'existe pas en base
     */
    @Override
    public ClientResponseDtoForClient trouver(String id) throws EntityNotFoundException {
        Optional<Client> optionalClient = clientDAO.findById(id);
        if (optionalClient.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        return clientMapper.toClientResponseDtoForCLient(optionalClient.get());

    }

    /**
     * <p>Vérifie les attributs de l'adresseDto, sert lors de l'ajout d'un client</p>
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
     * <p>Vérifier les différents attributs du clientRequestDto avant l'ajout en base</p>
     * @param clientRequestDto
     * @throws ClientException en cas où un attribut est mal renseigné, stop l'ajout.
     */

    private static void verifierClient(ClientRequestDto clientRequestDto) {
        if (clientRequestDto == null) {
            throw new ClientException("Le client est null");
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
        if (clientRequestDto.DateDeNaissance() == null) {
            throw new ClientException("Date de DateDeNaissance absente");
        }
        if ((clientRequestDto.DateDeNaissance().plusYears(18).isAfter(LocalDate.now())))
            throw new ClientException("Vous devez être majeur pour vous inscrire");

        if ((!clientRequestDto.email().matches(EMAIL_REGEX)))
            throw new ClientException("L'adresse email doit être valide");

        if (clientRequestDto.password() == null || clientRequestDto.password().isBlank() || (!clientRequestDto.password().matches(PASSWORD_REGEX)))
            throw new ClientException("Le mot de passe ne respecte pas les conditions");

    }
}
