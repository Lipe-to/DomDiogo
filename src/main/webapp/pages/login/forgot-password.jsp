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
        <h1>Recuperar senha</h1>
        <form action="" method="post">
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
            </div>

            <button class="button" type="submit">Recuperar</button>
        </form>
    </div>
</body>

</html>