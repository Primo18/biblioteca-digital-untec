<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Préstamos - Biblioteca UNTEC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="nav-brand">Biblioteca Digital UNTEC</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/libros">Libros</a>
        <a href="${pageContext.request.contextPath}/prestamos" class="active">Préstamos</a>
        <c:if test="${not usuarioLogueado.esAdmin()}">
            <a href="${pageContext.request.contextPath}/prestamos?accion=nuevo" class="btn btn-sm">+ Nuevo préstamo</a>
        </c:if>
        <span class="nav-user">Hola, <c:out value="${usuarioLogueado.nombre}"/></span>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm btn-danger">Salir</a>
    </div>
</nav>

<main class="container">
    <h2>
        <c:choose>
            <c:when test="${usuarioLogueado.esAdmin()}">Todos los Préstamos</c:when>
            <c:otherwise>Mis Préstamos Activos</c:otherwise>
        </c:choose>
    </h2>

    <c:if test="${not empty mensaje}">
        <div class="alert alert-success">
            <c:out value="${mensaje}"/>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty prestamos}">
            <p class="empty-msg">No hay préstamos registrados.</p>
        </c:when>
        <c:otherwise>
            <table class="tabla">
                <thead>
                    <tr>
                        <th>#</th>
                        <c:if test="${usuarioLogueado.esAdmin()}">
                            <th>Usuario</th>
                        </c:if>
                        <th>Libro</th>
                        <th>Fecha préstamo</th>
                        <th>Fecha devolución</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${prestamos}" varStatus="idx">
                        <tr>
                            <td><c:out value="${idx.count}"/></td>
                            <c:if test="${usuarioLogueado.esAdmin()}">
                                <td><c:out value="${p.nombreUsuario}"/></td>
                            </c:if>
                            <td><c:out value="${p.tituloLibro}"/></td>
                            <td><c:out value="${p.fechaPrestamo}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty p.fechaDevolucion}">
                                        <c:out value="${p.fechaDevolucion}"/>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${p.devuelto}">
                                        <span class="badge badge-ok">Devuelto</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-warn">Activo</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${not p.devuelto}">
                                    <a href="${pageContext.request.contextPath}/prestamos?accion=devolver&id=${p.id}"
                                       class="btn btn-sm btn-primary"
                                       onclick="return confirm('¿Registrar devolución?')">Devolver</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>

</body>
</html>
