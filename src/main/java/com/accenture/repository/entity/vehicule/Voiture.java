package com.accenture.repository.entity.vehicule;

import com.accenture.shared.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity Voiture pour la base de donnée
 * Le permis est déterminé automatiquement selon le nombre de places.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "voitures")
public class Voiture extends Vehicule {
    @Enumerated(value = EnumType.STRING)
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

}
