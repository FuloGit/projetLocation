package com.accenture.repository.vehicule;

import com.accenture.repository.entity.vehicule.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Gère les instances de Vehicule.class avec la base de donnée
 * Inclut des méthodes supplementaires permettant de chercher par l'attribut Actif, et RetirerDuParc.
 */
public interface VehiculeDao extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByActifTrue();
    List<Vehicule> findByActifFalse();

    List<Vehicule> findByRetireDuParcFalse();
    List<Vehicule> findByRetireDuParcTrue();
}
