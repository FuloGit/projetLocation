package com.accenture.repository.entity.utilisateur;

import com.accenture.shared.model.Permis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Le client qui utilisera le service de location.
 * La date d'inscription est générée automatiquement,
 * Le désactivé est automatiquement false, le compte est actif à sa création, mais il pourra être désactivé plus tard.
 */
@EqualsAndHashCode(callSuper = true)
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

}
