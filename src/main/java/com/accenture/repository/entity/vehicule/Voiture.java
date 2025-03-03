package com.accenture.repository.entity.vehicule;

import com.accenture.service.dto.vehicule.VoitureResponseDto;
import com.accenture.shared.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * entity voiturep pour la base de donnée
 */
@Data
@NoArgsConstructor

@Entity
@Table (name = "voitures")
public class Voiture extends Vehicule {
private TypeVoiture typeVoiture;
    private Integer nombreDePlaces;
    @Enumerated(value = EnumType.STRING)
    private Carburant carburant;
    @Enumerated(value = EnumType.STRING)
    private NombresDePortes nombresDePortes;
    @Enumerated(value = EnumType.STRING)
    private Transmission transmission;
    private Boolean climatisation;
    private Integer nombredeBagages;
    @Enumerated(value = EnumType.STRING)
    private Permis permis;



    public Voiture(Long id, String marque, String modele, String couleur, Integer tarifJournalier, Integer kilometrage, Boolean actif, Boolean retireDuParc, TypeVoiture typeVoiture, Integer nombreDePlaces, Carburant carburant, NombresDePortes nombresDePortes, Transmission transmission, Boolean climatisation, Integer nombredeBagages) {
        super(id, marque, modele, couleur, tarifJournalier, kilometrage, actif, retireDuParc);
        this.typeVoiture = typeVoiture;
        this.nombreDePlaces = nombreDePlaces;
        this.carburant = carburant;
        this.nombresDePortes = nombresDePortes;
        this.transmission = transmission;
        this.climatisation = climatisation;
        this.nombredeBagages = nombredeBagages;
    }
}
