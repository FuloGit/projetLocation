package com.accenture.repository.entity.vehicule;
import com.accenture.shared.model.TypeVelo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "velos")
@Data
@NoArgsConstructor
public class Velo extends Vehicule {

    private Integer tailleDuCadre;
    private Integer poids;
    private Boolean electrique;
    private Integer autonomie;
    private Boolean freinADisque;
    @Enumerated(value = EnumType.STRING)
    private TypeVelo typeVelo;
    public Velo(Long id, String marque, String modele, String couleur, Integer tarifJournalier, Integer kilometrage, Boolean actif, Boolean retireDuParc, Integer tailleDuCadre, Integer poids, Boolean electrique, Integer autonomie, Boolean freinADisque, TypeVelo typeVelo) {
        super(id, marque, modele, couleur, tarifJournalier, kilometrage, actif, retireDuParc);
        this.tailleDuCadre = tailleDuCadre;
        this.poids = poids;
        this.electrique = electrique;
        this.autonomie = autonomie;
        this.freinADisque = freinADisque;
        this.typeVelo = typeVelo;
    }
}
