package dev.gerardomarquez.mexico_locations.utils;

import java.util.Map;

public class Constants {

    public static String GLOBAL_EMPTY = "";
    public static Integer GLOBAL_ZERO = 0;
    public static Integer GLOBAL_BUFFER = 1024;

    public static String MSG_ERROR_DIRECTORY_NOT_VALID = "La ruta proporcionada '%s' no es un directorio.";
    public static String MSG_ERROR_DIRECTORY_FILE_DELETED = "Error al eliminar archivo/directorio '%s'. Mensaje de excepcion: %s.";
    public static String MSG_ERROR_DIRECTORY_NAVIGATE = "Error al recorrer directorio '%s'. Mensaje de excepcion: %s.";
    public static String MSG_ERROR_DOWNLOAD_FILE_NOT_FOUND = "El archivo descargado no se encontró en el directorio '%s' con el prefijo de %s. despues de %d ms.";
    public static String MSG_ERROR_DOWNLOAD_FILE = "Error durante la automatización de la descarga con Selenium: '%s'.";
    public static String MSG_ERROR_MAKE_DIRECTORY = "Fallo al crear el directorio: %s.";
    public static String MSG_ERROR_MAKE_PARENT_DIRECTORY = "Fallo al crear el directorio padre: %s.";
    public static String MSG_ERROR_DECOMPRESS_ZIP = "Error al descomprimir el archivo ZIP: %s.";
    public static String MSG_ERROR_READ_TEXT_FILE = "Error al leer el archivo: %s";

    public static String MSG_WARNING_INVALID_LINE = "Línea con formato incorrecto o incompleto, linea #: %d linea completa: %s.";

    public static String PREFS_CHROME_DRIVER = "prefs";
    public static String PREFS_CHROME_DRIVER_DOWNLOAD_DIRECTORY = "download.default_directory";
    public static String PREFS_CHROME_DRIVER_DOWNLOAD_PROMPT = "download.prompt_for_download";
    public static String PREFS_CHROME_DRIVER_DOWNLOAD_UPGRADE = "download.directory_upgrade";
    public static String PREFS_CHROME_DRIVER_SAFEBROWSING_ENABLED = "safebrowsing.enabled";
    public static String PREFS_CHROME_DRIVER_WEBDRIVER = "webdriver.chrome.driver";
    public static Integer PREFS_CHROME_DRIVER_WAIT_DOWNLOAD = 1000;

    public static final Map<String, Integer> TEXT_FILE_MEXICO_LOCATIONS_COLUMNS = Map.ofEntries(
        Map.entry("d_codigo", 0),
        Map.entry("d_asenta", 1),
        Map.entry("d_tipo_asenta", 2),
        Map.entry("D_mnpio", 3),
        Map.entry("d_estado", 4),
        Map.entry("d_ciudad", 5),
        Map.entry("d_CP", 6),
        Map.entry("c_estado", 7),
        Map.entry("c_oficina", 8),
        Map.entry("c_tipo_asenta", 9),     
        Map.entry("c_mnpio", 10),
        Map.entry("id_asenta_cpcons", 11),
        Map.entry("d_zona", 12),
        Map.entry("c_cve_ciudad", 13),
        Map.entry("c_CP", 14)
    );
    public static final Integer TEXT_FILE_SIZE_COLUMNS = 15;
}
