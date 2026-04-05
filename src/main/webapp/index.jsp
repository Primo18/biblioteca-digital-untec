<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca Digital UNTEC - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-container">
    <div class="login-card">
        <div class="login-header">
            <h1>Biblioteca Digital</h1>
            <p>Universidad UNTEC</p>
        </div>

        <%-- Mostrar mensaje de error si las credenciales son incorrectas --%>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">Correo electrónico</label>
                <input type="email" id="email" name="email"
                       placeholder="usuario@untec.edu" required autofocus>
            </div>
            <div class="form-group">
                <label for="password">Contraseña</label>
                <input type="password" id="password" name="password"
                       placeholder="Ingrese su contraseña" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Ingresar</button>
        </form>

        <div class="login-hint">
            <small>
                <strong>Demo:</strong> admin@untec.edu / admin123
                &nbsp;|&nbsp; juan@untec.edu / juan123
            </small>
        </div>
    </div>
</div>
</body>
</html>
