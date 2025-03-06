package com.accenture.repository.entity.utilisateur;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity des Administrateurs qui ont les droits sur la base de données.
 * Ajoute la fonction de l'administrateur par rapport à UtilisateurConnecté
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "Admins")
public class Administrateur extends UtilisateurConnecte {
    private String fonction;

    public Administrateur(String mail, String password, String nom, String prenom, String fonction) {
        super(mail, password, nom, prenom);
        this.fonction = fonction;
    }
}
