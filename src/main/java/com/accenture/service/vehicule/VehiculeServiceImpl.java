package com.accenture.service.vehicule;

import com.accenture.repository.LocationDao;
import com.accenture.repository.VehiculeDao;

import com.accenture.repository.entity.Location;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.repository.entity.vehicule.Velo;
import com.accenture.repository.entity.vehicule.Voiture;
import com.accenture.service.dto.vehicule.VehiculeDto;
import com.accenture.service.dto.vehicule.VeloResponseDto;
import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.service.mapper.VeloMapper;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.model.CategorieVehicule;
import com.accenture.shared.model.FiltreListe;
import com.accenture.shared.model.TypeVelo;
import com.accenture.shared.model.TypeVoiture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface Véhicule Service
 */
@Service
@AllArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private VehiculeDao vehiculeDao;
    private LocationDao locationDao;
    private VeloMapper veloMapper;
    private VoitureMapper voitureMapper;

    /**
     * Remonte les véhicules
     *
     * @return
     */
    @Override
    public VehiculeDto trouverTous() {
        return toVehiculeDto(vehiculeDao.findAll());
    }

    /**
     * Recherche les véhicules par filtres
     *
     * @param filtreListe
     * @return
     */
    public VehiculeDto trouverParFiltre(FiltreListe filtreListe) {
        List<Vehicule> liste = switch (filtreListe) {
            case ACTIFS -> vehiculeDao.findByActifTrue();
            case INACTIFS -> vehiculeDao.findByActifFalse();
            case HORS_DU_PARC -> vehiculeDao.findByRetireDuParcTrue();
            case DANS_LE_PARC -> vehiculeDao.findByRetireDuParcFalse();
        };
        return toVehiculeDto(liste);
    }

    /**
     * Chercher un véhiculeDto par date de locations
     *
     * @param
     * @param dateDeFin
     * @return
     */
    @Override
    public VehiculeDto trouverParDateEtCategorie(LocalDate dateDeDebut, LocalDate dateDeFin, CategorieVehicule categorieVehicule, TypeVoiture typeVoiture, TypeVelo typeVelo) {
        List<Vehicule> vehiculeListe = vehiculeDao.findAll();
        vehiculeListe = filtrerParDispo(vehiculeListe, dateDeDebut, dateDeFin);
        vehiculeListe = filtrerParCategorie(vehiculeListe, categorieVehicule);
        vehiculeListe = filtrerParType(vehiculeListe, typeVoiture, typeVelo);

        return toVehiculeDto(vehiculeListe);
    }


    private List<Vehicule> filtrerParCategorie(List<Vehicule> vehiculeListe, CategorieVehicule categorieVehicule) {
       return switch (categorieVehicule) {
            case VELO -> vehiculeListe.stream().filter(Velo.class::isInstance).toList();
            case VOITURE -> vehiculeListe.stream().filter(Voiture.class::isInstance).toList();};
    }

    private List<Vehicule> filtrerParDispo(List<Vehicule> vehiculeList, LocalDate dateDedebut, LocalDate dateDeFin) {
        List<Location> locationList = locationDao.findAll();
        List<Vehicule> vehiculeIndispo = locationList.stream().filter(location -> location.getDateDeDebut().isBefore(dateDeFin) && location.getDateDeFin().isAfter(dateDedebut))
                .map(Location::getVehicule).toList();
        vehiculeList.removeAll(vehiculeIndispo);

        return vehiculeList;
    }

    private List<Vehicule> filtrerParType(List<Vehicule> list, TypeVoiture typevoiture, TypeVelo typeVelo) {
        List<Vehicule> listeFiltree = new ArrayList<>();
        if (typeVelo != null){
        for (Vehicule v : list) {
            if (v instanceof Velo velo && velo.getTypeVelo() == typeVelo)
                listeFiltree.add(velo);
        }} else if (typevoiture!= null){
            for (Vehicule v : list){
                if (v instanceof Voiture voiture && voiture.getTypeVoiture() == typevoiture)
                    listeFiltree.add(voiture);
            }
        } else
            listeFiltree = list;


        return listeFiltree;
    }

    private VehiculeDto toVehiculeDto(List<Vehicule> listeVehicule) {
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
        return new VehiculeDto(listeVoiture, listeVelo);
    }


}