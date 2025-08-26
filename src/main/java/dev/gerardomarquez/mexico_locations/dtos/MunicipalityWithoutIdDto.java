package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data transfer para devolver en AllInformationDto la informacion necesaria del municipio
 */
@Data
@NoArgsConstructor
public class MunicipalityWithoutIdDto implements Serializable{

    /*
     * Codigo del municipio segun el estado al que pertenece
     */
    private String code;

    /*
     * Nombre del municipio
     */
    private String name;
}
