package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data transfer para devolver la ciudad segun el codigo municipio y el codigo estado
 */
@Data
@NoArgsConstructor
public class CityByStateAndMunicipalityCodeDto implements Serializable{

    /*
     * Id de la ciudad del cruce entre la ciudad y el municipio
     */
    private Integer id;

    /*
     * Nombre de la ciudad
     */
    private String name;

    /*
     * Codigo de la ciudad segun el municipio
     */
    private String cityCode;
}
