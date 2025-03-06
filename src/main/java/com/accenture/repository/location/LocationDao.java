package com.accenture.repository.location;

import com.accenture.repository.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Gère les instances de Location.class avec la base de donnée.
 */
public interface LocationDao extends JpaRepository<Location, Long> {
}
