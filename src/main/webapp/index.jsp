<%@ page import="com.domdiogo.model.StatusColor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page errorPage="pages/error.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Fazer Login</title>
    <link rel="shortcut icon" href="img/branding/favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="css/sights/login.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/variables.css">
    <link rel="stylesheet" href="css/font.css">
</head>

<body id="login">
<div class="login-form">
    <div style="justify-content: center" class="logo">
        <img src="img/branding/icone.png">
        <img src="img/branding/black.png">
    </div>
    <h1>Bem vindo de volta!</h1>
    <%
        String errorDisplay = "block";
        String color = (String) request.getAttribute("statusColor");

        if (request.getAttribute("statusMessage") == null) {
            errorDisplay = "none";
        }
    %>
    <p class="error-message" style="display: <%=errorDisplay%>; color: <%=color%>"><%=request.getAttribute("statusMessage")%></p>
    <form action="${pageContext.request.contextPath}/login?action=login" method="post">
        <div class="input-major">
            <div class="email input-container">
                <p class="required">Usuário</p>
                <input class="validation text-box" id="email" name="usuario" type="text"
                       placeholder="Insira seu usuário"
                       pattern="^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}|[a-zA-Z]\.[a-zA-Z])$" required>
            </div>

            <div class="input-container">
                <p class="required">Senha</p>
                <div class="input-holder">
                    <button class="show-password" name="toggle" type="button">
                        <img id="open" src="img/svg/eye.svg" alt="Mostrar senha">
                        <img id="closed" style="display: none;"
                             src="img/svg/eye-crossed.svg" alt="Ocultar senha">
                    </button>
                    <input class="text-box" name="senha" type="password" placeholder="Insira sua senha" required>
                </div>
            </div>

            <div class="forgot-password">
                <a href="pages/login/forgot-password.jsp">Esqueceu a senha?</a>
            </div>

            <button class="button fat" type="submit">Continuar</button>
        </div>
    </form>
    <p id="sign-up-redirect">Ainda não possui uma conta?<a href="pages/login/register.jsp">Registrar-se</a></p>
</div>

<div class="admin">
    <a href="${pageContext.request.contextPath}/pages/login/admin.jsp"><img src="img/svg/lock.svg" alt="">Entrar como administrador</a>
</div>
</body>

<script src="js/password.js"></script>

</html>