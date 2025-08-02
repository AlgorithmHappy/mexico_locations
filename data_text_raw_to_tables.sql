CREATE or replace PROCEDURE data_text_raw_to_tables()
LANGUAGE plpgsql
AS $$
BEGIN
/*
 * Procedimiento almacenado que transforma la tabla de datos en bruto
 * en datos normalizados distribullendolos en las diferentes tablas del
 * esquema para mejor gestion de datos. 
 */
	-- Inserta todas las zonas que vienen en los datos en crudo que no existan
	INSERT INTO catalogo_de_zonas (tipo_de_zona)
		SELECT DISTINCT d_zona FROM data_text_raw dtw where not exists(
			select tipo_de_zona from catalogo_de_zonas cdz where dtw.d_zona = cdz.tipo_de_zona
		);

	-- Inserta todos los estados que vienen en los datos en crudo que no existan
	INSERT into estados (clave_de_estado, estado)
		select distinct c_estado, d_estado from data_text_raw dtr where not exists(
			select clave_de_estado, estado from estados e where dtr.c_estado = e.clave_de_estado and dtr.d_estado = e.estado
		);
	
	-- Inserta todos los asentamientos que vienen en los datos en crudo que no existan
	INSERT into catalogo_de_tipos_de_asentamientos (clave_de_tipo_de_asentamiento, tipo_de_asentamiento)
	select distinct dtr.c_tipo_asenta, dtr.d_tipo_asenta  from data_text_raw dtr where not exists(
		select cdtda.clave_de_tipo_de_asentamiento, cdtda.tipo_de_asentamiento from catalogo_de_tipos_de_asentamientos cdtda
		where cdtda.clave_de_tipo_de_asentamiento = dtr.c_tipo_asenta and cdtda.tipo_de_asentamiento = dtr.d_tipo_asenta
	);

	-- Inserta todos los municipios que vienen en los datos en crudo que no existan
	INSERT into municipios (municipio)
	select distinct dtr.d_mnpio from data_text_raw dtr where not exists(
		select m.municipio from municipios m where m.municipio = dtr.d_mnpio
	);

	-- Inserta todas las ciudades que vienen en los datos en crudo que no existan
	INSERT into ciudades (ciudad)
	select distinct dtr.d_ciudad from data_text_raw dtr where not exists(
		select c.ciudad from ciudades c where c.ciudad = dtr.d_ciudad
	);

	-- Inserta todos los asentamientos que vienen en los datos en crudo que no existan
	INSERT into asentamientos (asentamiento)
	select distinct dtr.d_asenta from data_text_raw dtr where not exists(
		select a.asentamiento from asentamientos a where a.asentamiento = dtr.d_asenta
	);

	-- Inserta todas las claves de oficina que vienen en los datos en crudoque no existan
	INSERT into claves_oficina (clave_oficina, d_cp)
	select distinct dtr.c_oficina , dtr.d_cp from data_text_raw dtr where not exists(
		select co.clave_oficina, co.clave_oficina from claves_oficina co where co.clave_oficina = dtr.c_oficina and co.d_cp = dtr.d_cp
	);

	-- Inserta todos los codigos postales con el id oficina relacionado para cada codigo postal sin repeticiones
	INSERT into codigos_postales (codigo_postal, id_oficina)
	select distinct dtr.d_codigo, co.id from data_text_raw dtr, claves_oficina co
	where dtr.c_oficina = co.clave_oficina and dtr.d_cp = co.d_cp and not exists(
		select cp.codigo_postal, cp.id_oficina from codigos_postales cp
		where cp.codigo_postal = dtr.d_codigo and cp.id_oficina = co.id
	);

	-- Inserta todos los cruces de estados y municipios con su respectiva clave ya que los nombres de los municipios se pueden repetir en 2 o mas
	-- estados y cada estado lleva su propia clave de municipio. Si ya existe no se inserta
	insert into cruce_estados_municipios (id_estado, id_municipio, clave_municipio)
	select distinct e.id, m.id, dtr.c_mnpio from estados e, municipios m, data_text_raw dtr
	where e.clave_de_estado = dtr.c_estado and m.municipio = dtr.d_mnpio and e.estado = dtr.d_estado
	and not exists(
		select cem.id_municipio, cem.clave_municipio from cruce_estados_municipios cem
		where cem.id_municipio = m.id and cem.clave_municipio = dtr.c_mnpio
	);

	-- Inserta todos los cruces de ciudade de los municipios, es decir cada municipio le asigna una clave diferente a su ciudad, por ejemplo
	-- ciudad de mexico tiene una clave diferente en cada uno de sus municipios
	insert into cruce_ciudades_municipios (id_ciudad, id_cruce_estado_municipio, clave_ciudad)
	select distinct c.id, cem.id, dtr.c_cve_ciudad from ciudades c, cruce_estados_municipios cem, data_text_raw dtr
	where c.ciudad = dtr.d_ciudad and cem.clave_municipio = dtr.c_mnpio;


END;
$$;