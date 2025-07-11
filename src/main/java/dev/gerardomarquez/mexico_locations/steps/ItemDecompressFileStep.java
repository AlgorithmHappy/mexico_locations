package dev.gerardomarquez.mexico_locations.steps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import dev.gerardomarquez.mexico_locations.utils.Constants;
import lombok.extern.log4j.Log4j2;

/*
 * En esta clase se define el tercer paso en el que se desomprimira el archivo descargado de las
 * localidades de México
 */
@Log4j2
public class ItemDecompressFileStep implements Tasklet {

    /*
     * Ruta al directorio donde se guardarán los archivos descomprimidos.
     */
    @Value("${download.directory}")
    private String directory;

    /*
     * Extensión del archivo descargado que se descomprimirá.
     */
    @Value("${browser.download.file.extension}")
    private String fileExtension;

    /*
     * Nombre del archivo descargado que se descomprimirá.
     */
    @Value("${browser.download.file.name}")
    private String fileName;

    /*
     * @{inheritDoc}
     * Este método se ejecuta para descomprimir el archivo descargado.
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File destinationDirectory = new File(directory);

        // Buffer para leer y escribir datos
        byte[] buffer = new byte[Constants.GLOBAL_BUFFER];

        try (FileInputStream fileInputStream = new FileInputStream(directory + File.separator + fileName + fileExtension);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream) ) {

            ZipEntry zipEntry = zipInputStream.getNextEntry(); // Obtiene la primera entrada

            while (zipEntry != null) {
                String entryName = zipEntry.getName();
                File newFile = new File(destinationDirectory, entryName);

                // Crea directorios para entradas de carpeta si es necesario
                if (zipEntry.isDirectory() ) {
                    if (!newFile.isDirectory() && !newFile.mkdirs() ) {
                        log.error(String.format(Constants.MSG_ERROR_MAKE_DIRECTORY, newFile) );
                        throw new IOException(String.format(Constants.MSG_ERROR_MAKE_DIRECTORY, newFile) );
                    }
                } else {
                    // Asegura que los directorios padre existan para el archivo
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs() ) {
                        throw new IOException(String.format(Constants.MSG_ERROR_MAKE_PARENT_DIRECTORY, parent) );
                    }

                    // Escribe el contenido del archivo
                    try (FileOutputStream fileOutputStream = new FileOutputStream(newFile) ) {
                        int length;
                        while ( (length = zipInputStream.read(buffer) ) > Constants.GLOBAL_ZERO) {
                            fileOutputStream.write(buffer, Constants.GLOBAL_ZERO, length);
                        }
                    }
                }
                zipEntry = zipInputStream.getNextEntry(); // Pasa a la siguiente entrada
            }
            zipInputStream.closeEntry(); // Cierra la entrada actual
        } catch (IOException e) {
            System.err.println(String.format(Constants.MSG_ERROR_DECOMPRESS_ZIP, e.getMessage() ) );
            throw e; // Relanza la excepción para que el quien llama pueda manejarla
        }
        return RepeatStatus.FINISHED;
    }

}
