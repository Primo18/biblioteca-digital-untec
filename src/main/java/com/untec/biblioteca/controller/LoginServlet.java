package com.untec.biblioteca.controller;

import com.untec.biblioteca.dao.UsuarioDAO;
import com.untec.biblioteca.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Controlador: maneja el login y logout de usuarios.
 * URL: /login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private transient UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    /**
     * GET /login → muestra el formulario de login (index.jsp)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Si ya hay sesión activa, redirigir al catálogo
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            resp.sendRedirect(req.getContextPath() + "/libros");
            return;
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    /**
     * POST /login → procesa las credenciales del formulario
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        Usuario usuario = usuarioDAO.autenticar(email, password);

        if (usuario != null) {
            // Crear sesión y guardar el usuario autenticado
            HttpSession session = req.getSession(true);
            session.setAttribute("usuarioLogueado", usuario);
            session.setMaxInactiveInterval(30 * 60); // 30 minutos
            resp.sendRedirect(req.getContextPath() + "/libros");
        } else {
            // Credenciales incorrectas: volver al login con mensaje de error
            req.setAttribute("error", "Email o contraseña incorrectos.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
