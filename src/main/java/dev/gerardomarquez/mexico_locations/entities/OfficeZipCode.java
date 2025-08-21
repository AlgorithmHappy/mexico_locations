package dev.gerardomarquez.mexico_locations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidad que representa las oficinas de correos y sus códigos postales asociados en México.
 */
@Entity
@Table(name = "claves_oficina")
@Data
@NoArgsConstructor
public class OfficeZipCode {

    /*
     * Identificador único de cada oficina de correos en México.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Clave única de la oficina de correos.
     */
    @Column(name = "clave_oficina")
    private String officeCode;
    
    /*
     * Clave única de la oficina de correos, es igual al atributo officeCode.
     */
    @Column(name = "d_cp")
    private String d_cp;
}
