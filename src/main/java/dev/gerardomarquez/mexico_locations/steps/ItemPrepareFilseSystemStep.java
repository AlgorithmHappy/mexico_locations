package dev.gerardomarquez.mexico_locations.steps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import dev.gerardomarquez.mexico_locations.utils.Constants;
import lombok.extern.log4j.Log4j2;

/*
 * En esta clase se define el primer paso que consiste en dejar limpia las carpetas donde se van a descargar
 * el archivo .zip y donde se va a descomprimir dicho archivo
 */
@Log4j2
public class ItemPrepareFilseSystemStep implements Tasklet{

    /*
     * Ruta del directorio donde se eliminarán los archivos descargados.
     */
    @Value("${download.directory}")
    private String directoryPath;

    /*
     * {@inheritDoc}
     * Elimina todos los archivos y directorios del path especificado en el properties.
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Path directory = Paths.get(directoryPath);

        // 1. Verificar si la ruta existe y es un directorio
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        if (!Files.isDirectory(directory)) {
            log.error(String.format(Constants.MSG_ERROR_DIRECTORY_NOT_VALID, directoryPath) );
            throw new IllegalArgumentException( String.format(Constants.MSG_ERROR_DIRECTORY_NOT_VALID, directoryPath) );
        }

        // 2. Recorrer y borrar archivos/directorios recursivamente
        // Usando Files.walk
        try {
            // Files.walk crea un Stream de Paths. Recorre en orden inverso para borrar archivos antes que directorios.
            Files.walk(directory)
                .sorted(Comparator.reverseOrder() ) // Importante: para borrar de más profundo a menos
                .filter(p -> !p.equals(directory) ) // No borrar el directorio raíz que queremos limpiar
                .forEach(
                    p -> {
                        try {
                            Files.delete(p); // Borrar el archivo o directorio (que ya debe estar vacío)
                        } catch (IOException e) {
                            log.error(String.format(Constants.MSG_ERROR_DIRECTORY_FILE_DELETED, p.toString(), e.getMessage() ) );
                            throw new RuntimeException(e); // Lanzar excepción para detener el proceso si falla
                        }
                    }
                );
        } catch (Exception e) {
            log.error(String.format(Constants.MSG_ERROR_DIRECTORY_NAVIGATE, directoryPath, e.getMessage() ) );
            throw e; // Relanzar para notificar un fallo en la navegación del directorio
        }

        return RepeatStatus.FINISHED;
    }
}
