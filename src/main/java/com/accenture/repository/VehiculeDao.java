package com.accenture.repository;

import com.accenture.repository.entity.vehicule.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeDao extends JpaRepository<Vehicule, Long> {
}
