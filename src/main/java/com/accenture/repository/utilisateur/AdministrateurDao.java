package com.accenture.repository.utilisateur;

import com.accenture.repository.entity.utilisateur.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Gère les Instances Administrateur.class avec la base de données
 */
public interface AdministrateurDao extends JpaRepository<Administrateur,String> {
}
