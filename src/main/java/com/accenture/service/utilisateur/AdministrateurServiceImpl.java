package com.accenture.service.utilisateur;
import com.accenture.exception.UtilisateurException;
import com.accenture.repository.AdministrateurDao;
import com.accenture.repository.entity.utilisateur.Administrateur;
import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.utilisateur.AdministrateurResponseDto;
import com.accenture.service.mapper.AdministrateurMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.accenture.service.dto.utilisateur.UtilMessage.*;

/**
 * Implémentation de l'interface Administrateur Service
 */
@Service
@AllArgsConstructor
public class AdministrateurServiceImpl implements AdministrateurService {
    private AdministrateurDao administrateurDao;
    private AdministrateurMapper administrateurMapper;


    /**
     * Renvoie un administrateurResponseDto après avoir vérifier la requete et appeler save()
     * @param adminRequestDto
     * @return Remonte le client enregistré sous forme de ResponseDto
     * @throws UtilisateurException si l'email existe déjà dans la base de donnée
     */
    @Override
    public AdministrateurResponseDto ajouter(AdministrateurRequestDto adminRequestDto) {
        verifierAdministrateur(adminRequestDto);
        Optional<Administrateur> optionalAdministrateur = administrateurDao.findById(adminRequestDto.email());
        if (optionalAdministrateur.isPresent())
            throw new UtilisateurException("Email déjà utilisé");
        return administrateurMapper.toAdministrateurResponseDto(administrateurDao.save(administrateurMapper.toAdministrateur(adminRequestDto)));
    }

    /**
     * Renvoie l'administrateurResponseDto correspondant à l'id après avoir vérifier la requête utilisateur
     * @param id
     * @param password
     * @return AdministrateurResponseDto
     * @throws UtilisateurException dans le cas où l'email n'existe pas en base de donnée ou que le Password ne correspond pas
     */
    @Override
    public AdministrateurResponseDto trouverParId(String id, String password) throws EntityNotFoundException {
        return administrateurMapper.toAdministrateurResponseDto(verifierPasswordAdministrateur(id, password));
    }

    /**
     * Appel la méthode deleteParId(id)  après vérification de la requete
     * @param id
     * @param password
     * @throws UtilisateurException si la vérification échoue
     */
    @Override
    public void supprimerParid(String id, String password) throws UtilisateurException {
       verifierPasswordAdministrateur(id, password);
        if (administrateurDao.findAll().size() == 1)
            throw new UtilisateurException("Vous ne pouvez pas supprimer le dernière administrateur en base.");
        administrateurDao.deleteById(id);
    }

    /**
     * Transfert les valeurs des attributs de la requete à l'administrateur en base, après vérification.
     * @param id
     * @param password
     * @param administrateurRequestDto
     * @return AdministrateurResponseDto
     * @throws UtilisateurException si la vérification échoue.
     */
    @Override
    public AdministrateurResponseDto modifier(String id, String password, AdministrateurRequestDto administrateurRequestDto) throws UtilisateurException {
        Administrateur administrateurEnBase = verifierPasswordAdministrateur(id, password);
        Administrateur administrateurAModifier = administrateurMapper.toAdministrateur(administrateurRequestDto);
        remplace(administrateurEnBase, administrateurAModifier);
        verifierAdministrateur(administrateurMapper.toAdministrateurRequestDto(administrateurEnBase));
        Administrateur administrateurEnreg = administrateurDao.save(administrateurEnBase);
        return administrateurMapper.toAdministrateurResponseDto(administrateurEnreg);
    }


    private void remplace(Administrateur administrateurEnBase, Administrateur administrateurAModifier) {
        if (administrateurAModifier.getPassword() != null)
            administrateurEnBase.setPassword(administrateurAModifier.getPassword());
        if (administrateurAModifier.getNom() != null)
            administrateurEnBase.setNom(administrateurAModifier.getNom());
        if (administrateurAModifier.getPrenom() != null)
            administrateurEnBase.setPrenom(administrateurAModifier.getPrenom());
        if (administrateurAModifier.getFonction() != null)
            administrateurEnBase.setFonction(administrateurAModifier.getFonction());
    }


    private void verifierAdministrateur(AdministrateurRequestDto administrateurRequestDto) {
        if (administrateurRequestDto == null) {
            throw new UtilisateurException("La requête est null");
        }
        if (administrateurRequestDto.email() == null || administrateurRequestDto.email().isBlank()) {
            throw new UtilisateurException("L'adresse email est obligatoire");
        }
        if (administrateurRequestDto.nom() == null || administrateurRequestDto.nom().isBlank()) {
            throw new UtilisateurException("Le nom est obligatoire");
        }
        if (administrateurRequestDto.prenom() == null || administrateurRequestDto.prenom().isBlank()) {
            throw new UtilisateurException("Le prenom est obligatoire");
        }
        if ((!administrateurRequestDto.email().matches(EMAIL_REGEX)))
            throw new UtilisateurException("L'adresse email doit être valide");
        if (administrateurRequestDto.password() == null || administrateurRequestDto.password().isBlank() || (!administrateurRequestDto.password().matches(PASSWORD_REGEX)))
            throw new UtilisateurException("Le mot de passe ne respecte pas les conditions");
        if (administrateurRequestDto.fonction() == null || administrateurRequestDto.fonction().isBlank())
            throw new UtilisateurException("La fonction est obligatoire");
    }

    private Administrateur verifierPasswordAdministrateur(String id, String password) {
        Optional<Administrateur> optionalAdministrateur = administrateurDao.findById(id);
        if (optionalAdministrateur.isEmpty() || !optionalAdministrateur.get().getPassword().equals(password))
            throw new EntityNotFoundException(EMAIL_OU_PASSWORD_ERRONE);
        return optionalAdministrateur.get();
    }

}
