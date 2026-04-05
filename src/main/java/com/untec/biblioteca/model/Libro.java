package com.untec.biblioteca.model;

/**
 * Modelo: representa un libro de la biblioteca.
 */
public class Libro {

    private int     id;
    private String  titulo;
    private String  autor;
    private String  isbn;
    private int     anio;
    private boolean disponible;

    public Libro() {}

    public Libro(int id, String titulo, String autor, String isbn, int anio, boolean disponible) {
        this.id         = id;
        this.titulo     = titulo;
        this.autor      = autor;
        this.isbn       = isbn;
        this.anio       = anio;
        this.disponible = disponible;
    }

    // Getters y Setters
    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    public String getTitulo()           { return titulo; }
    public void setTitulo(String t)     { this.titulo = t; }

    public String getAutor()            { return autor; }
    public void setAutor(String a)      { this.autor = a; }

    public String getIsbn()             { return isbn; }
    public void setIsbn(String i)       { this.isbn = i; }

    public int getAnio()                { return anio; }
    public void setAnio(int a)          { this.anio = a; }

    public boolean isDisponible()       { return disponible; }
    public void setDisponible(boolean d){ this.disponible = d; }

    @Override
    public String toString() {
        return "Libro{id=" + id + ", titulo='" + titulo + "', autor='" + autor + "'}";
    }
}
