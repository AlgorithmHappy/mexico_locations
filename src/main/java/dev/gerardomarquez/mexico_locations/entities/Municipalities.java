package dev.gerardomarquez.mexico_locations.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidad que representa el catalogo de los Municipios de todo México.
 */
@Entity
@Table(name = "municipios")
@Data
@NoArgsConstructor
public class Municipalities {

    /*
     * Identificador único de cada municipio de méxico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Nombre del municipio, este nombre puede estar repetido en 2 o mas estados.
     */
    @Column(name = "municipio")
    private String name;

    /*
     * Lista de cruces entre los estados y municipios de México.
     */
    @OneToMany(mappedBy = "municipalities")
    private List<MunicipalitiesStatesMappingTable> municipalitiesStatesMappingTable;
}
