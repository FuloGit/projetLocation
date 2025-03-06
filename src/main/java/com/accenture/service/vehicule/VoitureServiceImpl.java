package com.accenture.service.vehicule;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.repository.vehicule.VoitureDao;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.model.FiltreListe;
import com.accenture.shared.model.Permis;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de l'interface Voiture service
 */
@Service
@AllArgsConstructor
@Slf4j
public class VoitureServiceImpl implements VoitureService {

    private VoitureDao voitureDao;
    private VoitureMapper voitureMapper;

    /**
     * Renvoie une VoitureResponseDto après avoir vérifié la requête et appeler save()
     *
     * @param voitureRequestDto
     * @return
     * @throws VehiculeException si un champ est null ou blank
     */
    @Override
    public VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto) throws VehiculeException {
        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        determinerPermis(voiture);
        return voitureMapper.toVoitureResponseDto(voitureDao.save(voiture));
    }

    /**
     * Appel la méthode findAll() et retransmet la liste des voitures
     *
     * @return liste de voitureResponseDto
     */
    @Override
    public List<VoitureResponseDto> trouverToutes() {
        return voitureDao.findAll().stream()
                .map(voiture -> voitureMapper.toVoitureResponseDto(voiture)).toList();
    }

    /**
     * Utilise un switch pour renvoyer une liste différente selon la requete utilisateur
     *
     * @param filtreListe filtre transmis depuis le Voiturecontroller
     * @return renvoie une liste de VoitureDto selon le critère reçu depuis le controller
     */
    @Override
    public List<VoitureResponseDto> trouverParFiltre(FiltreListe filtreListe) {
        List<Voiture> liste = switch (filtreListe) {
            case ACTIFS ->
                    voitureDao.findByActifTrue();
            case INACTIFS ->
                   voitureDao.findByActifFalse();
            case HORS_DU_PARC ->
                    voitureDao.findByRetireDuParcTrue();

            case DANS_LE_PARC ->
                     voitureDao.findByRetireDuParcFalse();

        };
        return liste.stream().map(voiture -> voitureMapper.toVoitureResponseDto(voiture)).toList();
    }

    /**
     * Renvoie la voitureResponseDto correspondant à l'id après vérification
     *
     * @param id
     * @return VoitureResponseDto
     * @throws EntityNotFoundException si l'id n'existe pas en base
     */
    @Override
    public VoitureResponseDto trouverParId(Long id) throws EntityNotFoundException {
        return voitureMapper.toVoitureResponseDto(checkVoiture(id));
    }

    /**
     * Appel deleteById() après vérification de la requête
     * A retravaillé une fois la classe location implémentée.
     *
     * @param id
     */
    @Override
    public void supprimerParId(Long id) {
        if (voitureDao.existsById(id))
            voitureDao.deleteById(id);
        else{
            EntityNotFoundException entityNotFoundException = new EntityNotFoundException("Id non présent");
            log.error("SupprimerParId (Long id): {} ", entityNotFoundException.getMessage());
            throw entityNotFoundException;
        }

    }

    @Override
    public VoitureResponseDto modifier(VoitureRequestDto voitureRequestDto, Long id) {
        Voiture voitureEnBase = checkVoiture(id);
        if (voitureEnBase.getRetireDuParc() != null && voitureEnBase.getRetireDuParc())
        { VehiculeException vehiculeException= new VehiculeException("Une voiture retirée du parc n'est pas modifiable");
            log.error("modifierParId(Long Id) : {}", vehiculeException.getMessage());
            throw vehiculeException;}
        Voiture voitureQuiModifie = voitureMapper.toVoiture(voitureRequestDto);
        remplacer(voitureQuiModifie, voitureEnBase);
        verifierVoiture(voitureMapper.toVoitureRequestDto(voitureEnBase));
        return voitureMapper.toVoitureResponseDto(voitureDao.save(voitureEnBase));
    }

    private Voiture checkVoiture(Long id) {
        Optional<Voiture> optionalVoiture = voitureDao.findById(id);
        if (optionalVoiture.isEmpty()){
            EntityNotFoundException entityNotFoundException = new EntityNotFoundException("Id non présent");
            log.error("checkVoiture (Long id) : {} ", entityNotFoundException.getMessage());
            throw entityNotFoundException;
        }

        return optionalVoiture.get();
    }

    private void remplacer(Voiture voitureQuiModifie, Voiture voitureEnBase) {
        if (voitureQuiModifie.getMarque() != null)
            voitureEnBase.setMarque(voitureQuiModifie.getMarque());
        if (voitureQuiModifie.getModele() != null)
            voitureEnBase.setModele(voitureQuiModifie.getModele());
        if (voitureQuiModifie.getCouleur() != null)
            voitureEnBase.setCouleur(voitureQuiModifie.getCouleur());
        if (voitureQuiModifie.getNombreDePlaces() != null)
            voitureEnBase.setNombreDePlaces(voitureQuiModifie.getNombreDePlaces());
        if (voitureQuiModifie.getCarburant() != null)
            voitureEnBase.setCarburant(voitureQuiModifie.getCarburant());
        if (voitureQuiModifie.getTypeVoiture() != null) {
            voitureEnBase.setTypeVoiture(voitureQuiModifie.getTypeVoiture());
        }
        if (voitureQuiModifie.getNombresDePortes() != null)
            voitureEnBase.setNombresDePortes(voitureQuiModifie.getNombresDePortes());
        if (voitureQuiModifie.getTransmission() != null)
            voitureEnBase.setTransmission(voitureQuiModifie.getTransmission());
        if (voitureQuiModifie.getClimatisation() != null)
            voitureEnBase.setClimatisation(voitureQuiModifie.getClimatisation());
        if (voitureQuiModifie.getNombredeBagages() != null)
            voitureEnBase.setNombredeBagages(voitureQuiModifie.getNombredeBagages());
        if (voitureQuiModifie.getTarifJournalier() != null)
            voitureEnBase.setTarifJournalier(voitureQuiModifie.getTarifJournalier());
        if (voitureQuiModifie.getKilometrage() != null)
            voitureEnBase.setKilometrage(voitureQuiModifie.getKilometrage());
        if (voitureQuiModifie.getActif() != null)
            voitureEnBase.setActif(voitureQuiModifie.getActif());
        if (voitureQuiModifie.getRetireDuParc() != null)
            voitureEnBase.setRetireDuParc(voitureQuiModifie.getRetireDuParc());
    }


    private void verifierVoiture(VoitureRequestDto voitureRequestDto) {
        VerificationVehiculeUtil.verifierObjectNotNull(voitureRequestDto, "La requête est null");
        verifierAttributeVehicule(voitureRequestDto);
        verifierAttributeVoiture(voitureRequestDto);
    }

    public void verifierAttributeVehicule(VoitureRequestDto voitureRequestDto) {
        VerificationVehiculeUtil.verifierStringNotNullNotEmpty(voitureRequestDto.marque(), "La marque est obligatoire");
        VerificationVehiculeUtil.verifierStringNotNullNotEmpty(voitureRequestDto.modele(), "Le modèle est obligatoire");
        VerificationVehiculeUtil.verifierStringNotNullNotEmpty(voitureRequestDto.couleur(), "La couleur est obligatoire");
        VerificationVehiculeUtil.verifierIntegerNotNullNotZeroOrInferior(voitureRequestDto.tarifJournalier(), "Le tarif Journalier est obligatoire");
        VerificationVehiculeUtil.verifierIntegerNotNullNotZeroOrInferior(voitureRequestDto.kilometrage(), "Le kilomètrage est obligatoire");
        VerificationVehiculeUtil.verifierObjectNotNull(voitureRequestDto.retireDuParc(), "Precisez si le véhicule est retiré du parc");
    }

    public void verifierAttributeVoiture(VoitureRequestDto voitureRequestDto) {
        VerificationVehiculeUtil.verifierIntegerNotNullNotZeroOrInferior(voitureRequestDto.nombreDePlaces(),"Le nombre de places est obligatoire");
        VerificationVehiculeUtil.verifierIntegerNotNullNotZeroOrInferior(voitureRequestDto.nombredeBagages(),"Le nombre de bagages est obligatoire" );
        VerificationVehiculeUtil.verifierObjectNotNull(voitureRequestDto.actif(), "Le véhicule doit être actif ou inactif");
        VerificationVehiculeUtil.verifierObjectNotNull(voitureRequestDto.typeVoiture(), "Le type de voiture est obligatoire");
        VerificationVehiculeUtil.verifierObjectNotNull(voitureRequestDto.carburant(), "Le carburant est obligatoire");
        VerificationVehiculeUtil.verifierObjectNotNull(voitureRequestDto.nombresDePortes(), "Le nombre de portes est obligatoire");
        VerificationVehiculeUtil.verifierObjectNotNull(voitureRequestDto.transmission(),"Le type de transmission est obligatoire" );
        VerificationVehiculeUtil.verifierObjectNotNull(voitureRequestDto.climatisation(), "Le statut de la climatisation est obligatoire");

    }

    private void determinerPermis(Voiture voiture) {
        if (voiture.getNombreDePlaces() <= 9)
            voiture.setPermis(Permis.B);
       else if (voiture.getNombreDePlaces() < 17)
            voiture.setPermis(Permis.D1);
        else {
          VehiculeException vehiculeException = new VehiculeException("Le nombre de passages n'est pas adéquat");
          log.error("determinerPermis (Voiture voiture) : {}", vehiculeException.getMessage());
          throw vehiculeException;
        }
    }


}
