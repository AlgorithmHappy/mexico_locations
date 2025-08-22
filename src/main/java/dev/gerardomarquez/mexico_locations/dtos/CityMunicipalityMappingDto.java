package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Informacion de la ciudad de acuerdo al municipio
 */
@Data
@NoArgsConstructor
public class CityMunicipalityMappingDto implements Serializable{

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

    /*
     * Codigo del estado al que pertenece el municipio
     */
    private String stateCode;

    /*
     * Nombre del estado
     */
    private String state;
}
