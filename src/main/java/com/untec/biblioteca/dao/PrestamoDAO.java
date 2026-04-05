package com.untec.biblioteca.dao;

import com.untec.biblioteca.model.Prestamo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: operaciones CRUD sobre la tabla 'prestamos'.
 */
public class PrestamoDAO {

    private final Connection conn;

    public PrestamoDAO() {
        this.conn = ConexionDB.getInstancia().getConexion();
    }

    /** Lista todos los préstamos con nombre de usuario y título del libro */
    public List<Prestamo> listarTodos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.usuario_id, p.libro_id, p.fecha_prestamo, " +
                     "p.fecha_devolucion, p.devuelto, u.nombre AS nombre_usuario, l.titulo AS titulo_libro " +
                     "FROM prestamos p " +
                     "JOIN usuarios u ON p.usuario_id = u.id " +
                     "JOIN libros   l ON p.libro_id   = l.id " +
                     "ORDER BY p.fecha_prestamo DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar préstamos: " + e.getMessage(), e);
        }
        return lista;
    }

    /** Lista préstamos activos (no devueltos) de un usuario */
    public List<Prestamo> listarActivosPorUsuario(int usuarioId) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.usuario_id, p.libro_id, p.fecha_prestamo, " +
                     "p.fecha_devolucion, p.devuelto, u.nombre AS nombre_usuario, l.titulo AS titulo_libro " +
                     "FROM prestamos p " +
                     "JOIN usuarios u ON p.usuario_id = u.id " +
                     "JOIN libros   l ON p.libro_id   = l.id " +
                     "WHERE p.usuario_id = ? AND p.devuelto = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar préstamos del usuario: " + e.getMessage(), e);
        }
        return lista;
    }

    /** Registra un nuevo préstamo */
    public void insertar(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (usuario_id, libro_id, fecha_prestamo, devuelto) VALUES (?, ?, ?, FALSE)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prestamo.getUsuarioId());
            ps.setInt(2, prestamo.getLibroId());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar préstamo: " + e.getMessage(), e);
        }
    }

    /** Marca un préstamo como devuelto y registra la fecha de devolución */
    public void registrarDevolucion(int prestamoId) {
        String sql = "UPDATE prestamos SET devuelto = TRUE, fecha_devolucion = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, prestamoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar devolución: " + e.getMessage(), e);
        }
    }

    /** Busca un préstamo por ID */
    public Prestamo buscarPorId(int id) {
        String sql = "SELECT p.id, p.usuario_id, p.libro_id, p.fecha_prestamo, " +
                     "p.fecha_devolucion, p.devuelto, u.nombre AS nombre_usuario, l.titulo AS titulo_libro " +
                     "FROM prestamos p " +
                     "JOIN usuarios u ON p.usuario_id = u.id " +
                     "JOIN libros   l ON p.libro_id   = l.id " +
                     "WHERE p.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar préstamo: " + e.getMessage(), e);
        }
        return null;
    }

    // Mapea un ResultSet a un objeto Prestamo
    private Prestamo mapear(ResultSet rs) throws SQLException {
        Prestamo p = new Prestamo(
            rs.getInt("id"),
            rs.getInt("usuario_id"),
            rs.getInt("libro_id"),
            rs.getDate("fecha_prestamo").toLocalDate(),
            rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null,
            rs.getBoolean("devuelto")
        );
        p.setNombreUsuario(rs.getString("nombre_usuario"));
        p.setTituloLibro(rs.getString("titulo_libro"));
        return p;
    }
}
