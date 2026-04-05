package com.untec.biblioteca.model;

import java.time.LocalDate;

/**
 * Modelo: representa un préstamo de libro a un usuario.
 */
public class Prestamo {

    private int       id;
    private int       usuarioId;
    private int       libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean   devuelto;

    // Datos enriquecidos para las vistas (JOIN)
    private String    nombreUsuario;
    private String    tituloLibro;

    public Prestamo() {}

    public Prestamo(int id, int usuarioId, int libroId,
                    LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto) {
        this.id              = id;
        this.usuarioId       = usuarioId;
        this.libroId         = libroId;
        this.fechaPrestamo   = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto        = devuelto;
    }

    // Getters y Setters
    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }

    public int getUsuarioId()                   { return usuarioId; }
    public void setUsuarioId(int u)             { this.usuarioId = u; }

    public int getLibroId()                     { return libroId; }
    public void setLibroId(int l)               { this.libroId = l; }

    public LocalDate getFechaPrestamo()         { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate f)   { this.fechaPrestamo = f; }

    public LocalDate getFechaDevolucion()       { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate f) { this.fechaDevolucion = f; }

    public boolean isDevuelto()                 { return devuelto; }
    public void setDevuelto(boolean d)          { this.devuelto = d; }

    public String getNombreUsuario()            { return nombreUsuario; }
    public void setNombreUsuario(String n)      { this.nombreUsuario = n; }

    public String getTituloLibro()              { return tituloLibro; }
    public void setTituloLibro(String t)        { this.tituloLibro = t; }

    @Override
    public String toString() {
        return "Prestamo{id=" + id + ", libro='" + tituloLibro + "', usuario='" + nombreUsuario + "'}";
    }
}
