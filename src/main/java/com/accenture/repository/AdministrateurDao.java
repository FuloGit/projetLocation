package com.accenture.repository;

import com.accenture.repository.entity.utilisateur.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Transmet à la base de données
 */
public interface AdministrateurDao extends JpaRepository<Administrateur,String> {
}
