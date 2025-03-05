package com.accenture.service.utilisateur;

import com.accenture.exception.UtilisateurException;

import com.accenture.repository.AdministrateurDao;
import com.accenture.repository.entity.utilisateur.Administrateur;

import com.accenture.service.dto.utilisateur.AdministrateurRequestDto;
import com.accenture.service.dto.utilisateur.AdministrateurResponseDto;

import com.accenture.service.mapper.AdministrateurMapper;
import jakarta.persistence.EntityNotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springdoc.core.customizers.ActuatorOpenApiCustomizer;

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
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(null));
        assertEquals("La requête est null", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attribut Email est null
            """)
    @Test
    void ajouterAvecEmailNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto(null, "fdsfds@Z23", "Gerard", "Gerard", "CDI");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("L'adresse email est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'expeption levée de Méthode ajouter si l'attribut email est blank
            """)
    @Test
    void ajouterAvecEmailBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("", "fdsfds@Z23", "Gerard", "Gerard", "CDI");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("L'adresse email est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si email ne corresponds aux conditions;
            """)
    @Test
    void ajouterAvecEmailNotMatchingRegex() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("dgfgdg", "fdsfds@Z23", "Gerard", "Gerard", "CDI");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("L'adresse email doit être valide", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si email déjà en base
            """)
    @Test
    void ajouterAvecEmailEnBase() {

        Administrateur administrateur = gerard();
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("Email déjà utilisé", ex.getMessage());
    }


    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut password ne correspond pas à la regex;
            """)
    @Test
    void ajouterAvecPasswordNotMatchingRegex() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfd23", "Gerard", "Gerard", "CDI");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("Le mot de passe ne respecte pas les conditions", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut password est null;
            """)
    @Test
    void ajouterAvecPasswordNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", null, "Gerard", "Gerard", "CDI");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("Le mot de passe ne respecte pas les conditions", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut password est blank
            """)
    @Test
    void ajouterAvecPasswordBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "", "Gerard", "Gerard", "CDI");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("Le mot de passe ne respecte pas les conditions", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut nom est null
            """)
    @Test
    void ajouterAvecNomNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsf@ggfdZ23", null, "Gerard", "CDI");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("Le nom est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de  méthode ajouter si l'attribut nom est blank'
            """)
    @Test
    void ajouterAvecNomBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "", "Gerard", "CDI");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("Le nom est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception de la méthode ajouter si l'attribut prénom est null
            """)
    @Test
    void ajouterAvecPrenomNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", null, "CDI");
       UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
       assertEquals("Le prenom est obligatoire" ,ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut prenom est blank;
            """)
    @Test
    void ajouterAvecPrenomBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "", "CDI");
       UtilisateurException ex =  assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
       assertEquals("Le prenom est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levvée de la méthode ajouter si la fonction est null;
            """)
    @Test
    void ajouterAvecFonctionNull() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", null);
      UtilisateurException ex =  assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
      assertEquals("La fonction est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attribut fonction est blank;
            """)
    @Test
    void ajouterAvecFonctionBlank() {
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", "");
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.ajouter(administrateurRequestDto));
        assertEquals("La fonction est obligatoire", ex.getMessage());
    }

    @DisplayName(
            """
                    Verifie si ajouter passe bien par save() si toutes les vérifications sont passées
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
            Verifie méthode trouver(id) qui doit renvoyer une EntityNotFoundException lorsque l'administrateur n'existe pas
            """)
    @Test
    void trouverParIdExistePas() {
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> administrateurService.trouverParId("Gerard@goatmail.com", "fdsfds@Z23"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Verifie méthode trouver(id) qui doit renvoyer une EntityNotFoundException lorsque le password est invalid
            """)
    @Test
    void trouverParIdPassWordInvalid() {
        Administrateur administrateur = gerard();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> administrateurService.trouverParId("Gerard@goatmail.com", "fdsfds23"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }


    @DisplayName("""
            Verifie méthode trouverParId() renvoie un AdministrateurResponseDto
            """)
    @Test
    void trouverExiste() {
        Administrateur administrateur = gerard();
        Optional<Administrateur> optionalAdministrateur = Optional.of(administrateur);
        AdministrateurResponseDto administrateurResponseDto = gerardResponse();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(optionalAdministrateur);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(gerard())).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.trouverParId("Gerard@goatmail.com", "fdsfds@Z23"));
    }

    @DisplayName("""
            Test l'exception levée de la méthode supprimerParId si l'id ne correspond à aucun administrateur en base.
            """)
    @Test
    void supprimerParIdOptionnalVide() {
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.empty());
       EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> administrateurService.supprimerParid("Gerard@goatmail.com", "fdsfds@Z23"));
        assertEquals("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode supprimerParId s'il n'y plus qu'un administrateur en base
            """)
    @Test
    void supprimerOnlyOneAdmin() {
        List<Administrateur> liste = List.of(gerard());
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(gerard()));
        Mockito.when(administrateurDaoMock.findAll()).thenReturn(liste);
        UtilisateurException ex = assertThrows(UtilisateurException.class, () -> administrateurService.supprimerParid("Gerard@goatmail.com", "fdsfds@Z23"));
        assertEquals("Vous ne pouvez pas supprimer le dernière administrateur en base.", ex.getMessage());
    }

    @DisplayName("""
            Verifie methode supprimer si Password invalid, renvoie Administrateur
            """)
    @Test
    void supprimerPassWordInvalid() {
        Administrateur administrateur = gerard();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> administrateurService.supprimerParid("Gerard@goatmail.com", "fdsfds23"));
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
        administrateurService.supprimerParid("Gerard@goatmail.com", "fdsfds@Z23");
        Mockito.verify(administrateurDaoMock).deleteById("Gerard@goatmail.com");
    }

    @DisplayName("""
            Vérifie si la méthode modifie renvoie une AdministrateurException si l'Email n'est pas en base
            """)
    @Test
    void modifierAvecEmailNull() {
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.empty());
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23", administrateurRequestDto));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Vérifie si la méthode modifie renvoie une AdministrateurException si le password est invalide
            """)
    @Test
    void modifierAvecPasswordInvalid() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur administrateur = gerard();
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(administrateur));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> administrateurService.modifier("Gerard@goatmail.com", "fdsfZ23", administrateurRequestDto));
        assertSame("Email ou password erroné", ex.getMessage());
    }

    @DisplayName("""
            Vérifie si la méthode modifier passe bien par save() si tout se passe correctement
            """)
    @Test
    void modifierSuccess() {
        AdministrateurRequestDto administrateurRequestDto = gerardRequest();
        Administrateur adminAvantEnreg = new Administrateur("Gerard@goatmail.com", "fdsfds@Z23", "Gerard", "Gerard", "Hr");
        Administrateur adminApresEnreg = gerard();
        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Gerard", "Gerard", "Hr");
        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(adminAvantEnreg));
        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminAvantEnreg);
        Mockito.when(administrateurDaoMock.save(adminAvantEnreg)).thenReturn(adminApresEnreg);


        Mockito.when(administrateurMapperMock.toAdministrateurRequestDto(adminAvantEnreg)).thenReturn(administrateurRequestDto);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminApresEnreg)).thenReturn(administrateurResponseDto);
        assertSame(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23", administrateurRequestDto));
        Mockito.verify(administrateurDaoMock).save(adminAvantEnreg);

    }
    @DisplayName("""
            Vérifier que la méthode modifier ne change rien en cas de requestNull
            """)
    @Test
    void modifierNull(){
        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto(null, null, null, null, null);

        Administrateur administrateurQuiModifie = new Administrateur(null, null, null, null, null);

        Administrateur vraiAdmin = gerard();
        Administrateur nouvelAdmin = gerard();


        AdministrateurRequestDto administrateurRequestDto1 = gerardRequest();
        AdministrateurResponseDto administrateurResponseDto = gerardResponse();

        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(vraiAdmin));

        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(administrateurQuiModifie);
        Mockito.when(administrateurMapperMock.toAdministrateurRequestDto(vraiAdmin)).thenReturn(administrateurRequestDto1);
        Mockito.when(administrateurDaoMock.save(vraiAdmin)).thenReturn(nouvelAdmin);
        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(nouvelAdmin)).thenReturn(administrateurResponseDto);

        AdministrateurResponseDto administrateurResponseDto1 = administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23", administrateurRequestDto);
        assertEquals("Gerard", administrateurResponseDto1.nom());
        Mockito.verify(administrateurDaoMock).save(nouvelAdmin);
    }


    @Test
    void TestModificationNom(){

        AdministrateurRequestDto administrateurRequestDto = new AdministrateurRequestDto(null, null, "Georges", null, null);

        Administrateur nouvelAdministrateur = new Administrateur();
        nouvelAdministrateur.setNom("Georges");

        Administrateur vraiAdmin = gerard();

        Administrateur adminquiRemplace = gerard();
        adminquiRemplace.setNom("Georges");


        AdministrateurResponseDto administrateurResponseDto = new AdministrateurResponseDto("Gerard@goatmail.com", "Georges", "Gerard", "Hr");

        AdministrateurRequestDto administrateurRequestDto1 = new AdministrateurRequestDto("Gerard@goatmail.com", "fdsfds@Z23", "Georges", "Gerard", "Hr");

        Mockito.when(administrateurDaoMock.findById("Gerard@goatmail.com")).thenReturn(Optional.of(vraiAdmin));

        Mockito.when(administrateurMapperMock.toAdministrateur(administrateurRequestDto)).thenReturn(adminquiRemplace);

        Mockito.when(administrateurDaoMock.save(vraiAdmin)).thenReturn(adminquiRemplace);

        Mockito.when(administrateurMapperMock.toAdministrateurResponseDto(adminquiRemplace)).thenReturn(administrateurResponseDto);
        Mockito.when(administrateurMapperMock.toAdministrateurRequestDto(adminquiRemplace)).thenReturn(administrateurRequestDto1);

        assertEquals("Gerard", vraiAdmin.getNom());

        assertEquals(administrateurResponseDto, administrateurService.modifier("Gerard@goatmail.com", "fdsfds@Z23", administrateurRequestDto));

        Mockito.verify(administrateurDaoMock, Mockito.times(1)).save(vraiAdmin);

        assertEquals("Georges", vraiAdmin.getNom());

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