package com.accenture.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Classe utilisé pour les requêtes d'ajout et de modifications de la classe Client Entity, ne demande pas les attributs généré automatiquement comme la date de réation ou le status actif du compte
 * @param email
 * @param password
 * @param nom
 * @param prenom
 * @param adresse
 * @param dateDeNaissance
 */
public record ClientRequestDto(
        @NotBlank (message = "L'adresse email est obligatoire")
        String email,
        @NotBlank (message = "Le mot de passe est obligatoire")
        String password,
        @NotBlank(message = "Le nom est obligatoire")
        String nom,
        @NotBlank (message = "Le prénom est obligatoire")
        String prenom,
        @NotNull(message = "L'adresse est obligatoire")
        AdresseDto adresse,
        @NotNull(message = "La date de naissance est obligatoire")
        LocalDate dateDeNaissance
) {
}
