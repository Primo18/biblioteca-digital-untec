package com.untec.biblioteca.dao;

import com.untec.biblioteca.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: operaciones CRUD sobre la tabla 'usuarios'.
 */
public class UsuarioDAO {

    private final Connection conn;

    public UsuarioDAO() {
        this.conn = ConexionDB.getInstancia().getConexion();
    }

    /** Retorna todos los usuarios */
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, email, password, rol FROM usuarios ORDER BY nombre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage(), e);
        }
        return lista;
    }

    /** Busca un usuario por email y contraseña (login) */
    public Usuario autenticar(String email, String password) {
        String sql = "SELECT id, nombre, email, password, rol FROM usuarios WHERE email = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al autenticar usuario: " + e.getMessage(), e);
        }
        return null; // credenciales incorrectas
    }

    /** Busca un usuario por ID */
    public Usuario buscarPorId(int id) {
        String sql = "SELECT id, nombre, email, password, rol FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario: " + e.getMessage(), e);
        }
        return null;
    }

    /** Inserta un nuevo usuario */
    public void insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, email, password, rol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    // Mapea un ResultSet a un objeto Usuario
    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("rol")
        );
    }
}
