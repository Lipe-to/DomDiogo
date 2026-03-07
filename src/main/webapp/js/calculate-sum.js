document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".popup form").forEach(form => {

        const n1 = form.querySelector(".grade-n1");
        const n2 = form.querySelector(".grade-n2");
        const media = form.querySelector(".final-grade");

        if (!n1 || !n2 || !media) return;

        function calcular() {
            const v1 = parseFloat(n1.value);
            const v2 = parseFloat(n2.value);

            if (!isNaN(v1) && !isNaN(v2)) {
                media.value = ((v1 + v2) / 2).toFixed(1);
            } else {
                media.value = "";
            }
        }

        n1.addEventListener("input", calcular);
        n2.addEventListener("input", calcular);

        calcular();
    });

});