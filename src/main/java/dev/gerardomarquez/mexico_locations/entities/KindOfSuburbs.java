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
 * Catalogo de tipos de asentamientos, pueden ser colonias, departamentos, barrios, etc.
 */
@Entity
@Table(name = "catalogo_de_tipos_de_asentamientos")
@Data
@NoArgsConstructor
public class KindOfSuburbs {

    /*
     * Id con el que se identifica el tipo de asentamiento en el catalogo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    /*
     * Clave del tipo de asentamiento
     */
    @Column(name = "clave_de_tipo_de_asentamiento")
    private String kindOfSuburbCode;

    /*
     * Tipo de asentamiento, que puede ser colonia, barrios, condominio, etc.
     */
    @Column(name = "tipo_de_asentamiento")
    private String kindOfSuburb;
}
