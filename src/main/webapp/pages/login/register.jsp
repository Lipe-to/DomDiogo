<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Registrar aluno</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/branding/favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<body id="sign-up">
    <div class="login-form">
        <div style="justify-content: center" class="logo">
            <img src="${pageContext.request.contextPath}/img/branding/icone.png">
            <img src="${pageContext.request.contextPath}/img/branding/black.png">
        </div>
        <h1>Registrar novo aluno</h1>
        <form action="${pageContext.request.contextPath}/aluno?action=create" method="post">
            <div class="input-major">
                <div class="email input-container warning">
                    <p class="required">Usuário</p>
                    <input class="validation text-box" id="email" name="usuario" type="email" placeholder="Insira seu usuário"
                           pattern="^[^\s@]+@[^\s@]+\.[^\s@]{2,}$" required>
                </div>

                <div id="create-password" class="input-container warning">
                    <p class="required">Senha</p>
                    <div class="input-holder">
                        <button class="show-password" name="toggle" type="button">
                            <img id="open" src="${pageContext.request.contextPath}/img/svg/eye.svg" alt="Mostrar senha">
                            <img id="closed" style="display: none;" src="${pageContext.request.contextPath}/img/svg/eye-crossed.svg" alt="Ocultar senha">
                        </button>
                        <input id="password-first-validation" class="text-box" name="senha" type="password" placeholder="Insira sua senha"
                            pattern=".{8,}" required>
                    </div>
                </div>

                <div id="password-validation" class="input-container is-valid">
                    <p id="password-validation-p" class="required-ever">Confirmar senha</p>
                    <input id="password-second-validation" class="text-box" type="password" placeholder="Insira sua senha novamente"
                        required>
                </div>

                <div id="key-word" class="input-container warning">
                    <p class="required">Comprovação de identidade
                        <span class="tooltip msg-identity">
                            <img class="info" src="${pageContext.request.contextPath}/img/svg/info.svg" alt="Informação">
                        </span>
                    </p>
                    <input class="text-box" name="palavra" type="text" placeholder="Insira uma palavra-chave"
                        pattern=".{3,}" required>
                </div>
            </div>

            <button id="button-submit" class="button" type="submit" disabled>Registrar</button>
        </form>
        <p id="sign-up-redirect">Já possui uma conta?<a href="${pageContext.request.contextPath}/index.jsp">Fazer login</a></p>

    </div>
</body>

<script src="${pageContext.request.contextPath}/js/password.js"></script>
<script src="${pageContext.request.contextPath}/js/password-validation.js"></script>

</html>