package com.accenture.service.dto.utilisateur;

import jakarta.validation.constraints.NotBlank;

/**
 * Sert à l'ajout d'un Admin
 *
 * @param email
 * @param password
 * @param nom
 * @param prenom
 * @param fonction
 */
public record AdministrateurRequestDto(
        @NotBlank(message = "L'adresse email est obligatoire")
        String email,
        @NotBlank(message = "Le mot de passe est obligatoire")
        String password,
        @NotBlank(message = "Le nom est obligatoire")
        String nom,
        @NotBlank(message = "Le prénom est obligatoire")
        String prenom,
        @NotBlank(message = "La fonction est obligatoire")
        String fonction
        ) {
}
