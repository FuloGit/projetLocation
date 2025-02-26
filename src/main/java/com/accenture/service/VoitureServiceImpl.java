package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.repository.Entity.vehicule.Voiture;
import com.accenture.repository.VoitureDao;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.model.FiltreListe;
import com.accenture.shared.model.Permis;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoitureServiceImpl implements VoitureService {

    private VoitureDao voitureDao;
    private VoitureMapper voitureMapper;

    /**
     * Verifie la voiture Request avant de la transmettre à la base
     *
     * @param voitureRequestDto
     * @return
     * @throws VoitureException
     */
    @Override
    public VoitureResponseDto ajouter(VoitureRequestDto voitureRequestDto) throws VoitureException {
        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        determinerPermis(voiture);
        return voitureMapper.toVoitureResponseDto(voitureDao.save(voiture));
    }

    /**
     * Liste des voitures en base
     *
     * @return
     */
    @Override
    public List<VoitureResponseDto> lister() {
        return voitureDao.findAll().stream()
                .map(voiture -> voitureMapper.toVoitureResponseDto(voiture)).toList();
    }

    /**
     * Permet d'afficher une liste différente suivant le filtre transmis depuis le controller
     * @param filtreListe
     * @return
     */
    @Override
    public List<VoitureResponseDto> listerParRequete(FiltreListe filtreListe) {
        List<VoitureResponseDto> liste = new ArrayList<>();
        switch (filtreListe) {
            case ACTIFS ->
                    liste = voitureDao.findByActifTrue().stream().map(voiture -> voitureMapper.toVoitureResponseDto(voiture)).toList();

            case INACTIFS ->
                    liste = voitureDao.findByActifFalse().stream().map(voiture -> voitureMapper.toVoitureResponseDto(voiture)).toList();

            case HORS_DU_PARC ->
                    liste = voitureDao.findByRetireDuParcTrue().stream().map(voiture -> voitureMapper.toVoitureResponseDto(voiture)).toList();

            case DANS_LE_PARC ->
                    liste = voitureDao.findByRetireDuParcFalse().stream().map(voiture -> voitureMapper.toVoitureResponseDto(voiture)).toList();

        }
        return liste;
    }

    /**
     * CHercher Voiture par id, renvoie Exeception si l'id ne correspond à rien en base
     *
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public VoitureResponseDto trouver(Long id) throws EntityNotFoundException {
        Optional<Voiture> optionalVoiture = voitureDao.findById(id);
        if (optionalVoiture.isEmpty())
            throw new EntityNotFoundException("Id non présent");
        return voitureMapper.toVoitureResponseDto(optionalVoiture.get());
    }

    @Override
    public void supprimer(Long id) {
        //TODO Changer une fois la notion de Location
        if (voitureDao.existsById(id))
            voitureDao.deleteById(id);
        else
            throw new EntityNotFoundException("Id non présent");
    }

    @Override
    public VoitureResponseDto modifier(VoitureRequestDto voitureRequestDto, Long id) {
        Optional<Voiture> optionalVoiture = voitureDao.findById(id);
        if (optionalVoiture.isEmpty())
            throw new VoitureException("Id non présent");
        Voiture voitureEnBase = optionalVoiture.get();
        if (voitureEnBase.getRetireDuParc() == true)
            throw new VoitureException("Une voiture retirée du parc n'est pas modifiable");
        Voiture voitureQuiModifie = voitureMapper.toVoiture(voitureRequestDto);
        remplacer(voitureQuiModifie, voitureEnBase);
        verifierVoiture(voitureMapper.toVoitureRequestDto(voitureEnBase));
        return voitureMapper.toVoitureResponseDto(voitureDao.save(voitureEnBase));
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

        if (voitureQuiModifie.getNombresDePortes() != null)
            voitureEnBase.setNombresDePortes(voitureQuiModifie.getNombresDePortes());
        if (voitureQuiModifie.getTransmission() != null)
            voitureEnBase.setTransmission(voitureQuiModifie.getTransmission());
        if (voitureQuiModifie.getClimatisation() != null)
            voitureEnBase.setClimatisation(voitureQuiModifie.getClimatisation());
        if (voitureQuiModifie.getNombredeBagages() != null)
            voitureEnBase.setNombredeBagages(voitureQuiModifie.getNombredeBagages());
        if (voitureQuiModifie.getTarifJournalier() != null)
            voitureEnBase.setClimatisation(voitureQuiModifie.getClimatisation());
        if (voitureQuiModifie.getKilometrage() != null)
            voitureEnBase.setClimatisation(voitureQuiModifie.getClimatisation());
        if (voitureQuiModifie.getActif() != null)
            voitureEnBase.setActif(voitureQuiModifie.getActif());
        if (voitureQuiModifie.getRetireDuParc()!= null)
            voitureEnBase.setRetireDuParc(voitureQuiModifie.getRetireDuParc());
    }


    private void verifierVoiture(VoitureRequestDto voitureRequestDto) {
        if (voitureRequestDto == null)
            throw new VoitureException("La requête est null");
        if (voitureRequestDto.marque() == null || voitureRequestDto.marque().isBlank())
            throw new VoitureException("La marque est obligatoire");
        if (voitureRequestDto.modele() == null || voitureRequestDto.modele().isBlank())
            throw new VoitureException("Le modèle est obligatoire");
        if (voitureRequestDto.couleur() == null || voitureRequestDto.couleur().isBlank())
            throw new VoitureException("La couleur est obligatoire");
        if (voitureRequestDto.tarifJournalier() == null || voitureRequestDto.tarifJournalier() <= 0)
            throw new VoitureException("Le tarif Journalier est obligatoire");
        if (voitureRequestDto.kilometrage() == null || voitureRequestDto.kilometrage() <= 0)
            throw new VoitureException("Le kilomètrage est obligatoire");
        if (voitureRequestDto.actif() == null)
            throw new VoitureException("Le véhicule doit être actif ou inactif");
        if (voitureRequestDto.nombreDePlaces() == null || voitureRequestDto.nombreDePlaces() <= 0 )
            throw new VoitureException("Le nombre de places est obligatoire");
        if (voitureRequestDto.carburant() == null)
            throw new VoitureException("Le carburant est obligatoire");
        if (voitureRequestDto.nombresDePortes() == null)
            throw new VoitureException("Le nombre de portes est obligatoire");
        if (voitureRequestDto.retireDuParc() == null)
            throw new VoitureException("Precisez si le véhicule est retiré du parc");
        if (voitureRequestDto.transmission() == null)
            throw new VoitureException("Le type de transmission est obligatoire");
        if (voitureRequestDto.climatisation() == null)
            throw new VoitureException("Le statut de la climatisation est obligatoire");
        if (voitureRequestDto.nombredeBagages() == null || voitureRequestDto.nombredeBagages() <= 0)
            throw new VoitureException("Le nombre de bagages est obligatoire");
    }

    private void determinerPermis(Voiture voiture) {
        if (voiture.getNombreDePlaces() > 0 && voiture.getNombreDePlaces() <= 9)
            voiture.setPermis(Permis.B);
        else if (voiture.getNombreDePlaces() >= 10 && voiture.getNombreDePlaces() < 17)
            voiture.setPermis(Permis.D1);
        else throw new VoitureException("Le nombre de passages n'est pas adéquat");
    }

}
