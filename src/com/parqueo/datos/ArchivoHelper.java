package com.parqueo.datos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ArchivoHelper {
    private static final Path DATA_DIR = Paths.get("data");

    static {
        asegurarDirectorio();
    }

    private ArchivoHelper() {
    }

    private static void asegurarDirectorio() {
        try {
            if (Files.notExists(DATA_DIR)) {
                Files.createDirectories(DATA_DIR);
            }
        } catch (IOException ex) {
            throw new RuntimeException("No fue posible preparar el directorio de datos", ex);
        }
    }

    private static Path resolver(String archivo) {
        asegurarDirectorio();
        return DATA_DIR.resolve(archivo);
    }

    public static List<String> leerLineas(String archivo) {
        Path ruta = resolver(archivo);
        try {
            if (Files.notExists(ruta)) {
                Files.createFile(ruta);
                return new ArrayList<String>();
            }
            return Files.readAllLines(ruta, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException("No fue posible leer el archivo " + archivo, ex);
        }
    }

    public static void escribirLineas(String archivo, List<String> lineas) {
        Path ruta = resolver(archivo);
        try {
            Files.write(ruta, lineas, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("No fue posible escribir el archivo " + archivo, ex);
        }
    }

    public static void agregarLinea(String archivo, String linea) {
        Path ruta = resolver(archivo);
        try {
            Files.write(ruta, (linea + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            throw new RuntimeException("No fue posible agregar la línea en " + archivo, ex);
        }
    }
}
