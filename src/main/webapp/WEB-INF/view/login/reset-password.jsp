<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Recuperar senha</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/login.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<body id="forgot-password">
    <div class="login-form">
        <h1>Recuperar senha</h1>
        <form action="${pageContext.request.contextPath}/login?action=validarPalavra" method="post">
            <div class="input-major">
                <div class="input-container">
                    <p class="required">Nova senha</p>
                    <div class="input-holder">
                        <button class="show-password" name="toggle">
                            <i id="open" class="fa-jelly-duo fa-regular fa-eye"></i>
                            <i id="closed" class="fa-jelly-duo fa-regular fa-eye-slash"></i>
                        </button>
                        <input class="text-box" name="senha" type="password" placeholder="Insira sua senha" required
                               pattern=".{8,}">
                    </div>
                </div>

                <div class="input-container">
                    <p class="required">Confirmar senha</p>
                    <input class="text-box" type="password" placeholder="Insira sua senha novamente" required>
                </div>
            </div>

            <button class="button" type="submit">Recuperar</button>
        </form>
    </div>
</body>

</html>