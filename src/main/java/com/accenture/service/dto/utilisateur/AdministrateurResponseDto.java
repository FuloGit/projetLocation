package com.accenture.service.dto.utilisateur;

/**
 * Sert au retour de l'administrateur depuis la base de donnée, ne retourne pas le password.
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
