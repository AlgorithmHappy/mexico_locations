package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data transfer de toda la informacion
 */
@Data
@NoArgsConstructor
public class AllInformationDto implements Serializable {

    /*
     * Id del asentamiento en la tabla de cruce de asentamientos municipios
     */
    private Integer id;

    /*
     * Codigo del asentamiento
     */
    private String code;

    /*
     * Nombre del asentamiento
     */
    private String name;

    /*
     * Tipo de zona del asentamiento
     */
    private String zone;

    /*
     * Tipo de asentamiento
     */
    private KindOfSuburbDto kindOfSuburb;

    /*
     * Informacion sobre el municipio al que pertenece
     */
    private MunicipalityWithoutIdDto municipalityInformation;

    /*
     * Informacion del estado al que pertenece
     */
    private StateWithoutIdDto stateInformation;

    /*
     * Informacion de la oficina a la que pertenece
     */
    private OfficeDto officeInformation;
}
