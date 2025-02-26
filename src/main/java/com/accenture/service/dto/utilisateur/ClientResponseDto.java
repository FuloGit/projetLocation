package com.accenture.service.dto.utilisateur;

import com.accenture.shared.model.Permis;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * Renvoie ses infos à l'administrateur,  pas à l'utilisateur des informations comme  le mot de passe ou le statut du compte.
 * @param nom
 * @param prenom
 * @param email
 * @param adresse
 * @param dateDeNaissance
 * @param dateInscription
 * @param permis
 */

public record ClientResponseDto(
        String nom,
        String prenom,
        String email,
        AdresseDto adresse,
        LocalDate dateDeNaissance,
        LocalDate dateInscription,
        List<Permis> permis
) {
}
