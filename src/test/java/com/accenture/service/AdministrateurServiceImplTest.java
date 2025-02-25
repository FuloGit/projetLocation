package com.accenture.service;

import com.accenture.exception.AdministrateurException;

import com.accenture.repository.AdministrateurDao;
import com.accenture.repository.Entity.Administrateur;

import com.accenture.service.dto.AdministrateurRequestDto;
import com.accenture.service.dto.AdministrateurResponseDto;

import com.accenture.service.mapper.AdministrateurMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdministrateurServiceImplTest {

    @Mock
    AdministrateurDao administrateurDaoMock;
    @Mock
    AdministrateurMapper administrateurMapperMock;
    @InjectMocks
    AdministrateurServiceImpl administrateurService;

    @DisplayName("""
            Test la méthode ajouter si null lui est passé""")
    @Test
    void ajouterNull() {
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(null));
    }

    @DisplayName("""
            Test Méthode ajouter si email null
            """)
    @Test
    void ajouterAvecEmailNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto(null, "fdsfds@Z23", "Gerard", "Gerard", "CDI");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si email blank
            """)
    @Test
    void ajouterAvecEmailBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("", "fdsfds@Z23", "Gerard", "Gerard", "CDI");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si email ne corresponds aux conditions;
            """)
    @Test
    void ajouterAvecEmailNotMatchingRegex() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("dgfgdg", "fdsfds@Z23", "Gerard", "Gerard", "CDI");
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("L'adresse email doit être valide", ex.getMessage());
    }

    @DisplayName("""
            Test Méthode ajouter si email déjà en base
            """)
    @Test
    void ajouterAvecEmailEnBase() {

        Administrateur administrateur = gerard();
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("Email déjà utilisé", ex.getMessage());
    }


    @DisplayName("""
            Test Méthode ajouter si le mot de passe ne correspond pas à la regex;
            """)
    @Test
    void ajouterAvecPasswordNotMatchingRegex() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfd23", "Gerard", "Gerard", "CDI");
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("Le mot de passe ne respecte pas les conditions", ex.getMessage());
    }

    @DisplayName("""
            Test Méthode ajouter si le mot de passe est null;
            """)
    @Test
    void ajouterAvecPasswordNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", null, "Gerard", "Gerard", "CDI");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si le mot de passe est blank;
            """)
    @Test
    void ajouterAvecPasswordBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "", "Gerard", "Gerard", "CDI");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si le nom est null;
            """)
    @Test
    void ajouterAvecnomNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsf@ggfdZ23", null, "Gerard", "CDI");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si le nom est blank;
            """)
    @Test
    void ajouterAvecnomBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "", "Gerard", "CDI");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si le prenom est null;
            """)
    @Test
    void ajouterAvecPrenomNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", null, "CDI");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si le prenom est blank;
            """)
    @Test
    void ajouterAvecPrenomBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "", "CDI");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si la fonction est null;
            """)
    @Test
    void ajouterAvecFonctionNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", null);
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName("""
            Test Méthode ajouter si la fonction est blank;
            """)
    @Test
    void ajouterAvecFonctionBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", "");
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
    }

    @DisplayName(
            """
                    Verifie si l'ajout se fait bien si tout les critères sont ok.
                    """
    )
    @Test
    void ajouterIsOk() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", "Hr");
        Administrateur adminAvantEnreg = gerard();
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Gerard", "Gerard", "Hr");
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.ajouter(administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }

    @DisplayName("""
            Verifie méthode trouver qui doit renvoyer une EntityNotFoundException lorsque l'administrateur n'existe pas
            """)
    @Test
    void trouverExistePas() {
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> administrateurService.trouver("Gerard@goatmail.com", "fdsfds@Z23"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie méthode trouver qui doit renvoyer une EntityNotFoundException lorsque le password est invalid
            """)
    @Test
    void trouverPassWordInvalid() {
        Administrateur administrateur = gerard();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> administrateurService.trouver("Gerard@goatmail.com", "fdsfds23"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }


    @DisplayName("""
            Verifie méthode trouver qui doit renvoyer un Administrateur.
            """)
    @Test
    void trouverExiste() {
        Administrateur administrateur = gerard();
        Optional<Administrateur> optionalAdministrateur = Optional.of(administrateur);
        AdministrateurResponseDto administrateurResponseDto = gerardResponse();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(optionalAdministrateur);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(gerard())).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.trouver("Gerard@goatmail.com", "fdsfds@Z23"));
    }

    @DisplayName("""
            Verifie methode supprimer si Administrateur est vide, renvoie AdministrateurException
            """)
    @Test
    void supprimerAdminOptionnalVide() {
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.empty());
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("Gerard@goatmail.com", "fdsfds@Z23"));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie methode supprimer si un seul administrateur en base
            """)
    @Test
    void supprimerOnlyOneAdmin() {
        List<Administrateur> liste = List.of(gerard());
        Administrateur administrateur = gerard();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(gerard()));
        Mockito.when(administrateurDaoMock.findAll()).thenReturn(liste);
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("Gerard@goatmail.com", "fdsfds@Z23"));
        assertSame("Vous ne pouvez pas supprimer le dernière administrateur en base.", ex.getMessage());
    }

    @DisplayName("""
            Verifie methode supprimer si Password invalid, renvoie ClientException
            """)
    @Test
    void supprimerPassWordInvalid() {
        Administrateur administrateur = gerard();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("Gerard@goatmail.com", "fdsfds23"));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie methode supprimer fonctionne correctement et passe par deleteById
            """)
    @Test
    void supprimerOk() {
        List<Administrateur> liste = List.of(gerard(), gerard());
        Mockito.when(administrateurDaoMock.findAll()).thenReturn(liste);
        Administrateur administrateur = gerard();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        administrateurService.supprimer("Gerard@goatmail.com", "fdsfds@Z23");
        Mockito.verify(administrateurDaoMock).deleteById("Gerard@goatmail.com");
    }

    @DisplayName("""
            Vérifie si la méthode modifie renvoie une AdministrateurException si l'Email n'est pas en base
            """)
    @Test
    void modifierAvecEmailNull() {
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.empty());
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23", administrateurRequestDto));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Vérifie si la méthode modifie renvoie une AdministrateurException si le password ne correspond pas à l'admin
            """)
    @Test
    void modifierAvecPasswordInvalid() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur administrateur = gerard();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.modifier("Gerard@goatmail.com", "fdsfZ23", administrateurRequestDto));
        assertSame("Email ou password erroné", ex.getMessage());
    }


    @DisplayName("""
            Vérifie si la méthode modifie avec nom null
            """)
    @Test
    void modifierNomNull() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", null , "Gerard", "Hr");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Patrick", "Gerard", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }

    @DisplayName("""
            Vérifie si la méthode modifie fonctionne avec Nom Blank
            """)
    @Test
    void modifierNomBlank() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", " ", "Gerard", "Hr");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Patrick", "Gerard", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }

    @DisplayName("""
            Vérifie si la méthode modifie nom
            """)
    @Test
    void modifierNomOk() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", "Hr");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Gerard", "Gerard", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }


    @DisplayName("""
            Vérifie si la méthode modifie avec prenom null
            """)
    @Test
    void modifierPrenomNull() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Patrick" , null, "Hr");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Patrick", "Gerard", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }

    @DisplayName("""
            Vérifie si la méthode modifie avec prenom blank
            """)
    @Test
    void modifierPrenomBlank() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Patrick", " ", "Hr");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Patrick", "Gerard", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }

    @DisplayName("""
            Vérifie si la méthode modifie prenom
            """)
    @Test
    void modifierPrenomOk() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Arthur", "Hr");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Gerard", "Arthur", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }

    @DisplayName("""
            Vérifie si la méthode modifie avec Fonction null
            """)
    @Test
    void modifierFonctionNull() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Patrick" , "Gerard", null);
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Patrick", "Gerard", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }

    @DisplayName("""
            Vérifie si la méthode modifie avec fonction blank
            """)
    @Test
    void modifierFonctionBlank() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Patrick", "Gerard ", " ");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Patrick", "Gerard", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }

    @DisplayName("""
            Vérifie si la méthode modifie fonction
            """)
    @Test
    void modifierFonctionOk() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Arthur", "CEO");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Gerard", "Arthur", "CEO");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23",administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);
    }















    private Administrateur gerard() {
        return new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", "Hr");
    }

    private AdministrateurResponseDto gerardResponse() {
        return new AdministrateurResponseDto("Gerard@goatmail.com", "Gerard", "Gerard", "Hr");
    }

    private AdministrateurRequestDto gerardRequest() {
        return new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", "Hr");
    }
}