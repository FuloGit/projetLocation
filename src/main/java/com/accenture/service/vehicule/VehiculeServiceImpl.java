package com.accenture.service.vehicule;

import com.accenture.repository.VehiculeDao;
import com.accenture.repository.VeloDao;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.repository.entity.vehicule.Velo;
import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.VehiculeDto;
import com.accenture.service.dto.vehicule.VeloResponseDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.service.mapper.VehiculeMapper;
import com.accenture.service.mapper.VeloMapper;
import com.accenture.service.mapper.VoitureMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private VehiculeDao vehiculeDao;
    private VehiculeMapper vehiculeMapper;
    private VeloDao veloDao;
    private VoitureDao voitureDao;
    private VeloMapper veloMapper;
    private VoitureMapper voitureMapper;

    @Override
    public VehiculeDto trouverTous() {
        List<VeloResponseDto> listeVelo = new ArrayList<>();
        List<VoitureResponseDto> listeVoiture = new ArrayList<>();
        List<Vehicule> listevehicule = vehiculeDao.findAll();
        for (Vehicule v : listevehicule) {
            if (v instanceof Velo velo) {
                listeVelo.add(veloMapper.toVeloResponseDto(velo));
            }
            if (v instanceof Voiture voiture) {
                listeVoiture.add(voitureMapper.toVoitureResponseDto(voiture));
            }
        }
        return new VehiculeDto(listeVoiture, listeVelo);
    }
}