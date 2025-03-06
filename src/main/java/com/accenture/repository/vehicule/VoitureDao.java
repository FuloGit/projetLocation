package com.accenture.repository.vehicule;

import com.accenture.repository.entity.vehicule.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Gére les instances de Voiture.class avec la base de donnée.
 * Inclut des méthodes supplementaires permettant de chercher par l'attribut Actif, et RetirerDuParc.
 */
public interface VoitureDao extends JpaRepository<Voiture, Long> {
   List<Voiture> findByActifTrue();
   List<Voiture> findByActifFalse();
   List<Voiture> findByRetireDuParcFalse();
   List<Voiture> findByRetireDuParcTrue();

}
