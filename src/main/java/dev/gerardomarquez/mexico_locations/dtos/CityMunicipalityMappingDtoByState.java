package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Dataos que tiene el municipio de la ciudad
 */
@NoArgsConstructor
@Data
public class CityMunicipalityMappingDtoByState implements Serializable {

    /*
    * Id uniquivoco de la ciudad por cada municipio
    */
    private Integer id;

    /*
     * Codigo de ciudad segun el municipio
     */
    private String cityCode;

    /*
     * Id del municipio de la entidad de cruce con estados
     */
    private Integer idMunicipality;

    /*
     * Codigo del municipio segun el estado
     */
    private String municipalityCode;

    /*
     * Nombre del municipio
     */
    private String municipality;

}
