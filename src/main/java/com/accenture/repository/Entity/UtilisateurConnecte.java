package com.accenture.repository.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe mère de Client, et Admin, qui aura pour Id le mail renseigné à la création du compte.
 */
@Data
@Entity
@NoArgsConstructor
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UtilisateurConnecte {

    @Id
    @Email
    @Column(unique = true)
    private String email;
    private String password;
    private String nom;
    private String prenom;

    public UtilisateurConnecte(String mail, String password, String nom, String prenom) {
        this.email = mail;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
    }
}
