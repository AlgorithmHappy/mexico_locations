package dev.gerardomarquez.mexico_locations.steps;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import dev.gerardomarquez.mexico_locations.utils.Constants;
import lombok.extern.log4j.Log4j2;

/*
 * En esta clase se define el segundo paso en el que se descargara el archivo de localidades de México
 */
@Log4j2
public class ItemDownloadZipStep implements Tasklet {

    // Ruta al ChromeDriver
    @Value("${webdriver.chrome.driver.path}")
    private String chromeDriverPath;

    // Directorio donde se guardarán los archivos descargados
    @Value("${download.directory}")
    private String downloadDirectory;

    // URL del sitio web desde donde se descargará el archivo
    @Value("${browser.url}")
    private String url;

    // Selector del elemento input que inicia la descarga
    @Value("${browser.input.id}")
    private String inputSelector;

    // Prefijo del nombre del archivo esperado para la descarga
    @Value("${browser.download.file.name}")
    private String expectedFileNamePrefix;

    // Modo de ejecución de Chrome (headless o normal)
    @Value("${webdriver.chrome.mode}")
    private String chromeMode;

    // Deshabilita GPU para Chrome en modo headless
    @Value("${webdriver.chrome.disable-gpu}")
    private String chromeDisableGpu;

    // Argumento para evitar problemas de permisos en entornos Linux/Docker
    @Value("${webdriver.chrome.no-sandbox}")
    private String chromeNoSandbox;

    // Tamaño de la ventana de Chrome para evitar problemas de renderizado
    @Value("${webdriver.chrome.window-size}")
    private String chromeWindowSize;

    // Tiempo de espera para que el elemento esté presente antes de interactuar
    @Value("${webdriver.chrome.wait-timeout.element}")
    private Long chromeWaitTimeout;

    // Tiempo máximo de espera para que se complete la descarga del archivo
    @Value("${webdriver.chrome.wait-timeout.download}")
    private Long chromeWaitDownloadTimeout;
    
    // Archivo que se ignorará durante la descarga (normalmente un archivo temporal de Chrome)
    @Value("${webdriver.chrome.file.ignore}")
    private String chromeIgnoreFile;

    /*
     * {@inheritDoc}
     * Este paso se encarga de obtener el archivo ZIP desde una URL y guardarlo en el sistema.
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // Configura las opciones del driver de Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments(chromeMode); // Ejecuta Chrome en modo headless (sin interfaz gráfica)
        options.addArguments(chromeDisableGpu); // Recomendado para headless
        options.addArguments(chromeNoSandbox); // Necesario para entornos Linux/Docker para evitar problemas de permisos
        options.addArguments(chromeWindowSize); // Establece un tamaño de ventana para evitar problemas de renderizado

        // Configura el directorio de descarga para el navegador
        Map<String, Object> prefs = new HashMap<>();
        prefs.put(Constants.PREFS_CHROME_DRIVER_DOWNLOAD_DIRECTORY, downloadDirectory);
        prefs.put(Constants.PREFS_CHROME_DRIVER_DOWNLOAD_PROMPT, false); // No preguntar dónde guardar
        prefs.put(Constants.PREFS_CHROME_DRIVER_DOWNLOAD_UPGRADE, true);
        prefs.put(Constants.PREFS_CHROME_DRIVER_SAFEBROWSING_ENABLED, true); // Deshabilita advertencias de seguridad para descargas
        options.setExperimentalOption(Constants.PREFS_CHROME_DRIVER, prefs);

        // Establece la propiedad del sistema para el ChromeDriver
        System.setProperty(Constants.PREFS_CHROME_DRIVER_WEBDRIVER, chromeDriverPath);

        WebDriver driver = null;
        try {
            driver = new ChromeDriver(options);
            driver.get(url);

            // Esperar a que la página cargue y el elemento esté presente
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(chromeWaitTimeout) );

            // Encuentra el elemento input por id.
            WebElement inputElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(inputSelector) ) );
            
            inputElement.click();

            // --- Lógica para esperar la descarga del archivo ---
            Path downloadDirPath = Paths.get(downloadDirectory);
            File[] downloadedFiles = null;
            long startTime = System.currentTimeMillis();
            long timeout = chromeWaitDownloadTimeout;

            boolean fileFound = Boolean.FALSE;
            while (System.currentTimeMillis() - startTime < timeout) {
                downloadedFiles = downloadDirPath.toFile().listFiles((dir, name) ->
                        name.startsWith(expectedFileNamePrefix) && !name.endsWith(chromeIgnoreFile) // Ignorar archivos temporales de Chrome
                );
                if (downloadedFiles != null && downloadedFiles.length > Constants.GLOBAL_ZERO) {
                    fileFound = Boolean.TRUE;
                    break;
                }
                Thread.sleep(Constants.PREFS_CHROME_DRIVER_WAIT_DOWNLOAD); // Espera 1 segundo antes de volver a comprobar
            }

            if (!fileFound) {
                log.error(String.format(Constants.MSG_ERROR_DOWNLOAD_FILE_NOT_FOUND, downloadDirectory, expectedFileNamePrefix, chromeWaitDownloadTimeout) );
            }

        } catch (Exception e) {
            log.error(String.format(Constants.MSG_ERROR_DOWNLOAD_FILE, e.getMessage() ) );
            throw e;
        } finally {
            if (driver != null) {
                driver.quit(); // Cierra el navegador y limpia los procesos
            }
        }

        return RepeatStatus.FINISHED;
    }
}
