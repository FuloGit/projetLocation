package com.accenture.repository.entity.vehicule;
import com.accenture.shared.model.TypeVelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity Velo pour la base de donnée
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "velos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Velo extends Vehicule {

    private Integer tailleDuCadre;
    private Integer poids;
    private Boolean electrique;
    private Integer autonomie;
    private Boolean freinADisque;
    @Enumerated(value = EnumType.STRING)
    private TypeVelo typeVelo;

}
