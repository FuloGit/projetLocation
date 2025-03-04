package com.accenture.service.vehicule;

import com.accenture.repository.LocationDao;
import com.accenture.repository.VehiculeDao;
import com.accenture.repository.VeloDao;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.Location;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.repository.entity.vehicule.Velo;
import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.VehiculeDto;
import com.accenture.service.dto.vehicule.VeloResponseDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.service.mapper.VehiculeMapper;
import com.accenture.service.mapper.VeloMapper;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.model.FiltreListe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private VehiculeDao vehiculeDao;
  private LocationDao locationDao;
    private VeloMapper veloMapper;
    private VoitureMapper voitureMapper;

    @Override
    public VehiculeDto trouverTous() {
        return toVehiculeDto(vehiculeDao.findAll());
    }

    @Override
    public VehiculeDto trouverParFiltre(FiltreListe filtreListe) {
        List<Vehicule> liste = switch (filtreListe) {
            case ACTIFS -> vehiculeDao.findByActifTrue();
            case INACTIFS -> vehiculeDao.findByActifFalse();
            case HORS_DU_PARC -> vehiculeDao.findByRetireDuParcTrue();
            case DANS_LE_PARC -> vehiculeDao.findByRetireDuParcFalse();
        };
        return toVehiculeDto(liste);
    }
    public VehiculeDto trouverParDate(LocalDate dateDedebut, LocalDate dateDeFin){
        List<Location> locationList = locationDao.findAll();
       List<Vehicule> vehiculeIndispo = locationList.stream().filter( location -> location.getDateDeDebut().isBefore(dateDeFin) && location.getDateDeFin().isAfter(dateDedebut))
                .map( location -> location.getVehicule()).toList();
       List<Vehicule> listeToutLesVehicule = vehiculeDao.findAll();
       listeToutLesVehicule.removeAll(vehiculeIndispo);
       return toVehiculeDto(listeToutLesVehicule);
    }

    private VehiculeDto toVehiculeDto(List<Vehicule> listeVehicule){
       List<VeloResponseDto> listeVelo = new ArrayList<>();
       List<VoitureResponseDto> listeVoiture = new ArrayList<>();
        for (Vehicule v : listeVehicule) {
            if (v instanceof Velo velo) {
                listeVelo.add(veloMapper.toVeloResponseDto(velo));
            }
            if (v instanceof Voiture voiture) {
                listeVoiture.add(voitureMapper.toVoitureResponseDto(voiture));
            }
        }
        return new VehiculeDto(listeVoiture,listeVelo);
    }

}