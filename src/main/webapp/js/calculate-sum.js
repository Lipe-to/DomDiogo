document.querySelectorAll('.popup').forEach(popup => {
    const n1 = popup.querySelector('.n1')
    const n2 = popup.querySelector('.n2')
    const media = popup.querySelector('.media')

    if (!n1 || !n2 || !media) return

    function calcularMedia(){
        const v1 = parseFloat(n1.value)
        const v2 = parseFloat(n2.value)

        if(!isNaN(v1) && !isNaN(v2)){
            media.value = ((v1 + v2) / 2).toFixed(1)
        } else {
            media.value = 'Valor faltante'
        }
    }

    n1.addEventListener('input', calcularMedia)
    n2.addEventListener('input', calcularMedia)
})