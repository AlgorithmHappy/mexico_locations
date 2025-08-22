package dev.gerardomarquez.mexico_locations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Tabla de cruce para cada municipio se le asigna un codigo diferente a la ciudad si esta abarca varios municipios
 * por ejemplo para la ciudad de mexico que tiene varios municipios esta tiene diferentes codigos por cada municipio
 */
@Entity
@Table(name = "cruce_ciudades_municipios")
@Data
@NoArgsConstructor
public class CitiesMunicipalitiesMappingTable {

    /*
     * Identificador de la ciudad por cada municipio
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Codigo de la ciudad por cada municipio
     */
    @Column(name = "clave_ciudad")
    private String cityCode;

    /*
     * Nombre de la ciudad
     */
    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    private Cities cities;

    /*
     * Municipio de la ciudad
     */
    @OneToOne
    private MunicipalitiesStatesMappingTable municipality;
}
