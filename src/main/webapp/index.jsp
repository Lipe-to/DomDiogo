<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Fazer Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
        integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />

    <link rel="stylesheet" href="css/sights/login.css">

    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/variables.css">
    <link rel="stylesheet" href="css/font.css">
</head>

<body id="login" style="overflow: hidden;">
    <div class="login-form">
        <h1>Bem vindo de volta!</h1>
        <form action="" method="">
            <div class="input-major">
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
            </div>

            <div class="forgot-password">
                <a href="pages/login/forgot-password.jsp">Esqueceu a senha?</a>
            </div>

            <button class="button" type="submit">Continuar</button>
        </form>
        
        <p id="sign-up-redirect">Ainda não possui uma conta?<a href="pages/login/register.jsp">Registrar-se</a></p>
    </div>

    <div class="admin">
        <a href="WEB-INF/view/sights/teacher.jsp"><img src="img/svg/lock.svg" alt="">Entrar como administrador</a>
    </div>
</body>

<script src="js/password.js"></script>

</html>