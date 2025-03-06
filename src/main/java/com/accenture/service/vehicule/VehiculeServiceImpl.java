package com.accenture.service.vehicule;

import com.accenture.repository.location.LocationDao;
import com.accenture.repository.vehicule.VehiculeDao;

import com.accenture.repository.entity.location.Location;
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
     * Remonte les véhicules de la base, puis appel la méthode toVehiculeDto pour le tri et transformation en ResponseDto des Entity
     *
     * @return VehiculeDto
     */
    @Override
    public VehiculeDto trouverTous() {
        return toVehiculeDto(vehiculeDao.findAll());
    }

    /**
     * Recherche les véhicules par filtre : ACTIF, INACTIF, HORS_PARC, DANS_LE_PARC, puis appel la méthode toVehiculeDto pour le tri et transformation
     *
     * @param filtreListe pour le switch
     * @return VehiculeDto
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
     * Recherche les véhicules selon différents critères entrées en attributs.
     * Appel les méthodes, filtrerParDispo, filtrerParCategorie, filtrerParType
     * Puis transformer avec la méthode toVéhiculeDto
     *
     * @param dateDeDebut       pour la méthode filtrerParDate, récupère la liste de location et exclus les véhicules dont la location n'est pas finis à la date de début
     * @param dateDeFin         pour la méthode filtrerParDate, récupère la liste de location et exclus les véhicules dont la location commence avant la date de fin entrée
     * @param categorieVehicule Sert à l'appel d'un switch dans la méthode filtrerParCategorie
     * @param typeVoiture       filtre utilisé dans la méthode filtrerParType
     * @param typeVelo          filtrer utilisé dans la méthode filtrerParType
     * @return VehiculeDto
     */
    @Override
    public VehiculeDto trouverParParametres(LocalDate dateDeDebut, LocalDate dateDeFin, CategorieVehicule categorieVehicule, TypeVoiture typeVoiture, TypeVelo typeVelo) {
        List<Vehicule> vehiculeListe = vehiculeDao.findAll();


        filtrerParDate(vehiculeListe, dateDeDebut, dateDeFin);

        vehiculeListe = filtrerParCategorie(vehiculeListe, categorieVehicule);

        if (typeVoiture != null)
            vehiculeListe = filtrerParTypeVoiture(vehiculeListe, typeVoiture);
        if (typeVelo != null)
            vehiculeListe = filtrerParTypeVelo(vehiculeListe, typeVelo);

        return toVehiculeDto(vehiculeListe);
    }


    private List<Vehicule> filtrerParCategorie(List<Vehicule> vehiculeListe, CategorieVehicule categorieVehicule) {
        return switch (categorieVehicule) {
            case null -> vehiculeListe;
            case VELO -> vehiculeListe.stream().filter(Velo.class::isInstance).toList();
            case VOITURE -> vehiculeListe.stream().filter(Voiture.class::isInstance).toList();
        };
    }

    private void filtrerParDate(List<Vehicule> vehiculeList, LocalDate dateDedebut, LocalDate dateDeFin) {
        List<Location> locationList = locationDao.findAll();
        List<Vehicule> vehiculeIndispo = locationList.stream().filter(location -> location.getDateDeDebut().isBefore(dateDeFin) && location.getDateDeFin().isAfter(dateDedebut))
                .map(Location::getVehicule).toList();
        vehiculeList.removeAll(vehiculeIndispo);

    }


    private List<Vehicule> filtrerParTypeVoiture(List<Vehicule> list, TypeVoiture typevoiture) {
        List<Vehicule> listeFiltree = new ArrayList<>();
        for (Vehicule v : list) {
            if (v instanceof Voiture voiture && voiture.getTypeVoiture() == typevoiture)
                listeFiltree.add(voiture);
        }
        return listeFiltree;
    }

    private List<Vehicule> filtrerParTypeVelo(List<Vehicule> list, TypeVelo typeVelo) {
        List<Vehicule> listeFiltree = new ArrayList<>();
        if (typeVelo != null) {
            for (Vehicule v : list) {
                if (v instanceof Velo velo && velo.getTypeVelo() == typeVelo)
                    listeFiltree.add(velo);
            }
        }
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