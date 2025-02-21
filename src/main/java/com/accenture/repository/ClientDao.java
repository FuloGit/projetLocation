package com.accenture.repository;

import com.accenture.repository.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *<p>Transmet à la base de donnée</p>
 */
public interface ClientDao extends JpaRepository<Client, String> {

}
