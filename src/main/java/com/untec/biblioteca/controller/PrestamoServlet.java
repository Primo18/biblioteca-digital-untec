package com.untec.biblioteca.controller;

import com.untec.biblioteca.dao.LibroDAO;
import com.untec.biblioteca.dao.PrestamoDAO;
import com.untec.biblioteca.model.Prestamo;
import com.untec.biblioteca.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador: gestión de préstamos y devoluciones.
 * URL: /prestamos
 */
@WebServlet("/prestamos")
public class PrestamoServlet extends HttpServlet {

    private transient PrestamoDAO prestamoDAO;
    private transient LibroDAO    libroDAO;

    @Override
    public void init() {
        prestamoDAO = new PrestamoDAO();
        libroDAO    = new LibroDAO();
    }

    /**
     * GET /prestamos          → lista préstamos (admin: todos, estudiante: los suyos)
     * GET /prestamos?accion=nuevo → formulario para solicitar préstamo
     * GET /prestamos?accion=devolver&id=X → registra devolución
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!estaAutenticado(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogueado");
        String accion   = req.getParameter("accion");

        if ("nuevo".equals(accion)) {
            // Pasar libros disponibles al formulario
            req.setAttribute("libros", libroDAO.listarDisponibles());
            req.getRequestDispatcher("/WEB-INF/views/prestamo-form.jsp").forward(req, resp);
            return;
        }

        if ("devolver".equals(accion)) {
            int prestamoId = Integer.parseInt(req.getParameter("id"));
            Prestamo p     = prestamoDAO.buscarPorId(prestamoId);
            if (p != null) {
                prestamoDAO.registrarDevolucion(prestamoId);
                libroDAO.actualizarDisponibilidad(p.getLibroId(), true);
            }
            resp.sendRedirect(req.getContextPath() + "/prestamos?mensaje=" + URLEncoder.encode("Devolucion registrada.", StandardCharsets.UTF_8));
            return;
        }

        // Listar préstamos según rol
        List<Prestamo> prestamos;
        if (usuario.esAdmin()) {
            prestamos = prestamoDAO.listarTodos();
        } else {
            prestamos = prestamoDAO.listarActivosPorUsuario(usuario.getId());
        }

        req.setAttribute("prestamos", prestamos);
        req.setAttribute("mensaje", req.getParameter("mensaje"));
        req.getRequestDispatcher("/WEB-INF/views/prestamos.jsp").forward(req, resp);
    }

    /**
     * POST /prestamos → registra un nuevo préstamo
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!estaAutenticado(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogueado");
        int libroId     = Integer.parseInt(req.getParameter("libroId"));

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuarioId(usuario.getId());
        prestamo.setLibroId(libroId);
        prestamo.setFechaPrestamo(LocalDate.now());

        prestamoDAO.insertar(prestamo);
        libroDAO.actualizarDisponibilidad(libroId, false);

        resp.sendRedirect(req.getContextPath() + "/prestamos?mensaje=" + URLEncoder.encode("Prestamo registrado correctamente.", StandardCharsets.UTF_8));
    }

    private boolean estaAutenticado(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("usuarioLogueado") != null;
    }
}
