package com.accenture.service.dto.utilisateur;


import jakarta.validation.constraints.NotBlank;

/**
 * Version sans l'ID de la classe Adresse pour transmission avec les utilisateurs.
 *
 * @param rue
 * @param codePostal
 * @param ville
 */
public record AdresseDto(
        @NotBlank (message = "La rue est obligatoire")
        String rue,
        @NotBlank(message = "Le code postal est obligatoire")
        String codePostal,
        @NotBlank(message = "La ville est obligatorie")
        String ville
) {

}
