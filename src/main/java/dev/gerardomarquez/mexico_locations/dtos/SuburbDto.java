package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data transfer object para el asentamiento
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuburbDto implements Serializable {

    /*
     * Identificador del asentamiento (colonia, barrio etc.)
     */
    private Integer id;

    /*
     * Id del asentamiento que le puso el municipio
     */
    private String idSuburbMunicipality;

    /*
     * Nombre del asentamiento
     */
    private String name;

    /*
     * Codigo del tipo de asentamiento (colonia, barrito etc.)
     */
    private String kindOfSuburbCode;

    /*
     * Nombre del tipo de asentamiento (colonia, barrito etc.)
     */
    private String kindOfSuburb;

    /*
     * Id de la zona del catalogo de zonas
     */
    private Short idZone;

    /*
     * Tipo de zona
     */
    private String zone;
}
