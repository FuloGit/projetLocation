package com.accenture.repository.entity.location;

import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.shared.model.EtatLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Location
 */
@Entity
@Table (name  ="locations")
@Data
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Vehicule vehicule;
    private LocalDate dateDeDebut;
    private LocalDate dateDeFin;
    private Long kilometreParcouru;
    private Long montantTotalCalcule;
    private LocalDate dateDeValidation;
    private EtatLocation etatLocation;

}
