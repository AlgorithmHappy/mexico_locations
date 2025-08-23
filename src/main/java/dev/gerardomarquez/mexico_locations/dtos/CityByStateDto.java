package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Dto que se devolvera para la consulta de ciudades por estado
 */
@NoArgsConstructor
@Data
public class CityByStateDto implements Serializable{

    /*
     * Id de la ciudad del catalogo
     */
    private Integer id;

    /*
     * Nombre de la ciudad
     */
    private String name;

    private CityMunicipalityMappingDtoByState municipalityCityData;

}
