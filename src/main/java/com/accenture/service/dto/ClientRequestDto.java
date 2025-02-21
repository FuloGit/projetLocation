package com.accenture.service.dto;

import java.time.LocalDate;

/**
 * Classe utilisé pour les requêtes d'ajout et de modifications de la classe Client Entity, ne demande pas les attributs généré automatiquement comme la date de réation ou le status actif du compte
 * @param email
 * @param password
 * @param nom
 * @param prenom
 * @param adresse
 * @param DateDeNaissance
 */
public record ClientRequestDto(
        String email,
        String password,
        String nom,
        String prenom,
        AdresseDto adresse,
        LocalDate DateDeNaissance
) {
}
