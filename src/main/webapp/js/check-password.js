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



// const password = document.getElementById('password');
// const confirmPassword = document.getElementById('confirm_password');
// const message = document.getElementById('message');
// const submitButton = document.getElementById('submitButton');

// function checkPasswordMatch() {
//     if (password.value === confirmPassword.value) {
//         message.style.color = 'green';
//         message.innerHTML = 'Passwords match';
//         submitButton.disabled = false; // Enable button if passwords match
//     } else {
//         message.style.color = 'red';
//         message.innerHTML = 'Passwords do not match';
//         submitButton.disabled = true; // Disable button if they don't match
//     }
// }

// // Add event listeners to both fields for real-time validation
// password.addEventListener('keyup', checkPasswordMatch);
// confirmPassword.addEventListener('keyup', checkPasswordMatch);

// // Optional: Add form submission handler to prevent submission if validation fails (though the button is disabled)
// document.getElementById('registrationForm').addEventListener('submit', function(event) {
//     if (password.value !== confirmPassword.value) {
//         event.preventDefault(); // Prevent form submission
//         alert('Please ensure your passwords match.');
//     }
// });
