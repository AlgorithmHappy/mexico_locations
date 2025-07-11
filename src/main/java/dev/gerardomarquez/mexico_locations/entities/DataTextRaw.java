package dev.gerardomarquez.mexico_locations.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

/*
 * Entidad que representa el archivo de texto crudo de localidades de México.
 */
@Builder
@Entity
@Table(name = "data_text_raw")
public class DataTextRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la tabla
    private String d_codigo; // Código Postal asentamiento
    private String d_asenta; // Nombre asentamiento
    private String d_tipo_asenta; // Tipo de asentamiento (Catálogo SEPOMEX)
    private String D_mnpio; // Nombre Municipio (INEGI, Marzo 2013)
    private String d_estado; // Nombre Entidad (INEGI, Marzo 2013)
    private String d_ciudad; // Nombre Ciudad (Catálogo SEPOMEX)
    private String d_CP; // Código Postal de la Administración Postal que reparte al asentamiento
    private String c_estado; // Clave Entidad (INEGI, Marzo 2013)
    private String c_oficina; // Código Postal de la Administración Postal que reparte al asentamiento
    private String c_tipo_asenta; // Clave Tipo de asentamiento (Catálogo SEPOMEX)
    private String c_mnpio; // Clave Municipio (INEGI, Marzo 2013)
    private String id_asenta_cpcons; // Identificador único del asentamiento (nivel municipal)
    private String d_zona; // Zona en la que se ubica el asentamiento (Urbano/Rural)
    private String c_cve_ciudad; // Clave Ciudad (Catálogo SEPOMEX)
    private String c_CP; // Campo Vacio
}
