package com.untec.biblioteca.controller;

import com.untec.biblioteca.dao.LibroDAO;
import com.untec.biblioteca.model.Libro;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Controlador: gestión del catálogo de libros (listar, agregar, eliminar).
 * URL: /libros
 */
@WebServlet("/libros")
public class LibroServlet extends HttpServlet {

    private transient LibroDAO libroDAO;

    @Override
    public void init() {
        libroDAO = new LibroDAO();
    }

    /**
     * GET /libros          → lista todos los libros
     * GET /libros?accion=nuevo → muestra formulario para agregar libro
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Verificar sesión activa
        if (!estaAutenticado(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String accion = req.getParameter("accion");

        if ("nuevo".equals(accion)) {
            req.getRequestDispatcher("/WEB-INF/views/libro-form.jsp").forward(req, resp);
            return;
        }

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            libroDAO.eliminar(id);
            resp.sendRedirect(req.getContextPath() + "/libros?mensaje=" + URLEncoder.encode("Libro eliminado correctamente.", StandardCharsets.UTF_8));
            return;
        }

        // Listar todos los libros
        List<Libro> libros = libroDAO.listarTodos();
        req.setAttribute("libros", libros);
        req.setAttribute("mensaje", req.getParameter("mensaje"));
        req.getRequestDispatcher("/WEB-INF/views/libros.jsp").forward(req, resp);
    }

    /**
     * POST /libros → guarda un nuevo libro
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!estaAutenticado(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");
        Libro libro = new Libro();
        libro.setTitulo(req.getParameter("titulo"));
        libro.setAutor(req.getParameter("autor"));
        libro.setIsbn(req.getParameter("isbn"));
        libro.setAnio(Integer.parseInt(req.getParameter("anio")));
        libro.setDisponible(true);

        libroDAO.insertar(libro);
        resp.sendRedirect(req.getContextPath() + "/libros?mensaje=" + URLEncoder.encode("Libro agregado correctamente.", StandardCharsets.UTF_8));
    }

    // Verifica que exista una sesión con usuario autenticado
    private boolean estaAutenticado(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("usuarioLogueado") != null;
    }
}
