package com.accenture.repository.Entity.vehicule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe mère pour les véhicules, contient les donnnées communes à tous
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public class Vehicule {

    @Id
    @GeneratedValue (strategy = GenerationType.TABLE)
    private Long id;
    private String marque;
    private String modele;
    private String couleur;
    private Integer tarifJournalier;
    private Integer kilometrage;
    private Boolean actif;
    private Boolean retireDuParc;
}
