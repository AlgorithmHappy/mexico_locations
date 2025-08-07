package dev.gerardomarquez.mexico_locations.steps;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import dev.gerardomarquez.mexico_locations.utils.Constants;

/*
 * En esta clase se define el sexto paso en el que se distribuye los datos de la tabla en crudo en todas las
 * demas tablas del esquema de base de datos 
 */
@Component
public class ItemDistributeStoreProcedureStep implements Tasklet {

    /*
     * Cuantos datos en crudo guardar en el historial
     */
    @Value("${text.raw.level.history}")
    private String levelHistory;

    /*
     * Para ejecutar el procedimiento almacenado
     */
    private final JdbcTemplate jdbcTemplate;

    /*
     * Constructor para injectar el jdbcTemplate
     */
    public ItemDistributeStoreProcedureStep(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * {@inheritDoc}
     * Ejecuta el procedimiento almacenado para distribuir los datos y normalizar la base de datos
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        jdbcTemplate.execute(String.format(Constants.PROCEDURE_DATA_TEXT_RAW_TO_TABLES, levelHistory) );
        return RepeatStatus.FINISHED;
    }

}
