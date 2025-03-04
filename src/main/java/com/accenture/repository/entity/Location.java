package com.accenture.repository.entity;

import com.accenture.repository.entity.utilisateur.Client;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.shared.model.EtatLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

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

    public Location(Long id, Client client, Vehicule vehicule, LocalDate dateDeDebut, LocalDate dateDeFin, Long kilometreParcouru, LocalDate dateDeValidation, EtatLocation etatLocation) {
        this.id = id;
        this.client = client;
        this.vehicule = vehicule;
        this.dateDeDebut = dateDeDebut;
        this.dateDeFin = dateDeFin;
        this.kilometreParcouru = kilometreParcouru;
        this.dateDeValidation = dateDeValidation;
        this.etatLocation = etatLocation;
    }
}
