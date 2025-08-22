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
 * Catalogo de tipos de zonas.
 */
@Entity
@Table(name = "catalogo_de_zonas")
@Data
@NoArgsConstructor
public class Zones {

    /*
     * Identificador para el tipo de zona
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    /*
     * Tipo de zona
     */
    @Column(name = "tipo_de_zona")
    private String zone;
}
