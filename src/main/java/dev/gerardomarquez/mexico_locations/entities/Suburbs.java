package dev.gerardomarquez.mexico_locations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidad de la tabla que representa el catalogo de los asentamientos (barrios, colonias, etc.).
 */
@Entity
@Table(name = "asentamientos")
@Data
@NoArgsConstructor
public class Suburbs {

    /*
     * Identificador unico de los nombres de las colonias, barrios, condominios, asentamientos etc.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Nombre de asentamiento.
     */
    @Column(name = "asentamiento")
    private String name;
}
