package dev.gerardomarquez.mexico_locations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidad que cruza todas las entidades para definir al barrio, colonia o asentamiento
 */
@Entity
@Table(name = "cruce_asentamientos_municipios")
@Data
@NoArgsConstructor
public class SuburbMunicipalitiesStatesMappingTable {

    /*
     * Id que identifica a cada colonia, barrio, condominio de todo mexico, este es el id de la tabla que cruza todo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Id del nombre del asentamiento
     */
    @ManyToOne
    @JoinColumn(name = "id_asentamiento")
    private Suburbs suburb;

    /*
     * Cruce de municipios con estados, es decir tabla que identifica a cada municipio
     */
    @ManyToOne
    @JoinColumn(name = "id_cruce_estado_municipio")
    private MunicipalitiesStatesMappingTable municipalityStateMappingTable;

    /*
     * Codigo Id que cada municipio tiene para el asentamiento de dicho municipio
     */
    @Column(name = "id_asenta_cpcons")
    private String suburbCode;

    /*
     * Id del catalogo de tipo de asentamiento (condominio, colonia, barrio, etc.)
     */
    @ManyToOne
    @JoinColumn(name = "id_tipo_de_asentamiento")
    private KindOfSuburbs kindOfSuburb;

    /*
     * Id del catalogo de zonas (urbano, semiurbano, rural)
     */
    @ManyToOne
    @JoinColumn(name = "id_zona")
    private Zones zone;

    /*
     * Codigo postal asociado al asentamiento (barrio, colonia, etc.)
     */
    @ManyToOne
    @JoinColumn(name = "id_codigo_postal")
    private ZipCode zipCode;
}
