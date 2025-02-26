package com.accenture.service.dto.utilisateur;

/**
 * Sert au retours à l'admin, ne retourne pas le password.
 * @param email
 * @param nom
 * @param prenom
 * @param fonction
 */
public record AdministrateurResponseDto(
        String email,
        String nom,
        String prenom,
        String fonction
) {
}
