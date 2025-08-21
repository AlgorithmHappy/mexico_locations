package dev.gerardomarquez.mexico_locations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * Entidad que representa los códigos postales de México.
 * Cada código postal está asociado a una oficina de correos específica.
 */
@Entity
@Table(name = "codigos_postales")
public class ZipCode {

    /*
     * Identificador único de cada código postal en México.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Código postal, es un número de 5 dígitos que identifica una zona geográfica específica en México.
     */
    @Column(name = "codigo_postal")
    private String zipCode;

    /*
     * Oficina de correos asociada a este código postal.
     */
    @ManyToOne
    @JoinColumn(name = "id_oficina")
    private OfficeZipCode officeZipCode;
}
