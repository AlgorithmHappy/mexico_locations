package dev.gerardomarquez.mexico_locations.repositories.projections;

/*
 * Proyeccion de spring data para devolver toda la informacion y convertirlo a un dto
 */
public interface AllInformationProjection {

    /*
     * Id que identifica a cada colonia, barrio, condominio de todo mexico, este es el id de la tabla que cruza todo
     * @return Id del asentamiento.
     */
    public Integer getId();

    /*
     * Codigo del asentamiento segun el municipio
     * @return Codigo del asesntamiento segun el municipio
     */
    public String getSuburbCode();

    /*
     * Proyeccion para obtener la entidad del tipo de asentamiento
     * @return Devuelve la proyeccion para la entidad de tipo de asentamiento
     */
    public KindOfSuburbsProjection getKindOfSuburb();

    public interface KindOfSuburbsProjection {

        /*
         * Codigo de tipo de asentamiento que es
         * @return Devuelve el codigo de tipo de asentamiento que es
         */
        public String getKindOfSuburbCode();

        /*
         * Tipo de asentamiento al que pertenece
         * @return Devuelve el tipo de asentamiento al que pertenece
         */
        public String getKindOfSuburb();
    }

    /*
     * Proyeccion para el catalogo de asentamientos
     * @return Devuelve la proyeccion para el catalogo de asentamientos
     */
    public SuburbsProjection getSuburb();

    /*
     * Proyeccion del catalogo de asentamientos
     */
    public interface SuburbsProjection {
        
        /*
         * Nombre del asentamiento
         * @return Devuelve el nombre del asentamiento
         */
        public String getName();
        
    }

    /*
     * Devuelve el projection del MunicipalitiesStatesMappingTable, tabla de cruce de municipios y estados
     * @return Projection de la tabla de cruce de municipios y estados
     */
    public MunicipalitiesStatesMappingTableProjection getMunicipalityStateMappingTable();

    /*
     * Proyeccion de MunicipalitiesStatesMappingTableProjection
     */
    public interface MunicipalitiesStatesMappingTableProjection {

        /*
         * Codigo de municipio segun el estado.
         * @return Devuelve el codigo de municipio asignado por el estado
         */
        public String getMunicipalityCode();
        
        /*
         * Estado al que pertenece el asentamiento.
         * @return Devuelve el projection del estado al que pertenece el asentamiento
         */
        public StatesProjection getStates();

        public interface StatesProjection {

            /*
             * Clave del estado
             * @return Clave del estado del INEGI
             */
            public String getStateCode();

            /*
             * Nombre de estado del que pertenece el asentamiento.
             * @return Nombre del estado
             */
            public String getName();            
        }

        /*
         * Proyeccion que contiene la informacion del catalogo de municipios.
         * @return Devuelve la proyeccion de la tabla de catalogos de municipios.
         */
        public MunicipalityProjection getMunicipalities();

        public interface MunicipalityProjection {
        
            /*
             * Devuelve el nombre del municipio
             * @return Nombre del municipio.
             */
            public String getName();
        }
        
    }

    /*
     * Proyeccion de la entidad de la zona
     * @return Devuelve la proyeccion de la entidad de la zona
     */
    public ZonesProjection getZone();

    /*
     * Proyeccion para la entidad de la zona
     */
    public interface ZonesProjection {

        /*
         * Tipo de zona del asentamiento
         * @return Devuelve el tipo de zona del asentamiento
         */
        public String getZone();
    }


    /*
     * Proyeccion del codigo postal
     * @return Devuelve la proyeccion del codigo postal
     */
    public ZipoCodeProjection getZipCode();

    /*
     * Proyeccion de la entidad del codigo postal
     */
    public interface ZipoCodeProjection {

        /*
         * Proyeccion de la entidad del codigo postal
         * @return Devuelve la proyeccion de la entidad del codigo postal
         */
        public OfficeZipCodeProjection getOfficeZipCode();

        /*
         * Projection del Codigo postal
         */
        public interface OfficeZipCodeProjection {
        
            /*
             * Devuelve el codigo de oficina del codigo postal
             * @return Devuelve el codigo de oficina del codigo postal
             */
            public String getOfficeCode();
        }
    }
}
