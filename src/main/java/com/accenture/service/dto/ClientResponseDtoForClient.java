package com.accenture.service.dto;

import com.accenture.shared.model.Permis;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * Ne renvoie pas à l'utilisateur des informations comme  le mot de passe ou le statut du compte.
 * @param nom
 * @param prenom
 * @param email
 * @param adresse
 * @param dateDeNaissance
 * @param incription
 * @param permis
 */

public record ClientResponseDtoForClient(
        String nom,
        String prenom,
        String email,
        AdresseDto adresse,
        LocalDate dateDeNaissance,
        LocalDate incription,
        List<Permis> permis
) {
}
