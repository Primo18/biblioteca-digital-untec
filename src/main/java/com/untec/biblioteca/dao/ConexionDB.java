package com.untec.biblioteca.dao;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Singleton para gestionar la conexión a la base de datos H2.
 * Patrón Singleton: garantiza una sola instancia de conexión.
 */
public class ConexionDB {

    // H2 con archivo persistente en el directorio del usuario
    private static final String URL      = "jdbc:h2:~/biblioteca-untec";
    private static final String USUARIO  = "sa";
    private static final String PASSWORD = "";

    private static ConexionDB instancia;
    private Connection conexion;

    // Constructor privado: evita instanciación externa
    private ConexionDB() {
        try {
            Class.forName("org.h2.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            inicializarEsquema();
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna la única instancia de ConexionDB (Thread-safe con double-checked locking).
     */
    public static ConexionDB getInstancia() {
        if (instancia == null) {
            synchronized (ConexionDB.class) {
                if (instancia == null) {
                    instancia = new ConexionDB();
                }
            }
        }
        return instancia;
    }

    /**
     * Retorna la conexión activa. La recrea si fue cerrada.
     */
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener conexion: " + e.getMessage(), e);
        }
        return conexion;
    }

    /**
     * Lee y ejecuta el archivo schema.sql para crear tablas e insertar datos iniciales.
     */
    private void inicializarEsquema() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("schema.sql")) {
            if (is == null) return;
            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            try (Statement stmt = conexion.createStatement()) {
                // Ejecutar cada sentencia separada por ;
                for (String bloque : sql.split(";")) {
                    // Eliminar líneas de comentario (--) dentro de cada bloque
                    StringBuilder limpio = new StringBuilder();
                    for (String linea : bloque.split("\n")) {
                        if (!linea.trim().startsWith("--")) {
                            limpio.append(linea).append("\n");
                        }
                    }
                    String sentencia = limpio.toString().trim();
                    if (!sentencia.isEmpty()) {
                        try {
                            stmt.execute(sentencia);
                        } catch (Exception ex) {
                            // Ignorar errores de duplicados o tablas ya existentes
                            System.out.println("SQL omitido (ya existe): " + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al inicializar esquema: " + e.getMessage());
        }
    }
}
