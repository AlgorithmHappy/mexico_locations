package dev.gerardomarquez.mexico_locations.controllers;

import org.springframework.web.bind.annotation.RestController;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("prueba")
public class Prueba {
    
    // Ruta al ChromeDriver, puedes configurarla en application.properties
    @Value("${webdriver.chrome.driver.path}")
    private String chromeDriverPath;

    // Directorio donde se guardarán los archivos descargados
    @Value("${download.directory}")
    private String downloadDirectory;

    @Value("${browser.url}")
    private String url;

    @Value("${browser.input.id}")
    private String inputSelector;

    @Value("${browser.download.file.name}")
    private String expectedFileNamePrefix;

    @GetMapping("/prueba")
    public void prueba(){
        // Configura las opciones de Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Ejecuta Chrome en modo headless (sin interfaz gráfica)
        options.addArguments("--disable-gpu"); // Recomendado para headless en algunos entornos
        options.addArguments("--no-sandbox"); // Necesario para entornos Linux/Docker para evitar problemas de permisos
        options.addArguments("--window-size=1920,1080"); // Establece un tamaño de ventana para evitar problemas de renderizado

        // Configura el directorio de descarga para el navegador
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadDirectory);
        prefs.put("download.prompt_for_download", false); // No preguntar dónde guardar
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true); // Deshabilita advertencias de seguridad para descargas
        options.setExperimentalOption("prefs", prefs);

        // Establece la propiedad del sistema para el ChromeDriver
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        WebDriver driver = null;
        try {
            driver = new ChromeDriver(options);
            System.out.println("Navegando a la URL: " + url);
            driver.get(url);

            // Esperar a que la página cargue y el elemento esté presente
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Espera hasta 30 segundos

            // Encuentra el elemento input.
            // Puedes usar By.id(), By.name(), By.cssSelector(), By.xpath(), etc.
            // Por ejemplo, si el input tiene id="downloadInput":
            // WebElement inputElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(inputSelector)));
            // Si el input tiene name="downloadFile":
            // WebElement inputElement = wait.until(ExpectedConditions.elementToBeClickable(By.name(inputSelector)));
            // Si es un selector CSS:
            WebElement inputElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(inputSelector) ) );

            System.out.println("Elemento input encontrado. Clickeando...");
            inputElement.click();

            // --- Lógica para esperar la descarga del archivo ---
            // Esto es crucial. Necesitas una forma de saber cuándo el archivo se ha descargado.
            // Una forma común es esperar a que un archivo con un prefijo o extensión específica aparezca en el directorio de descargas.

            Path downloadDirPath = Paths.get(downloadDirectory);
            File[] downloadedFiles = null;
            long startTime = System.currentTimeMillis();
            long timeout = 60 * 1000; // Espera hasta 60 segundos por el archivo

            System.out.println("Esperando la descarga del archivo en: " + downloadDirPath.toAbsolutePath());
            boolean fileFound = false;
            while (System.currentTimeMillis() - startTime < timeout) {
                downloadedFiles = downloadDirPath.toFile().listFiles((dir, name) ->
                        name.startsWith(expectedFileNamePrefix) && !name.endsWith(".crdownload") // Ignorar archivos temporales de Chrome
                );
                if (downloadedFiles != null && downloadedFiles.length > 0) {
                    fileFound = true;
                    break;
                }
                Thread.sleep(1000); // Espera 1 segundo antes de volver a comprobar
            }

            if (fileFound) {
                System.out.println("Archivo descargado exitosamente: " + downloadedFiles[0].getName());
                // Aquí puedes añadir lógica para procesar el archivo, moverlo, renombrarlo, etc.
            } else {
                System.err.println("Error: El archivo no se descargó o no se encontró en el tiempo esperado.");
            }

        } catch (Exception e) {
            System.err.println("Error durante la automatización de la descarga con Selenium: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit(); // Cierra el navegador y limpia los procesos
                System.out.println("Navegador cerrado.");
            }
        }
    }
}
