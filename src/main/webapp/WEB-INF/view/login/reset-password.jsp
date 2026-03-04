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
            <div class="input-major">
                <div id="create-password" class="input-container warning">
                    <p class="required">Atualize sua senha</p>
                    <div class="input-holder">
                        <button class="show-password" name="toggle" type="button">
                            <img id="open" src="${pageContext.request.contextPath}/img/svg/eye.svg" alt="Mostrar senha">
                            <img id="closed" style="display: none;" src="${pageContext.request.contextPath}/img/svg/eye-crossed.svg" alt="Ocultar senha">
                        </button>
                        <input id="password-first-validation" class="text-box validation" id="senha" name="senha" type="password" placeholder="Insira sua senha" pattern=".{8,}" required>
                    </div>
                </div>

                <div id="password-validation" class="input-container is-valid">
                    <p id="password-validation-p" class="required-ever">Confirmar senha</p>
                    <input id="password-second-validation" class="text-box validation" name="confirmarSenha" type="password" placeholder="Insira sua senha novamente" required>
                </div>

                <div id="key-word" class="input-container warning">
                    <p>Nova palavra-chave (opcional)</p>
                    <input class="text-box validation" id="palavra" name="palavra" type="text" placeholder="Insira sua nova palavra-chave"
                    pattern=".{3,}">
                </div>

                <input type="hidden" name="action" value="resetPassword">
                <input type="hidden" name="usuario" value="<%=request.getAttribute("usuario")%>">
                <input type="hidden" name="userId" value="${userId}">
            </div>

            <button id="button-submit" class="button" type="submit">Recuperar</button>
        </form>
    </div>
</body>

<script src="${pageContext.request.contextPath}/js/password.js"></script>
<script src="${pageContext.request.contextPath}/js/password-validation.js"></script>

</html>