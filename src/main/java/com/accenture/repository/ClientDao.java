package com.accenture.repository;

import com.accenture.repository.entity.utilisateur.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *Transmet à la base de donnée
 */
public interface ClientDao extends JpaRepository<Client, String> {

}
