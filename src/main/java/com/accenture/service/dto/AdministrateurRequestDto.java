package com.accenture.service.dto;

/**
 * Sert à l'ajout d'un Admin
 * @param email
 * @param password
 * @param nom
 * @param prenom
 * @param fonction
 */
public record AdministrateurRequestDto(
        String email,
        String password,
        String nom,
        String prenom,
        String fonction ) {
}
