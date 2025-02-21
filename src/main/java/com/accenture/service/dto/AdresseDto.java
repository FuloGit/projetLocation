package com.accenture.service.dto;


/**
 * <p>Version sans l'id de la classe adresse pour transmission avec les utilisateurs</p>
 * @param rue
 * @param codePostal
 * @param ville
 */
public record AdresseDto(String rue,
                         String codePostal,
                         String ville
) {

}
