package com.accenture.repository.Entity.utilisateur;

import com.accenture.shared.model.Permis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 *Le client qui utilisera la base de donnée le service de location.
 * La date d'inscription est générée automatiquement
 * Le desactivé est automatiquement false, le compte est actif à sa création mais pourra être désactiver plus tard
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name= "clients")
public class Client extends UtilisateurConnecte {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Adresse adresse;
    private LocalDate dateDeNaissance;
    private LocalDate dateInscription = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private List<Permis> permis;
    private Boolean desactive = false;

    public Client(String mail, String password, String nom, String prenom, Adresse adresse, LocalDate dateDeNaissance, List<Permis> permis) {
        super(mail, password, nom, prenom);
        this.adresse = adresse;
        this.dateDeNaissance = dateDeNaissance;
        this.permis = permis;
    }
}
