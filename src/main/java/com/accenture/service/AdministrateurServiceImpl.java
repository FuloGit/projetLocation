package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.repository.AdministrateurDao;
import com.accenture.service.dto.AdministrateurRequestDto;
import com.accenture.service.dto.AdministrateurResponseDto;
import com.accenture.service.mapper.AdministrateurMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * <p>S'occupe de la vérification de AdministrateurRequestDto, de remonter et de la transmettre sous forme de Administrateur Entity pour le repository</p>
 */
@Service
@AllArgsConstructor
public class AdministrateurServiceImpl implements AdministrateurService{
    public static final String ID_NON_PRESENT = "Id non présent";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@\\-_§]).{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private AdministrateurDao administrateurDao;
    private AdministrateurMapper administrateurMapper;

    @Override
    public AdministrateurResponseDto ajouter(AdministrateurRequestDto adminRequestDto) {
        verifierAdministrateur(adminRequestDto);
        return administrateurMapper.toAdministrateurResponseDto(administrateurDao.save(administrateurMapper.toAdministrateur(adminRequestDto)));
    }


    private void verifierAdministrateur(AdministrateurRequestDto administrateurRequestDto){
        if (administrateurRequestDto == null) {
            throw new AdministrateurException("Le client est null");
        }
        if (administrateurRequestDto.email() == null || administrateurRequestDto.email().isBlank()) {
            throw new AdministrateurException("L'adresse Email est absente");
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
            throw new AdministrateurException("La fonction de l'administrateur doit être précisée.");
    }
}
