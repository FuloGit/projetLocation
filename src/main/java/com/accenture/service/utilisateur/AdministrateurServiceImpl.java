package com.accenture.service.utilisateur;

import com.accenture.exception.UtilisateurException;
import com.accenture.repository.utilisateur.AdministrateurDao;
import com.accenture.repository.entity.utilisateur.Administrateur;
import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.utilisateur.AdministrateurResponseDto;
import com.accenture.service.mapper.AdministrateurMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.accenture.service.utilisateur.UtilisateurUtil.*;

/**
 * Implémentation de l'interface Administrateur Service
 */
@Service
@AllArgsConstructor
@Slf4j
public class AdministrateurServiceImpl implements AdministrateurService {
    private AdministrateurDao administrateurDao;
    private AdministrateurMapper administrateurMapper;


    /**
     * Renvoie un administrateurResponseDto après avoir vérifié la requete et appeler save()
     * @param adminRequestDto reçu depuis le controller
     * @return Remonte le client enregistré sous forme de ResponseDto
     * @throws UtilisateurException si l'email existe déjà dans la base de donnée
     */
    @Override
    public AdministrateurResponseDto ajouter(AdministrateurRequestDto adminRequestDto) {
        verifierAdministrateur(adminRequestDto);
        Optional<Administrateur> optionalAdministrateur = administrateurDao.findById(adminRequestDto.email());
        if (optionalAdministrateur.isPresent()){
            UtilisateurException utilisateurException = new UtilisateurException("Email déjà utilisé");
            log.error("ajouter (administrateurRequest) : {} ", utilisateurException.getMessage());
            throw utilisateurException;}
        return administrateurMapper.toAdministrateurResponseDto(administrateurDao.save(administrateurMapper.toAdministrateur(adminRequestDto)));
    }

    /**
     * Renvoie l'administrateurResponseDto correspondant à l'id après avoir vérifié la requête utilisateur
     * @param id transmis depuis le controller
     * @param password transmis depuis le controller
     * @return AdministrateurResponseDto
     * @throws UtilisateurException dans le cas où l'email n'existe pas en base de donnée ou que le Password ne correspond pas
     */
    @Override
    public AdministrateurResponseDto trouverParId(String id, String password) throws EntityNotFoundException {
        return administrateurMapper.toAdministrateurResponseDto(verifierPasswordAdministrateur(id, password));
    }

    /**
     * Appel la méthode deleteParId (id) après vérification de la requete
     * @param id transmis depuis le controller
     * @param password transmis depuis le controller
     * @throws UtilisateurException si la vérification échoue ou si on tente de supprimer le dernier administrateur en base
     */
    @Override
    public void supprimerParid(String id, String password) throws UtilisateurException {
        verifierPasswordAdministrateur(id, password);

        if (administrateurDao.findAll().size() == 1) {
            UtilisateurException utilisateurException = new UtilisateurException("Vous ne pouvez pas supprimer le dernière administrateur en base.");
            log.error("supprimerParid() : {} ",utilisateurException.getMessage());
            throw utilisateurException;
        }
        administrateurDao.deleteById(id);
    }

    /**
     * Transfert les valeurs des attributs de la requête à l'administrateur en base, après vérification.
     * @param id transmis depuis le controller
     * @param password transmis depuis le controller
     * @param administrateurRequestDto qui modifie celui en base
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
        UtilisateurUtil.verifierObjectNotNull(administrateurRequestDto, "La requête est null");
        UtilisateurUtil.verifierStringNotNullNotEmpty(administrateurRequestDto.email(),"L'adresse email est obligatoire");
        UtilisateurUtil.verifierStringNotNullNotEmpty(administrateurRequestDto.nom(),"Le nom est obligatoire");
        UtilisateurUtil.verifierStringNotNullNotEmpty(administrateurRequestDto.prenom(), "Le prenom est obligatoire");
        UtilisateurUtil.verifierStringNotNullNotEmpty(administrateurRequestDto.fonction(),"La fonction est obligatoire" );
        UtilisateurUtil.verifierPassWordMatchingRegex(administrateurRequestDto.password(),"Le mot de passe ne respecte pas les conditions");
        UtilisateurUtil.verifierEmailMatchingRegex(administrateurRequestDto.email(),"L'adresse email doit être valide");
    }



    private Administrateur verifierPasswordAdministrateur(String id, String password) {
        Optional<Administrateur> optionalAdministrateur = administrateurDao.findById(id);
        if (optionalAdministrateur.isEmpty() || !optionalAdministrateur.get().getPassword().equals(password)) {
            log.error("verifier PasswordAdministrateur : {}" , EMAIL_OU_PASSWORD_ERRONE);
            throw new EntityNotFoundException(EMAIL_OU_PASSWORD_ERRONE);
        }
        return optionalAdministrateur.get();
    }

}
