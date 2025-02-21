package com.accenture.repository;

import com.accenture.repository.Entity.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Transmet à la base de données</p>
 */
public interface AdministrateurDao extends JpaRepository<Administrateur,String> {
}
