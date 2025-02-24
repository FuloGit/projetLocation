package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.repository.AdministrateurDao;
import com.accenture.repository.Entity.Administrateur;
import com.accenture.repository.Entity.Client;
import com.accenture.service.dto.AdministrateurRequestDto;
import com.accenture.service.dto.AdministrateurResponseDto;
import com.accenture.service.dto.ClientResponseDtoForClient;
import com.accenture.service.mapper.AdministrateurMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * S'occupe de la vérification de AdministrateurRequestDto, de remonter et de la transmettre sous forme de Administrateur Entity pour le repository
 */
@Service
@AllArgsConstructor
public class AdministrateurServiceImpl implements AdministrateurService{
    public static final String ID_NON_PRESENT = "Id non présent";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@\\-_§]).{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String EMAIL_OU_PASSWORD_ERRONE = "Email ou password erroné";
    private AdministrateurDao administrateurDao;
    private AdministrateurMapper administrateurMapper;

    /**
     * Vérifie la request et transforme en Entity pour la base de donnée
     * @param adminRequestDto
     * @return
     */
    @Override
    public AdministrateurResponseDto ajouter(AdministrateurRequestDto adminRequestDto) {
        verifierAdministrateur(adminRequestDto);
        return administrateurMapper.toAdministrateurResponseDto(administrateurDao.save(administrateurMapper.toAdministrateur(adminRequestDto)));
    }

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

    @Override
    public void supprimer(String id, String password) throws  AdministrateurException {
        Optional<Administrateur> administrateurOptional = administrateurDao.findById(id);
        if (administrateurOptional.isEmpty() || !administrateurOptional.get().getPassword().equals(password) ) {
            throw new AdministrateurException(EMAIL_OU_PASSWORD_ERRONE);
        }
        administrateurDao.deleteById(id);
    }


    private void verifierAdministrateur(AdministrateurRequestDto administrateurRequestDto){
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
