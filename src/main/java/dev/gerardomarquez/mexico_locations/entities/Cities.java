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
 * Entidad que almacena todas las ciudades de México, por ejemplo la ciudad de México
 */
@Entity
@Table(name = "ciudades")
@Data
@NoArgsConstructor
public class Cities {

    /*
     * Id de la ciudad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Nombre de la ciudad
     */
    @Column(name = "ciudad")
    private String name;
}
