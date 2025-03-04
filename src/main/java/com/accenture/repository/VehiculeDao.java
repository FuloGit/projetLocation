package com.accenture.repository;

import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.repository.entity.vehicule.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeDao extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByActifTrue();
    List<Vehicule> findByActifFalse();

    List<Vehicule> findByRetireDuParcFalse();
    List<Vehicule> findByRetireDuParcTrue();
}
