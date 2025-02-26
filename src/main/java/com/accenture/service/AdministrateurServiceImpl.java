package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.repository.AdministrateurDao;
import com.accenture.repository.Entity.utilisateur.Administrateur;
import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.utilisateur.AdministrateurResponseDto;
import com.accenture.service.mapper.AdministrateurMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.accenture.shared.model.UtilMessage.*;
//TODO relire et vérifier synthaxe
/**
 * S'occupe de la vérification de AdministrateurRequestDto, de remonter et de la transmettre sous forme de Administrateur Entity pour le repository
 */
@Service
@AllArgsConstructor
public class AdministrateurServiceImpl implements AdministrateurService {
    private AdministrateurDao administrateurDao;
    private AdministrateurMapper administrateurMapper;

    /**
     * Vérifie la request et transforme en Entity pour la base de donnée
     *
     * @param adminRequestDto
     * @return
     */
    @Override
    public AdministrateurResponseDto ajouter(AdministrateurRequestDto adminRequestDto) {
        verifierAdministrateur(adminRequestDto);
        Optional<Administrateur> optionalAdministrateur = administrateurDao.findById(adminRequestDto.email());
        if (optionalAdministrateur.isPresent())
            throw new AdministrateurException("Email déjà utilisé");
        return administrateurMapper.toAdministrateurResponseDto(administrateurDao.save(administrateurMapper.toAdministrateur(adminRequestDto)));
    }

    /**
     * Trouve les informations d'un administrateur après vérification d'un mot de passe.
     *
     * @param id
     * @param password
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public AdministrateurResponseDto trouver(String id, String password) throws EntityNotFoundException {
        Optional<Administrateur> optionalAdministrateur = administrateurDao.findById(id);
        if (optionalAdministrateur.isEmpty())
            throw new EntityNotFoundException(EMAIL_OU_PASSWORD_ERRONE);
        Administrateur administrateur = optionalAdministrateur.get();
        if (!administrateur.getPassword().equals(password))
            throw new EntityNotFoundException(EMAIL_OU_PASSWORD_ERRONE);
        return administrateurMapper.toAdministrateurResponseDto(administrateur);
    }

    /**
     * Supprime un utilisateur
     *
     * @param id
     * @param password
     * @throws AdministrateurException
     */
    @Override
    public void supprimer(String id, String password) throws AdministrateurException {
        Optional<Administrateur> administrateurOptional = administrateurDao.findById(id);
        if (administrateurOptional.isEmpty() || !administrateurOptional.get().getPassword().equals(password)) {
            throw new AdministrateurException(EMAIL_OU_PASSWORD_ERRONE);
        }
        if (administrateurDao.findAll().size() == 1)
            throw new AdministrateurException("Vous ne pouvez pas supprimer le dernière administrateur en base.");
        administrateurDao.deleteById(id);
    }

    /**
     * Méthode Patch pour Admin
     *
     * @param id
     * @param password
     * @param administrateurRequestDto
     * @return
     */
    @Override
    public AdministrateurResponseDto modifier(String id, String password, AdministrateurRequestDto administrateurRequestDto) throws AdministrateurException {
        Optional<Administrateur> optionalAdministrateur = administrateurDao.findById(id);
        if (optionalAdministrateur.isEmpty() || !optionalAdministrateur.get().getPassword().equals(password))
            throw new AdministrateurException(EMAIL_OU_PASSWORD_ERRONE);
        Administrateur administrateurEnBase = optionalAdministrateur.get();
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
            throw new AdministrateurException("La requête est null");
        }
        if (administrateurRequestDto.email() == null || administrateurRequestDto.email().isBlank()) {
            throw new AdministrateurException("L'adresse email est absente");
        }
        if (administrateurRequestDto.nom() == null || administrateurRequestDto.nom().isBlank()) {
            throw new AdministrateurException("Le nom est absent");
        }
        if (administrateurRequestDto.prenom() == null || administrateurRequestDto.prenom().isBlank()) {
            throw new AdministrateurException("Le prenom est absent");
        }
        if ((!administrateurRequestDto.email().matches(EMAIL_REGEX)))
            throw new AdministrateurException("L'adresse email doit être valide");

        if (administrateurRequestDto.password() == null || administrateurRequestDto.password().isBlank() || (!administrateurRequestDto.password().matches(PASSWORD_REGEX)))
            throw new AdministrateurException("Le mot de passe ne respecte pas les conditions");
        if (administrateurRequestDto.fonction() == null || administrateurRequestDto.fonction().isBlank())
            throw new AdministrateurException("La fonction est absent");
    }


}
