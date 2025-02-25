package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.repository.Entity.vehicule.Voiture;
import com.accenture.repository.VoitureDao;
import com.accenture.service.dto.vehicule.VoitureRequestDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.model.Permis;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoitureServiceImpl implements VoitureService {

    private VoitureDao voitureDao;
    private VoitureMapper voitureMapper;

    @Override
    public VoitureResponseDto ajouter(VoitureRequestDto voitureRequestDto) throws VoitureException {
        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        determinerPermis(voiture);
        return voitureMapper.toVoitureResponseDto(voitureDao.save(voiture));
    }

    @Override
    public List<VoitureResponseDto> lister() {
        return List.of();
    }

    private void verifierVoiture(VoitureRequestDto voitureRequestDto){
        if (voitureRequestDto == null)
            throw new VoitureException("La requête est null");
        if (voitureRequestDto.marque() == null || voitureRequestDto.marque().isBlank())
            throw new VoitureException("La marque est obligatoire");
        if(voitureRequestDto.modele() == null || voitureRequestDto.modele().isBlank())
            throw new VoitureException("Le modèle est obligatoire");
        if (voitureRequestDto.couleur() == null || voitureRequestDto.couleur().isBlank())
            throw new VoitureException("La couleur est obligatoire");
        if (voitureRequestDto.tarifJournalier() == 0)
            throw new VoitureException("Le tarrif Journalier est obligatoire");
        if (voitureRequestDto.kilometrage() == 0)
            throw new VoitureException("Le kilomètrage est obligatoire");
        if (voitureRequestDto.actif() == null)
            throw new VoitureException("Le véhicule doit être actif ou inactif");
        if (voitureRequestDto.nombreDePlaces() == 0)
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
        if (voitureRequestDto.nombredeBagages() == 0)
            throw new VoitureException("Le nombres de bagages est obligatoire");
        }
        private void determinerPermis(Voiture voiture){
        if (voiture.getNombreDePlaces() > 0 && voiture.getNombreDePlaces() <= 9)
            voiture.setPermis(Permis.B);
        else if (voiture.getNombreDePlaces() >=10 && voiture.getNombreDePlaces() <17)
            voiture.setPermis(Permis.D1);
        else throw new VoitureException("Le nombre de passages n'est pas adéquat");
        }
    }

