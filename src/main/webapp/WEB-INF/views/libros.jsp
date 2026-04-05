<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Catálogo de Libros - Biblioteca UNTEC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%-- Barra de navegación --%>
<nav class="navbar">
    <div class="nav-brand">Biblioteca Digital UNTEC</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/libros" class="active">Libros</a>
        <a href="${pageContext.request.contextPath}/prestamos">Préstamos</a>
        <c:if test="${usuarioLogueado.esAdmin()}">
            <a href="${pageContext.request.contextPath}/libros?accion=nuevo" class="btn btn-sm">+ Agregar libro</a>
        </c:if>
        <span class="nav-user">Hola, <c:out value="${usuarioLogueado.nombre}"/></span>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm btn-danger">Salir</a>
    </div>
</nav>

<main class="container">
    <h2>Catálogo de Libros</h2>

    <%-- Mensaje de confirmación --%>
    <c:if test="${not empty mensaje}">
        <div class="alert alert-success">
            <c:out value="${mensaje}"/>
        </div>
    </c:if>

    <%-- Tabla de libros usando c:forEach --%>
    <c:choose>
        <c:when test="${empty libros}">
            <p class="empty-msg">No hay libros registrados en el catálogo.</p>
        </c:when>
        <c:otherwise>
            <table class="tabla">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>ISBN</th>
                        <th>Año</th>
                        <th>Estado</th>
                        <c:if test="${usuarioLogueado.esAdmin()}">
                            <th>Acciones</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="libro" items="${libros}" varStatus="idx">
                        <tr>
                            <td><c:out value="${idx.count}"/></td>
                            <td><c:out value="${libro.titulo}"/></td>
                            <td><c:out value="${libro.autor}"/></td>
                            <td><c:out value="${libro.isbn}"/></td>
                            <td><c:out value="${libro.anio}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${libro.disponible}">
                                        <span class="badge badge-ok">Disponible</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-no">Prestado</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:if test="${usuarioLogueado.esAdmin()}">
                                <td>
                                    <a href="${pageContext.request.contextPath}/libros?accion=eliminar&id=${libro.id}"
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('¿Eliminar este libro?')">Eliminar</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <%-- Solicitar préstamo (solo estudiantes) --%>
            <c:if test="${not usuarioLogueado.esAdmin()}">
                <div class="acciones">
                    <a href="${pageContext.request.contextPath}/prestamos?accion=nuevo"
                       class="btn btn-primary">Solicitar préstamo</a>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</main>

</body>
</html>
