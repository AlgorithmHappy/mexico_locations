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
 * Entidad que representa el catalogo de los Estados de México.
 */
@Entity
@Table(name = "estados")
@NoArgsConstructor
@Data
public class States {

    /*
     * Identificador único de cada Estado de méxico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    /*
     * Codigo de estado que le puso el INEGI.
     */
    @Column(name = "clave_de_estado")
    private String stateCode;

    /*
     * Nombre del estado.
     */
    @Column(name = "estado")
    private String name;

    /*
     * Lista de cruces entre los estados y municipios de México.
     */
    @OneToMany(mappedBy = "states")
    private List<MunicipalitiesStatesMappingTable> municipalitiesStatesMappingTable;
}
