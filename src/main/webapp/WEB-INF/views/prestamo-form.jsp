<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Solicitar Préstamo - Biblioteca UNTEC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="nav-brand">Biblioteca Digital UNTEC</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/libros">Libros</a>
        <a href="${pageContext.request.contextPath}/prestamos">Préstamos</a>
        <span class="nav-user">Hola, <c:out value="${usuarioLogueado.nombre}"/></span>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm btn-danger">Salir</a>
    </div>
</nav>

<main class="container">
    <h2>Solicitar Préstamo</h2>

    <c:choose>
        <c:when test="${empty libros}">
            <div class="alert alert-error">No hay libros disponibles para préstamo en este momento.</div>
            <a href="${pageContext.request.contextPath}/libros" class="btn btn-secondary">Volver al catálogo</a>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/prestamos" method="post" class="form-card">
                <div class="form-group">
                    <label for="libroId">Seleccionar libro disponible</label>
                    <select id="libroId" name="libroId" required>
                        <option value="">-- Seleccione un libro --</option>
                        <c:forEach var="libro" items="${libros}">
                            <option value="${libro.id}">
                                <c:out value="${libro.titulo}"/> — <c:out value="${libro.autor}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Confirmar préstamo</button>
                    <a href="${pageContext.request.contextPath}/libros" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</main>

</body>
</html>
