CREATE OR REPLACE PROCEDURE public.data_text_raw_to_tables(IN level_history integer, IN in_step_name character varying)
 LANGUAGE plpgsql
AS $procedure$
DECLARE
	max_id_step INT; -- Id maximo para el log de "step" de spring batch
	min_id_step INT; -- Id minimo para el historial de insercciones que se tendra que borrar
	count_history INT; -- Cantidad de bloques insertados del historial
	num_iterations INT;
	i INT;
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
		) order by d_zona;

	-- Inserta todos los estados que vienen en los datos en crudo que no existan
	INSERT into estados (clave_de_estado, estado)
		select distinct c_estado, d_estado from data_text_raw dtr where not exists(
			select clave_de_estado, estado from estados e where dtr.c_estado = e.clave_de_estado and dtr.d_estado = e.estado
		) order by c_estado, d_estado;
	
	-- Inserta todos los asentamientos que vienen en los datos en crudo que no existan
	INSERT into catalogo_de_tipos_de_asentamientos (clave_de_tipo_de_asentamiento, tipo_de_asentamiento)
	select distinct dtr.c_tipo_asenta, dtr.d_tipo_asenta  from data_text_raw dtr where not exists(
		select cdtda.clave_de_tipo_de_asentamiento, cdtda.tipo_de_asentamiento from catalogo_de_tipos_de_asentamientos cdtda
		where cdtda.clave_de_tipo_de_asentamiento = dtr.c_tipo_asenta and cdtda.tipo_de_asentamiento = dtr.d_tipo_asenta
	) order by dtr.c_tipo_asenta, dtr.d_tipo_asenta;

	-- Inserta todos los municipios que vienen en los datos en crudo que no existan
	INSERT into municipios (municipio)
	select distinct dtr.d_mnpio from data_text_raw dtr where not exists(
		select m.municipio from municipios m where m.municipio = dtr.d_mnpio
	) order by dtr.d_mnpio;

	-- Inserta todas las ciudades que vienen en los datos en crudo que no existan
	INSERT into ciudades (ciudad)
	select distinct dtr.d_ciudad from data_text_raw dtr where not exists(
		select c.ciudad from ciudades c where c.ciudad = dtr.d_ciudad
	) order by dtr.d_ciudad;

	-- Inserta todos los asentamientos que vienen en los datos en crudo que no existan
	INSERT into asentamientos (asentamiento)
	select distinct dtr.d_asenta from data_text_raw dtr where not exists(
		select a.asentamiento from asentamientos a where a.asentamiento = dtr.d_asenta
	) order by dtr.d_asenta;

	-- Inserta todas las claves de oficina que vienen en los datos en crudoque no existan
	INSERT into claves_oficina (clave_oficina, d_cp)
	select distinct dtr.c_oficina , dtr.d_cp from data_text_raw dtr where not exists(
		select co.clave_oficina, co.clave_oficina from claves_oficina co where co.clave_oficina = dtr.c_oficina and co.d_cp = dtr.d_cp
	) order by dtr.c_oficina, dtr.d_cp;

	-- Inserta todos los codigos postales con el id oficina relacionado para cada codigo postal sin repeticiones
	INSERT into codigos_postales (codigo_postal, id_oficina)
	select distinct dtr.d_codigo, co.id from data_text_raw dtr, claves_oficina co
	where dtr.c_oficina = co.clave_oficina and dtr.d_cp = co.d_cp and not exists(
		select cp.codigo_postal, cp.id_oficina from codigos_postales cp
		where cp.codigo_postal = dtr.d_codigo and cp.id_oficina = co.id
	) order by dtr.d_codigo, co.id;

	-- Inserta todos los cruces de estados y municipios con su respectiva clave ya que los nombres de los municipios se pueden repetir en 2 o mas
	-- estados y cada estado lleva su propia clave de municipio. Si ya existe no se inserta
	insert into cruce_estados_municipios (id_estado, id_municipio, clave_municipio)
	select distinct e.id, m.id, dtr.c_mnpio from estados e, municipios m, data_text_raw dtr
	where e.clave_de_estado = dtr.c_estado and m.municipio = dtr.d_mnpio and e.estado = dtr.d_estado
	and not exists(
		select cem.id_municipio, cem.clave_municipio from cruce_estados_municipios cem
		where cem.id_municipio = m.id and cem.clave_municipio = dtr.c_mnpio
	) order by e.id, m.id, dtr.c_mnpio;

	-- Inserta todos los cruces de ciudade de los municipios, es decir cada municipio le asigna una clave diferente a su ciudad, por ejemplo
	-- ciudad de mexico tiene una clave diferente en cada uno de sus municipios
	insert into cruce_ciudades_municipios (id_cruce_estado_municipio, id_ciudad, clave_ciudad)
	select distinct cem.id, c.id, dtr.c_cve_ciudad from data_text_raw dtr, cruce_estados_municipios cem, estados e, ciudades c where cem.clave_municipio = dtr.c_mnpio
	and e.id = cem.id_estado and e.clave_de_estado = dtr.c_estado and c.ciudad = dtr.d_ciudad and dtr.c_cve_ciudad is not null and not exists(
		select ccm.id_cruce_estado_municipio, ccm.id_ciudad , ccm.clave_ciudad from cruce_ciudades_municipios ccm where ccm.id_cruce_estado_municipio = cem.id
		and ccm.id_ciudad = c.id and ccm.clave_ciudad = dtr.c_cve_ciudad
	) order by cem.id, c.id, dtr.c_cve_ciudad;

	-- Esta tabla une a casi todas las tablas es la intermediaria para todos, va por id asentamiento
	insert into cruce_asentamientos_municipios(id_asentamiento, id_cruce_estado_municipio, id_zona, id_codigo_postal, id_tipo_de_asentamiento, id_asenta_cpcons)
	select distinct a.id, cem.id, cdz.id, cp.id, cdtda.id, dtr.id_asenta_cpcons from data_text_raw dtr, asentamientos a, cruce_estados_municipios cem,
	catalogo_de_zonas cdz, codigos_postales cp, catalogo_de_tipos_de_asentamientos cdtda, estados e, claves_oficina co where dtr.d_asenta = a.asentamiento
	and dtr.c_estado = e.clave_de_estado and dtr.c_mnpio = cem.clave_municipio and cem.id_estado = e.id and dtr.d_zona = cdz.tipo_de_zona
	and dtr.d_codigo = cp.codigo_postal and dtr.c_oficina = co.clave_oficina and co.id = cp.id_oficina and dtr.d_cp = co.d_cp
	and dtr.d_tipo_asenta = cdtda.tipo_de_asentamiento and dtr.c_tipo_asenta = cdtda.clave_de_tipo_de_asentamiento and not exists (
		select * from cruce_asentamientos_municipios cam where cam.id_asentamiento = a.id and cam.id_cruce_estado_municipio = cem.id
		and cam.id_zona = cdz.id and cam.id_codigo_postal = cp.id and cam.id_tipo_de_asentamiento = cdtda.id and cam.id_asenta_cpcons = dtr.id_asenta_cpcons
	) order by a.id, cem.id, cdz.id, cp.id, cdtda.id, dtr.id_asenta_cpcons;

	IF level_history <= 0 Then
		--Delete from data_text_raw_history; -- Descomentar en casode que al poner 0 se requiera borrar todo el historial en el caso de que hubiera dicho historial
		return;
	end if;

	Select count(distinct
		step_execution_id
	)
	into
		count_history
	from
		data_text_raw_history;

	Select 
		max(bse.step_execution_id)
	into
		max_id_step
	from
		batch_step_execution bse
	where
		bse.step_name = in_step_name;

	Select 
		min(dtrh.step_execution_id)
	into
		min_id_step
	from
		data_text_raw_history dtrh;

	if count_history >= level_history Then
		Delete from data_text_raw_history where step_execution_id = min_id_step;

		/*
			----------- Lo siguiente es en el caso de que cambien un nivel muy alto de nivel de historia a un nivel muy bajo para eliminar todo lo antiguo -----------
		*/
		num_iterations := (count_history - level_history);
		if num_iterations < 0 Then
			num_iterations = 0;
		end if;
		FOR i in 1..num_iterations loop
			Select 
				min(dtrh.step_execution_id)
			into
				min_id_step
			from
				data_text_raw_history dtrh;
			Delete from data_text_raw_history where step_execution_id = min_id_step;
		end loop;
		/*
			----------- FIN -----------
		*/
	end if;

	INSERT INTO data_text_raw_history (step_execution_id, d_mnpio,c_cp,c_cve_ciudad,c_estado,c_mnpio,c_oficina,c_tipo_asenta,d_cp,d_asenta,d_ciudad,d_codigo,d_estado,d_tipo_asenta,d_zona,id_asenta_cpcons)
	 	select max_id_step, d_mnpio,c_cp,c_cve_ciudad,c_estado,c_mnpio,c_oficina,c_tipo_asenta,d_cp,d_asenta,d_ciudad,d_codigo,d_estado,d_tipo_asenta,d_zona,id_asenta_cpcons from data_text_raw order by id asc;

	Delete from data_text_raw;
		
END;
$procedure$
;