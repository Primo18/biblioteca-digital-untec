package com.untec.biblioteca.dao;

import com.untec.biblioteca.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: operaciones CRUD sobre la tabla 'libros'.
 */
public class LibroDAO {

    private final Connection conn;

    public LibroDAO() {
        this.conn = ConexionDB.getInstancia().getConexion();
    }

    /** Retorna todos los libros */
    public List<Libro> listarTodos() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, isbn, anio, disponible FROM libros ORDER BY titulo";
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar libros: " + e.getMessage(), e);
        }
        return lista;
    }

    /** Retorna solo los libros disponibles */
    public List<Libro> listarDisponibles() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, isbn, anio, disponible FROM libros WHERE disponible = TRUE ORDER BY titulo";
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar libros disponibles: " + e.getMessage(), e);
        }
        return lista;
    }

    /** Busca un libro por su ID */
    public Libro buscarPorId(int id) {
        String sql = "SELECT id, titulo, autor, isbn, anio, disponible FROM libros WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar libro: " + e.getMessage(), e);
        }
        return null;
    }

    /** Inserta un nuevo libro */
    public void insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, anio, disponible) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setInt(4,    libro.getAnio());
            ps.setBoolean(5, libro.isDisponible());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar libro: " + e.getMessage(), e);
        }
    }

    /** Actualiza los datos de un libro */
    public void actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, isbn=?, anio=?, disponible=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setInt(4,    libro.getAnio());
            ps.setBoolean(5, libro.isDisponible());
            ps.setInt(6,    libro.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar libro: " + e.getMessage(), e);
        }
    }

    /** Elimina un libro por ID */
    public void eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar libro: " + e.getMessage(), e);
        }
    }

    /** Actualiza la disponibilidad de un libro */
    public void actualizarDisponibilidad(int libroId, boolean disponible) {
        String sql = "UPDATE libros SET disponible = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, disponible);
            ps.setInt(2, libroId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar disponibilidad: " + e.getMessage(), e);
        }
    }

    // Mapea un ResultSet a un objeto Libro
    private Libro mapear(ResultSet rs) throws SQLException {
        return new Libro(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("autor"),
            rs.getString("isbn"),
            rs.getInt("anio"),
            rs.getBoolean("disponible")
        );
    }
}
