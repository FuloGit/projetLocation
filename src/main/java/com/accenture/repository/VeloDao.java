package com.accenture.repository;


import com.accenture.repository.entity.vehicule.Velo;
import com.accenture.repository.entity.vehicule.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeloDao extends JpaRepository<Velo, Long> {
    List<Velo> findByActifTrue();
    List<Velo> findByActifFalse();
    List<Velo> findByRetireDuParcFalse();
    List<Velo> findByRetireDuParcTrue();
}
