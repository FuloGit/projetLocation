package com.accenture.repository.entity.utilisateur;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe mère de Client, et Admin, qui aura pour Id le email renseigné à la création du compte.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UtilisateurConnecte {

    @Id
    @Email
    @Column(unique = true)
    private String email;
    private String password;
    private String nom;
    private String prenom;


}
