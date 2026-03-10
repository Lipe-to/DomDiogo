const first = document.getElementById('password-first-validation')
const second = document.getElementById('password-second-validation')
const submit = document.getElementById('button-submit')
const container = document.getElementById('password-validation')
const required = document.getElementById('password-validation-p')

function checkPassword() {
    const isValid = first.checkValidity()
    const equals = first.value === second.value && second.value.length > 0
    validation(first)
    validation(second)

    if (isValid && equals) {
        submit.disabled = false
        required.classList.add('valid')
    }
    else {
        submit.disabled = true
        required.classList.remove('valid')
    }

    if (isValid && equals) {
        container.classList.remove('wrong-password-validation')
    }
    else if (second.value.length > 0) {
        container.classList.add('wrong-password-validation')
    }
    else {
        container.classList.remove('wrong-password-validation')
    }
}

function validation(input) {
    if (input === first) {
        if (first.value != 0) {
            if (!first.checkValidity()) {
                input.classList.add('invalid')
            } else {
                input.classList.remove('invalid')
            }
        }
        else {
            input.classList.remove('invalid')
        }
    }

    if (input === second) {
        if (second.value != 0) {
            if (second.value !== first.value || second.value.length === 0) {
                input.classList.add('invalid')
            } else {
                input.classList.remove('invalid')
            }
        }
        else {
            input.classList.remove('invalid')
        }
    }
}

first.addEventListener('input', checkPassword)
second.addEventListener('input', checkPassword)