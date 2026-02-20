<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Registrar aluno</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/login.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<body id="sign-up">
    <div class="login-form">
        <h1>Registrar aluno</h1>
        <form action="" method="">
            <div class="input-major">
                <div class="input-container">
                    <p class="required">Nome completo</p>
                    <input class="text-box" name="" type="text" placeholder="Insira seu nome" required>
                </div>

                <div class="email input-container">
                    <p class="required">E-mail</p>
                    <input class="validation text-box" id="email" name="" type="email"
                        placeholder="Insira seu endereço de e-mail" pattern="^[^\s@]+@[^\s@]+\.[^\s@]{2,}$" required>
                </div>

                <div class="input-container">
                    <p class="required">Senha</p>
                    <div class="input-holder">
                        <button class="show-password" name="toggle">
                            <i id="open" class="fa-jelly-duo fa-regular fa-eye"></i>
                            <i id="closed" class="fa-jelly-duo fa-regular fa-eye-slash"></i>
                        </button>
                        <input class="text-box" name="password" type="password" placeholder="Insira sua senha" required>
                    </div>
                </div>

                <div class="input-container">
                    <p class="required">Confirmar senha</p>
                    <input class="text-box" name="" type="password" placeholder="Insira sua senha novamente" required>
                </div>

                <div class="input-container">
                    <p class="required">Comprovação de identidade <span class="tooltip msg-identity"><img class="info" src="../../img/svg/info.svg"></span></p>
                    <input class="text-box" name="" type="text" placeholder="Insira uma palavra-chave" required>
                </div>
            </div>

            <button class="button" type="submit">Registrar</button>
        </form>
    </div>
</body>

<script src="${pageContext.request.contextPath}/js/password.js"></script>

</html>