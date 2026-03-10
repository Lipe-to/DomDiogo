<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Fazer Login</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/branding/favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<body id="admin">
<div class="login-form">
    <div style="justify-content: center" class="logo">
        <img src="${pageContext.request.contextPath}/img/branding/icone.png" draggable="false">
        <img src="${pageContext.request.contextPath}/img/branding/black.png" draggable="false">
    </div>
    <h1 style="whitespace: nowrap">Portal de acesso à diretoria</h1>
    <%
        String errorDisplay = "block";
        String color = (String) request.getAttribute("statusColor");

        if (request.getAttribute("statusMessage") == null) {
            errorDisplay = "none";
        }
    %>
    <p class="error-message" style="display: <%=errorDisplay%>; color: <%=color%>"><%=request.getAttribute("statusMessage")%></p>
    <form action="${pageContext.request.contextPath}/login?action=loginAdmin" method="post">
        <div class="input-major">
            <div class="email input-container">
                <p class="required">Usuário</p>
                <input class="validation text-box" id="email" name="usuario" type="text"
                       placeholder="Insira seu usuário" required>
            </div>

            <div class="input-container">
                <p class="required">Senha</p>
                <div class="input-holder">
                    <button class="show-password" name="toggle" type="button">
                        <img id="open" src="${pageContext.request.contextPath}/img/svg/eye.svg" alt="Mostrar senha">
                        <img id="closed" style="display: none;"
                             src="${pageContext.request.contextPath}/img/svg/eye-crossed.svg" alt="Ocultar senha">
                    </button>
                    <input class="text-box" name="senha" type="password" placeholder="Insira sua senha" required>
                </div>
            </div>

            <button class="button fat" type="submit">Continuar</button>
        </div>
    </form>
    <p id="sign-up-redirect">Não é um administrador?<a href="${pageContext.request.contextPath}/index.jsp">Fazer login</a></p>
</div>
</body>

<script src="${pageContext.request.contextPath}/js/password.js"></script>

</html>