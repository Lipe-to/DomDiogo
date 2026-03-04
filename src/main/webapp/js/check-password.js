document.addEventListener("DOMContentLoaded", function () {
    const senhaInput = document.querySelector('input[name="senha"]');
    const confirmSenhaInput = document.querySelectorAll('input[type="password"]')[1];

    function validarSenha() {
        const senha = senhaInput.value;
        const confirmSenha = confirmSenhaInput.value;

        if (senha === confirmSenha) {
            confirmSenhaInput.value = senha;
        } else {
            confirmSenhaInput.value = null;
        }
    }

    confirmSenhaInput.addEventListener("input", validarSenha);
    senhaInput.addEventListener("input", validarSenha);
});

