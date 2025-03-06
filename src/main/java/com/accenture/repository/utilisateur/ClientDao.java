package com.accenture.repository.utilisateur;

import com.accenture.repository.entity.utilisateur.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Gère les Instances de Client.class avec la base de donnée
 */
public interface ClientDao extends JpaRepository<Client, String> {

}
