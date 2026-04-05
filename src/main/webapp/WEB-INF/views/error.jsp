<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error - Biblioteca UNTEC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-container">
    <div class="login-card" style="text-align:center;">
        <h2>Oops, algo salió mal</h2>
        <p>Ocurrió un error inesperado. Por favor, intente nuevamente.</p>
        <a href="${pageContext.request.contextPath}/libros" class="btn btn-primary">Volver al inicio</a>
    </div>
</div>
</body>
</html>
