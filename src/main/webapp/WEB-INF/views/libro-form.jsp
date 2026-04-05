<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar Libro - Biblioteca UNTEC</title>
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
    <h2>Agregar Nuevo Libro</h2>

    <form action="${pageContext.request.contextPath}/libros" method="post" class="form-card">
        <div class="form-group">
            <label for="titulo">Título</label>
            <input type="text" id="titulo" name="titulo" required placeholder="Título del libro">
        </div>
        <div class="form-group">
            <label for="autor">Autor</label>
            <input type="text" id="autor" name="autor" required placeholder="Nombre del autor">
        </div>
        <div class="form-group">
            <label for="isbn">ISBN</label>
            <input type="text" id="isbn" name="isbn" required placeholder="Ej: 9780132350884">
        </div>
        <div class="form-group">
            <label for="anio">Año de publicación</label>
            <input type="number" id="anio" name="anio" required min="1900" max="2099"
                   placeholder="Ej: 2024">
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Guardar libro</button>
            <a href="${pageContext.request.contextPath}/libros" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</main>

</body>
</html>
