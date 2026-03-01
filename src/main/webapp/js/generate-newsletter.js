async function gerarBoletim() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF('p', 'mm', 'a4');


    const nomeAluno = document.querySelector('.personal-info h3').innerText;
    const turmaAluno = document.querySelector('.table-info h3').innerText;

    doc.setFont("helvetica", "bold");
    doc.setFontSize(14);
    doc.text('BOLETIM ESCOLAR', 105, 15, { align: "center" });


    doc.setLineWidth(0.5);
    doc.rect(10, 20, 190, 25);

    doc.setFontSize(10);
    doc.text('Escola Dom Diogo', 12, 28);

    doc.setFont("helvetica", "normal");
    doc.text(`Nome: ${nomeAluno}`, 12, 35);
    doc.text(`Turma: ${turmaAluno}`, 12, 42);
    doc.text(`Ano Letivo: 2026`, 150, 42);

    const tabelaOriginal = document.getElementById("report-card");
    const linhas = tabelaOriginal.querySelectorAll("tbody tr");
    const dadosBoletim = [];

    linhas.forEach(linha => {
        const colunas = linha.querySelectorAll("td");
        if (colunas.length > 0) {
            dadosBoletim.push([
                colunas[0].innerText,
                colunas[1].innerText,
                colunas[2].innerText,
                colunas[3].innerText,
                colunas[4].innerText
            ]);
        }
    });

    doc.autoTable({
        startY: 55,
        head: [['Matéria', 'N1', 'N2', 'Média Final', 'Situação']],
        body: dadosBoletim,
        theme: 'grid',
        headStyles: {
            fillColor: [44, 62, 80],
            textColor: [255, 255, 255],
            halign: 'center'
        },
        styles: {
            halign: 'center',
            fontSize: 10
        },
        columnStyles: {
            0: { halign: 'left', fontStyle: 'bold', cellWidth: 60 },
        },
        didParseCell: function (data) {
            // Lógica para colorir Aprovado/Reprovado
            if (data.section === 'body' && data.column.index === 4) {
                const texto = data.cell.raw.trim().toUpperCase();
                if (texto === "APROVADO") {
                    data.cell.styles.textColor = [0, 100, 0];
                    data.cell.styles.fontStyle = 'bold';
                } else if (texto === "REPROVADO") {
                    data.cell.styles.textColor = [200, 0, 0];
                    data.cell.styles.fontStyle = 'bold';
                }
            }
        }
    });

    const finalY = doc.lastAutoTable.finalY + 30;
    doc.setFontSize(10);

    doc.line(10, finalY, 80, finalY);
    doc.text("Assinatura do Secretário", 10, finalY + 5);

    doc.line(120, finalY, 190, finalY);
    doc.text("Assinatura do Diretor", 120, finalY + 5);

    // Salva o arquivo
    doc.save(`Boletim_${nomeAluno.replace(/\s+/g, '_')}.pdf`);
}