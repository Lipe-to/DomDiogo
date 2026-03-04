<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Recuperar senha</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/branding/favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<body id="forgot-password">
    <div class="login-form">
        <div style="justify-content: center" class="logo">
            <img src="${pageContext.request.contextPath}/img/branding/icone.png">
            <img src="${pageContext.request.contextPath}/img/branding/black.png">
        </div>
        <h1>Recuperar senha</h1>
        <%
        String errorDisplay = "block";
        if (request.getAttribute("statusMessage") == null) {
            errorDisplay = "none";
        }
        %>
        <p class="error-message" style="display: <%=errorDisplay%>;"><%=request.getAttribute("statusMessage")%></p>
        <form action="${pageContext.request.contextPath}/login?action=validarPalavra" method="post">
            <div class="input-major">
                <div class="email input-container">
                    <p class="required">Usuário</p>
                    <input class="validation text-box" id="email" name="usuario" type="text" placeholder="Insira seu endereço de e-mail"
                           pattern="^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}|[a-zA-Z]\.[a-zA-Z])$" required>
                </div>

                <div class="input-container">
                    <p class="required">Validar identidade</p>
                    <input class="text-box" name="palavra" type="text" placeholder="Insira sua palavra-chave" required>
                </div>
                <input type="hidden" name="action" value="validarPalavra">
            </div>

            <button class="button" type="submit">Recuperar</button>
        </form>
        <p id="sign-up-redirect">Lembrou sua senha?<a href="${pageContext.request.contextPath}/index.jsp">Fazer login</a></p>
    
    </div>
</body>

</html>