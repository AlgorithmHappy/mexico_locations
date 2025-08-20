package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Clase Dto para transferir los datos de los municipios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalityDto implements Serializable{

    /*
     * Identificador de la entidad del cruce de municipios estados el MunicipalitiesStatesMappingTable.
     */
    private Integer id;

    /*
     * Codigo de municipio por cada estado que le puso el INEGI.
     */
    private String municipalityCode;

    /*
     * Nombre del municipio, este nombre puede estar repetido en 2 o más estados.
     */
    private String name;
}
