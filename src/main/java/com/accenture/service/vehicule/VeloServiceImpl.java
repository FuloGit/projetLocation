package com.accenture.service.vehicule;
import com.accenture.exception.VehiculeException;
import com.accenture.repository.VeloDao;
import com.accenture.repository.entity.vehicule.Velo;
import com.accenture.service.dto.vehicule.VeloRequestDto;
import com.accenture.service.dto.vehicule.VeloResponseDto;


import com.accenture.service.mapper.VeloMapper;
import com.accenture.shared.model.FiltreListe;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class VeloServiceImpl implements VeloService {

    private VeloDao veloDao;
    private VeloMapper veloMapper;


    /**
     * Renvoie une VeloResponseDto après avoir vérifier la requete  et appeler save()
     *
     * @param veloRequestDto
     * @return
     * @throws VehiculeException si un champs est null ou blank
     */
    @Override
    public VeloResponseDto ajouterVelo(VeloRequestDto veloRequestDto) {
        verifierVelo(veloRequestDto);
        Velo velo = veloMapper.toVelo(veloRequestDto);
        if (!velo.getElectrique()){
            velo.setAutonomie(0);
        }
       return  veloMapper.toVeloResponseDto(veloDao.save(velo));
    }

    /**
     * Appel la méthode findAll() et retransmet la liste des velo en veloResponseDto
     * @return liste veloResponseDto
     */
    @Override
    public List<VeloResponseDto> trouverTous() {
        return veloDao.findAll().stream().map(velo -> veloMapper.toVeloResponseDto(velo)).toList();
    }


    /**
     * Utilise un switch pour renvoyer une liste différente selon la requete utilisateur
     *
     * @param filtreListe filtre transmis depuis le VeloController
     * @return renvoie une liste de VeloResponseDto selon le critère reçu depuis le controller
     */
    @Override
    public List<VeloResponseDto> trouverParFiltre(FiltreListe filtreListe) {
        List<Velo> liste = switch (filtreListe) {
            case ACTIFS ->
                    veloDao.findByActifTrue();
            case INACTIFS ->
                    veloDao.findByActifFalse();
            case HORS_DU_PARC ->
                   veloDao.findByRetireDuParcTrue();
            case DANS_LE_PARC ->
                    veloDao.findByRetireDuParcFalse();

        };
        return liste.stream().map(velo -> veloMapper.toVeloResponseDto(velo)).toList();
    }

    /**
     * Renvoie la veloResponseDto correspondant à l'id après vérification
     *
     * @param id
     * @return VeloResponseDto
     * @throws EntityNotFoundException si l'id n'existe pas en base
     */
    @Override
    public VeloResponseDto trouverParId(Long id) {
       return veloMapper.toVeloResponseDto(checkVelo(id));
    }


    /**
     * Appel deleteById() après vérification de la requête
     * A retravaillé une fois la classe location implémentée.
     *
     * @param id
     */
    @Override
    public void supprimerParId(Long id) {
if (veloDao.existsById(id))
    veloDao.deleteById(id);
else throw new EntityNotFoundException("Id non présent");
    }

    /**
     * Transfert les Valeurs du veloRequestDto puis vérifie les attributs avant save()
     * @param veloRequestDto
     * @param id
     * @throws VehiculeException
     * @return
     */
    @Override
    public VeloResponseDto modifierParId(VeloRequestDto veloRequestDto, Long id) {
        Velo veloEnBase = checkVelo(id);
        if(veloEnBase.getRetireDuParc())
            throw new VehiculeException("Une voiture retirée du parc n'est pas modifiable");
        Velo veloQuiModifie = veloMapper.toVelo(veloRequestDto);
        remplacer(veloQuiModifie, veloEnBase);
        verifierVelo(veloMapper.toVeloRequestDto(veloEnBase));
        return veloMapper.toVeloResponseDto(veloDao.save(veloEnBase));
    }




    private void verifierVelo(VeloRequestDto veloRequestDto) {
        UtilVerificationVehicule.verifierObjectNotNull(veloRequestDto, "La requête est null");
        verifierAttributVehicule(veloRequestDto);
        verifierAttributVelo(veloRequestDto);
    }

    public void verifierAttributVehicule(VeloRequestDto veloRequestDto) {
        UtilVerificationVehicule.verifierStringNotNullNotEmpty(veloRequestDto.marque(), "La marque est obligatoire");
        UtilVerificationVehicule.verifierStringNotNullNotEmpty(veloRequestDto.modele(), "Le modèle est obligatoire");
        UtilVerificationVehicule.verifierStringNotNullNotEmpty(veloRequestDto.couleur(), "La couleur est obligatoire");
        UtilVerificationVehicule.verifierIntegerNotNullNotZeroOrInferior(veloRequestDto.tarifJournalier(), "Le tarif Journalier est obligatoire");
        UtilVerificationVehicule.verifierIntegerNotNullNotZeroOrInferior(veloRequestDto.kilometrage(), "Le kilomètrage est obligatoire");
        UtilVerificationVehicule.verifierObjectNotNull(veloRequestDto.retireDuParc(), "Precisez si le véhicule est retiré du parc");
    }

    public void verifierAttributVelo (VeloRequestDto veloRequestDto){
        UtilVerificationVehicule.verifierIntegerNotNullNotZeroOrInferior(veloRequestDto.tailleDuCadre(), "La taille de cadre est obligatoire");
        UtilVerificationVehicule.verifierIntegerNotNullNotZeroOrInferior(veloRequestDto.poids(), "Le poids du vélo est obligatoire");
        UtilVerificationVehicule.verifierObjectNotNull(veloRequestDto.electrique(), "Précisez si le vélo est électrique ou non");
        UtilVerificationVehicule.verifierObjectNotNull(veloRequestDto.freinADisque(), "Précisez si le vélo posséde des freins à disque ou non");
        UtilVerificationVehicule.verifierObjectNotNull(veloRequestDto.typeVelo(), "Le type de vélo est obligatoire");
        if (veloRequestDto.electrique()){
            UtilVerificationVehicule.verifierIntegerNotNullNotZeroOrInferior(veloRequestDto.autonomie(), "L'autonomie est obligatoire pour un vélo électrique");
        }

    }
    private Velo checkVelo(Long id) {
        Optional<Velo> optionalVelo = veloDao.findById(id);
        if (optionalVelo.isEmpty())
            throw new EntityNotFoundException("Id non présent");
        return optionalVelo.get();
    }

    private void remplacer(Velo veloQuiModifie, Velo veloEnBase) {
        if (veloQuiModifie.getMarque() != null)
            veloEnBase.setMarque(veloQuiModifie.getMarque());
        if (veloQuiModifie.getModele() != null)
            veloEnBase.setModele(veloQuiModifie.getModele());
        if (veloQuiModifie.getCouleur() != null)
            veloEnBase.setCouleur(veloQuiModifie.getCouleur());
        if (veloQuiModifie.getTarifJournalier() != null)
            veloEnBase.setTarifJournalier(veloQuiModifie.getTarifJournalier());
        if (veloQuiModifie.getKilometrage() != null)
            veloEnBase.setKilometrage(veloQuiModifie.getKilometrage());
        if (veloQuiModifie.getActif() != null)
            veloEnBase.setActif(veloQuiModifie.getActif());
        if (veloQuiModifie.getRetireDuParc() != null)
            veloEnBase.setRetireDuParc(veloQuiModifie.getRetireDuParc());
        if (veloQuiModifie.getTailleDuCadre() != null)
            veloEnBase.setTailleDuCadre(veloQuiModifie.getTailleDuCadre());
        if (veloQuiModifie.getPoids() != null)
            veloEnBase.setPoids(veloQuiModifie.getPoids());
        if (veloQuiModifie.getElectrique() != null)
            veloEnBase.setElectrique(veloQuiModifie.getElectrique());
        if (veloQuiModifie.getAutonomie() != null)
            veloEnBase.setAutonomie(veloEnBase.getAutonomie());
        if (veloQuiModifie.getFreinADisque() != null)
            veloEnBase.setFreinADisque(veloQuiModifie.getFreinADisque());
        if (veloQuiModifie.getTypeVelo() != null)
            veloEnBase.setTypeVelo(veloQuiModifie.getTypeVelo());
    }

}
