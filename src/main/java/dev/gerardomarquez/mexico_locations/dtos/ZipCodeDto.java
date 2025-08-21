package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO que transfiere de entidad a response del codigo postal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZipCodeDto implements Serializable{

    /*
     * Id del codigo postal
     */
    private Integer id;

    /*
     * Codigo postal
     */
    private String zipCode;

    /*
     * Codigo de oficina del correo para cada codigo postal
     */
    private String officeCode;
}
