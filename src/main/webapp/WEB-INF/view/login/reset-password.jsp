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
        <h1>Nova senha</h1>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="hidden" name="action" value="resetPassword">
            <input type="hidden" name="usuario" value="<%=request.getAttribute("usuario")%>">
            <input type="hidden" name="userId" value="${userId}">

            <div class="input-major">
                <div class="input-container">
                    <p class="required">Nova senha</p>
                    <div class="input-holder">
                        <button class="show-password" name="toggle" type="button">
                            <img id="open" src="${pageContext.request.contextPath}/img/svg/eye.svg" alt="Mostrar senha">
                            <img id="closed" style="display: none;" src="${pageContext.request.contextPath}/img/svg/eye-crossed.svg" alt="Ocultar senha">
                        </button>
                        <input class="text-box" id="senha" name="senha" type="password" placeholder="Insira sua senha" pattern=".{8,}" required>
                    </div>
                </div>

                <div class="input-container">
                    <p class="required">Confirmar senha</p>
                    <input class="text-box" id="confirmarSenha" name="confirmarSenha" type="password" placeholder="Insira sua senha novamente" required>
                </div>

                <div class="input-container">
                    <p>Nova palavra-chave (opcional)</p>
                    <input class="text-box" id="palavra" name="palavra" type="text" placeholder="Insira sua nova palavra-chave (opcional)">
                </div>
            </div>

            <button class="button" type="submit">Recuperar</button>
        </form>
    </div>
</body>

</html>