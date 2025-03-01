package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.repository.Entity.vehicule.Voiture;
import com.accenture.repository.VoitureDao;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.model.*;
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

import static com.accenture.shared.model.Permis.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VoitureServiceImplTest {

    @Mock
    private VoitureDao voitureDaoMock;
    @Mock
    private VoitureMapper voitureMapperMock;

    @InjectMocks
    VoitureServiceImpl voitureService;

    @DisplayName("""
            Test la méthode ajouter si null lui est passé""")
    @Test
    void ajouterNull() {
        assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(null));
    }

    @DisplayName("""
            Test l'excpetion levée de la méthode ajouter si l'attribut marque est null
            """)
    @Test
    void ajouterAvecMarqueNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto(null, "MultiPlat", "rouge", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("La marque est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut Marque est blank
            """)
    @Test
    void ajouterAvecMarqueBlank() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("", "MultiPlat", "rouge", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("La marque est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut modèle est Null
            """)
    @Test
    void ajouterAvecModeleNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", null, "rouge", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le modèle est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attrbiut modèle est Blank
            """)
    @Test
    void ajouterAvecModeleBlank() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "", "rouge", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le modèle est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attribut couleur est null
            """)
    @Test
    void ajouterAvecCouleurNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", null, 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("La couleur est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attribut Couleur est BLank
            """)
    @Test
    void ajouterAvecCouleurBlank() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("La couleur est obligatoire", ex.getMessage());
    }


    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut NombreDePlaces est null
            """)
    @Test
    void ajouterAvecNombredePlacesNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", null, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le nombre de places est obligatoire", ex.getMessage());
    }


    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut Caburant est null
            """)
    @Test
    void ajouterAvecCarburantNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, null, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le carburant est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut NombresDeportes est null
            """)
    @Test
    void ajouterAvecNombreDePorteNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, null, Transmission.AUTO, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le nombre de portes est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attribut transmission est null
            """)
    @Test
    void ajouterAvecTransmissionNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, null, true, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le type de transmission est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'excepetion levée de la Méthode ajouter si l'attribut Climatisation est null
            """)
    @Test
    void ajouterAvecClimatisationNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, null, 3, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le statut de la climatisation est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attribut nombreDeBagages est null
            """)
    @Test
    void ajouterAvecBagageNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, null, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le nombre de bagages est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si  l'attribut nombreDeBagages est 0
            """)
    @Test
    void ajouterAvecBagageZero() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 0, 10, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le nombre de bagages est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attribut tarifJournalier est null
            """)
    @Test
    void ajouterAvecTarifNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, null, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le tarif Journalier est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la Méthode ajouter si l'attribut tarifJournalier est zéro
            """)
    @Test
    void ajouterAvecTarifZero() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 0, 100040, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le tarif Journalier est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut kilometrage est null
            """)
    @Test
    void ajouterAvecKilometrageNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, null, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le kilomètrage est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut kilometrage est zero
            """)
    @Test
    void ajouterAvecKilometrageZero() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 0, true, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le kilomètrage est obligatoire", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de laMéthode ajouter si l'attribut Actif est null
            """)
    @Test
    void ajouterAvecActifNull() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, null, false);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le véhicule doit être actif ou inactif", ex.getMessage());
    }

    @DisplayName("""
            Test l'exception levée de la méthode ajouter si l'attribut RetirerDuParc est null
            """)
    @Test
    void ajouterAvecRetirerDuParc() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, false, null);
        VoitureException ex = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Precisez si le véhicule est retiré du parc", ex.getMessage());
    }

    @DisplayName("""
            Verifie le permis assigné lors de l'enregistrement, avec Permis D
            """)
    @Test
    void TestAjouterOKPermisD1() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 10, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        Voiture voitureAvant = new Voiture(1L, "Volvo", "Multiplat", "rouge ", 10, 100040, true, false, 10, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3);
        Voiture voitureApres = new Voiture(1L, "Volvo", "Multiplat", "rouge ", 10, 100040, true, false, 10, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3);
        VoitureResponseDto voitureResponseDto = new VoitureResponseDto(1L, "Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, D1);
        Mockito.when(voitureMapperMock.toVoiture(voitureRequestDto)).thenReturn(voitureAvant);
        Mockito.when(voitureDaoMock.save(voitureAvant)).thenReturn(voitureApres);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voitureApres)).thenReturn(voitureResponseDto);
        assertSame(voitureResponseDto.permis(), voitureService.ajouterVoiture(voitureRequestDto).permis());
        Mockito.verify(voitureDaoMock).save(voitureAvant);
    }

    @DisplayName("""
            Verifie le permis assigné lors de l'enregistrement, avec Permis B
            """)
    @Test
    void TestAjouterOKPermisB() {
        VoitureRequestDto voitureRequestDto = creerRequest();
        Voiture voitureAvant = creerVoiture();
        Voiture voitureApres = creerVoiture();
        VoitureResponseDto voitureResponseDto = creerResponse();
        Mockito.when(voitureMapperMock.toVoiture(voitureRequestDto)).thenReturn(voitureAvant);
        Mockito.when(voitureDaoMock.save(voitureAvant)).thenReturn(voitureApres);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voitureApres)).thenReturn(voitureResponseDto);
        assertSame(voitureResponseDto.permis(), voitureService.ajouterVoiture(voitureRequestDto).permis());
        Mockito.verify(voitureDaoMock).save(voitureAvant);
    }

    @DisplayName("""
            Verifie l'exception levée si l'attribut nombresDePassager est trop elevé pour assigner un permis
            """)
    @Test
    void TestAjouterPermisImpossible() {
        VoitureRequestDto voitureRequestDto = new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 20, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
        Voiture voitureAvant = new Voiture(1L, "Volvo", "Multiplat", "rouge ", 10, 100040, true, false, 20, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3);
        Mockito.when(voitureMapperMock.toVoiture(voitureRequestDto)).thenReturn(voitureAvant);
        VoitureException voitureException = assertThrows(VoitureException.class, () -> voitureService.ajouterVoiture(voitureRequestDto));
        assertEquals("Le nombre de passages n'est pas adéquat", voitureException.getMessage());
    }

    @DisplayName("""
            Verifie la méthode TrouverToutes, renvoie bien une liste de voitureResponse
            """)
    @Test
    void TrouverToutes() {
        Voiture voiture1 = creerVoiture();
        Voiture voiture2 = creerVoiture2();
        List<Voiture> liste = List.of(voiture1, voiture2);
        VoitureResponseDto voitureResponseDto1 = creerResponse();
        VoitureResponseDto voitureResponseDto2 = creerResponse2();
        List<VoitureResponseDto> listeResponse = List.of(voitureResponseDto1, voitureResponseDto2);
        Mockito.when(voitureDaoMock.findAll()).thenReturn(liste);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture1)).thenReturn(voitureResponseDto1);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture2)).thenReturn(voitureResponseDto2);
        assertEquals(listeResponse, voitureService.TrouverToutes());
    }

    @DisplayName("""
            Verifie la méthode listerParRequete ACTIFS, renvoie bien une liste de voitureResponse
            """)
    @Test
    void trouverParRequeteActif() {
        Voiture voiture1 = creerVoiture();
        Voiture voiture2 = creerVoiture2();
        List<Voiture> liste = List.of(voiture1, voiture2);
        VoitureResponseDto voitureResponseDto1 = creerResponse();
        VoitureResponseDto voitureResponseDto2 = creerResponse2();
        List<VoitureResponseDto> listeResponse = List.of(voitureResponseDto1, voitureResponseDto2);
        Mockito.when(voitureDaoMock.findByActifTrue()).thenReturn(liste);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture1)).thenReturn(voitureResponseDto1);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture2)).thenReturn(voitureResponseDto2);
        assertEquals(listeResponse, voitureService.trouverParFiltre(FiltreListe.ACTIFS));
    }

    @DisplayName("""
            Verifie la méthode listerParRequete INACTIFS, renvoie bien une liste de voitureResponse
            """)
    @Test
    void trouverParRequeteInactif() {
        Voiture voiture1 = creerVoiture();
        Voiture voiture2 = creerVoiture2();
        List<Voiture> liste = List.of(voiture1, voiture2);
        VoitureResponseDto voitureResponseDto1 = creerResponse();
        VoitureResponseDto voitureResponseDto2 = creerResponse2();
        List<VoitureResponseDto> listeResponse = List.of(voitureResponseDto1, voitureResponseDto2);
        Mockito.when(voitureDaoMock.findByActifFalse()).thenReturn(liste);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture1)).thenReturn(voitureResponseDto1);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture2)).thenReturn(voitureResponseDto2);
        assertEquals(listeResponse, voitureService.trouverParFiltre(FiltreListe.INACTIFS));
    }

    @DisplayName("""
            Verifie la méthode listerParRequete HorsParc
            """)
    @Test
    void trouverParRequeteHorsParc() {
        Voiture voiture1 = creerVoiture();
        Voiture voiture2 = creerVoiture2();
        List<Voiture> liste = List.of(voiture1, voiture2);
        VoitureResponseDto voitureResponseDto1 = creerResponse();
        VoitureResponseDto voitureResponseDto2 = creerResponse2();
        List<VoitureResponseDto> listeResponse = List.of(voitureResponseDto1, voitureResponseDto2);
        Mockito.when(voitureDaoMock.findByRetireDuParcTrue()).thenReturn(liste);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture1)).thenReturn(voitureResponseDto1);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture2)).thenReturn(voitureResponseDto2);
        assertEquals(listeResponse, voitureService.trouverParFiltre(FiltreListe.HORS_DU_PARC));
    }

    @DisplayName("""
            Verifie la méthode listerParRequete DansleParc
            """)
    @Test
    void trouverParRequeteDansleParc() {
        Voiture voiture1 = creerVoiture();
        Voiture voiture2 = creerVoiture2();
        List<Voiture> liste = List.of(voiture1, voiture2);
        VoitureResponseDto voitureResponseDto1 = creerResponse();
        VoitureResponseDto voitureResponseDto2 = creerResponse2();
        List<VoitureResponseDto> listeResponse = List.of(voitureResponseDto1, voitureResponseDto2);
        Mockito.when(voitureDaoMock.findByRetireDuParcFalse()).thenReturn(liste);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture1)).thenReturn(voitureResponseDto1);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture2)).thenReturn(voitureResponseDto2);
        assertEquals(listeResponse, voitureService.trouverParFiltre(FiltreListe.DANS_LE_PARC));
    }

    @DisplayName("""
            Verifie rechercher voiture par Id renvoie une EntityNotfoundException si l'Id ne correspond pas
            """)
    @Test
    void trouverParIdExistePas() {
        Mockito.when(voitureDaoMock.findById(2L)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> voitureService.trouverParId(2L));
        assertEquals("Id non présent", ex.getMessage());
    }

    @DisplayName("""
            Test la méthode trouver (id) qui doit renvoyer une voitureResponseDto lorsque la voiture existe
            """)
    @Test
    void trouverParIdExiste() {
        Voiture voiture = creerVoiture();
        Optional<Voiture> optionalVoiture = Optional.of(voiture);
        VoitureResponseDto voitureResponseDto = creerResponse();
        Mockito.when(voitureDaoMock.findById(1L)).thenReturn(optionalVoiture);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voiture)).thenReturn(voitureResponseDto);
        assertSame(voitureResponseDto, voitureService.trouverParId(1L));
    }


    @DisplayName("""
            Test l'exception levée de la méthode supprimer si l'id ne correspond à aucune entity
            """)
    @Test
    void supprimerIdExistePas() {
        Mockito.when(voitureDaoMock.existsById(1L)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> voitureService.supprimerParId(1L));
        assertSame("Id non présent", ex.getMessage());
    }

    @DisplayName("""
            Vérifie que la méthose supprimer appel bien deleteById(id)
            """)
    @Test
    void supprimerExiste() {
        Mockito.when(voitureDaoMock.existsById(1L)).thenReturn(true);
        voitureService.supprimerParId(1L);
        Mockito.verify(voitureDaoMock).deleteById(1L);
    }


    @DisplayName("""
            Test l'exception levée de la méthode modifier is la voiture est retiré du parc
            """)
    @Test
    void modifierAvecVoitureRetireDUParc (){
        Voiture voiture = new Voiture(1L, "Volvo", "Multiplat", "rouge ", 5, 100040, true, true, 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3);
        Mockito.when(voitureDaoMock.findById(1L)).thenReturn(Optional.of(voiture));
        VoitureRequestDto voitureRequestDto = creerRequest();
        VoitureException exception = assertThrows(VoitureException.class, () -> voitureService.modifier(voitureRequestDto, 1L));
        assertSame("Une voiture retirée du parc n'est pas modifiable", exception.getMessage());
    }

    @DisplayName("""
            Vérifie si la méthode modifie renvoie une voitureException si l'Email n'est pas en base
            """)
    @Test
    void modifierAvecIdInexistante() {
        Mockito.when(voitureDaoMock.findById(1L)).thenReturn(Optional.empty());
        VoitureRequestDto voitureRequestDto = creerRequest();
        EntityNotFoundException ex  = assertThrows(EntityNotFoundException.class, () -> voitureService.modifier(voitureRequestDto, 1L));
        assertSame("Id non présent", ex.getMessage());
    }

    @DisplayName("""
            Vérifie si la méthode modifier par bien par save() si tout se passe bien
            """)
    @Test
    void modifierSuccess() {
       VoitureRequestDto voitureRequestDto = creerRequest();
        Voiture voitureAvantEnreg = creerVoiture();
        Voiture voitureApresENreg = creerVoiture();
        VoitureResponseDto voitureResponseDto = creerResponse();
        Mockito.when(voitureDaoMock.findById(1L)).thenReturn(Optional.of(voitureAvantEnreg));
        Mockito.when(voitureMapperMock.toVoiture(voitureRequestDto)).thenReturn(voitureAvantEnreg);
        Mockito.when(voitureDaoMock.save(voitureAvantEnreg)).thenReturn(voitureApresENreg);
        Mockito.when(voitureMapperMock.toVoitureRequestDto(voitureAvantEnreg)).thenReturn(voitureRequestDto);
        Mockito.when(voitureMapperMock.toVoitureResponseDto(voitureApresENreg)).thenReturn(voitureResponseDto);
        assertSame(voitureResponseDto, voitureService.modifier(voitureRequestDto, 1L));
        Mockito.verify(voitureDaoMock).save(voitureAvantEnreg);
    }



    private VoitureRequestDto creerRequest() {
        return new VoitureRequestDto("Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, 10, 100040, true, false);
    }


    private VoitureResponseDto creerResponse() {
        return new VoitureResponseDto(1L, "Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, B);
    }

    private VoitureResponseDto creerResponse2() {
        return new VoitureResponseDto(1L, "Volvo", "Multiplat", "rouge ", 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3, B);
    }

    private Voiture creerVoiture() {
        return new Voiture(1L, "Volvo", "Multiplat", "rouge ", 5, 100040, true, false, 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3);
    }

    private Voiture creerVoiture2() {
        return new Voiture(1L, "Honda", "Multiplat", "rouge ", 5, 100040, true, false, 5, Carburant.Diesel, NombresDePortes.TROIS, Transmission.AUTO, true, 3);
    }
}