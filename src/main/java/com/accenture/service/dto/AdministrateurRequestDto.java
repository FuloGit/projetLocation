package com.accenture.service.dto;

import com.accenture.shared.model.Permis;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

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
