package dev.gerardomarquez.mexico_locations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidad que representa la tabla de cruces entre los estados y municipios de México.
 */
@Entity
@Table(name = "cruce_estados_municipios")
@Data
@NoArgsConstructor
public class MunicipalitiesStatesMappingTable {

    /*
     * Identificador único del cruce de estados y municipios, esta tabla es la que identifica uniquivocamente el municipio.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Identificador del estado.
     */
    @ManyToOne
    @JoinColumn(name = "id_estado")
    private States states;

    /*
     * Identificador del nombre del municipio.
     */
    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipalities municipalities;

    /*
     * Codigo de municipio que le puso el INEGI.
     */
    @Column(name = "clave_municipio")
    private String municipalityCode;
}
