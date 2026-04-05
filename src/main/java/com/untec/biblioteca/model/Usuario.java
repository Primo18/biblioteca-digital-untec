package com.untec.biblioteca.model;

/**
 * Modelo: representa un usuario del sistema.
 */
public class Usuario {

    private int    id;
    private String nombre;
    private String email;
    private String password;
    private String rol; // "ADMIN" o "ESTUDIANTE"

    public Usuario() {}

    public Usuario(int id, String nombre, String email, String password, String rol) {
        this.id       = id;
        this.nombre   = nombre;
        this.email    = email;
        this.password = password;
        this.rol      = rol;
    }

    // Getters y Setters
    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    public String getNombre()           { return nombre; }
    public void setNombre(String n)     { this.nombre = n; }

    public String getEmail()            { return email; }
    public void setEmail(String e)      { this.email = e; }

    public String getPassword()         { return password; }
    public void setPassword(String p)   { this.password = p; }

    public String getRol()              { return rol; }
    public void setRol(String r)        { this.rol = r; }

    public boolean esAdmin()            { return "ADMIN".equals(rol); }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', rol='" + rol + "'}";
    }
}
