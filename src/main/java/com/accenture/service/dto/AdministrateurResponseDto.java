package com.accenture.service.dto;

/**
 * Sert au retours à l'utilisateur, ne retourne pas le password
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
