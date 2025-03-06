package com.accenture.repository.vehicule;


import com.accenture.repository.entity.vehicule.Velo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Gére les instances de Velo.class avec la base de donnée.
 * Inclut des méthodes supplementaires permettant de chercher par l'attribut Actif, et RetirerDuParc.
 */
public interface VeloDao extends JpaRepository<Velo, Long> {
    List<Velo> findByActifTrue();
    List<Velo> findByActifFalse();
    List<Velo> findByRetireDuParcFalse();
    List<Velo> findByRetireDuParcTrue();
}
