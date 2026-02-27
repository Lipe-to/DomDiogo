<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Recuperar senha</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/login.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<body id="forgot-password">
    <div class="login-form">
        <h1>Informe a nova senha</h1>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="hidden" name="action" value="resetPassword">
            <br>
            <input type="hidden" name="userId" value="${userId}">

            <div class="input-major">
                <div class="input-container">
                    <p class="required">Nova senha</p>
                    <div class="input-holder">
                        <button class="show-password" name="toggle">
                            <i id="open" class="fa-jelly-duo fa-regular fa-eye"></i>
                            <i id="closed" class="fa-jelly-duo fa-regular fa-eye-slash"></i>
                        </button>
                        <input class="text-box" id="senha" name="senha" type="password" placeholder="Insira sua senha" required
                               pattern=".{8,}">
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