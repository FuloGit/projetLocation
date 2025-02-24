package com.accenture.repository.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity complémentaire au Client, sera associé en OneToOne à celui-ci
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="adresses")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rue;
    private String codePostal;
    private String ville;

    public Adresse(String rue, String codePostal, String ville) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }
}

