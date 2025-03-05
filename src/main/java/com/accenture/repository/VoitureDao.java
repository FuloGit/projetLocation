package com.accenture.repository;

import com.accenture.repository.entity.vehicule.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureDao extends JpaRepository<Voiture, Long> {
   List<Voiture> findByActifTrue();
   List<Voiture> findByActifFalse();
   List<Voiture> findByRetireDuParcFalse();
   List<Voiture> findByRetireDuParcTrue();

}
