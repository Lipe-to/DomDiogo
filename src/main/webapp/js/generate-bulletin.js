async function gerarBoletim() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF('p', 'mm', 'a4');

    const logoImg = document.getElementById('img-logo-pdf');
    const assinaturaSecImg = document.getElementById('img-assinatura-sec');
    const assinaturaDirImg = document.getElementById('img-assinatura-dir');
    const nomeAluno = document.querySelector('.personal-info h3').innerText;
    const turmaAluno = document.querySelector('.table-info h3').innerText;

    if (logoImg) {
        doc.addImage(logoImg, 'PNG', 10, 10, 45, 10 );
    }

    doc.setFont('helvetica', 'bold');
    doc.setFontSize(14);
    doc.text('BOLETIM ESCOLAR', 105, 18, { align: 'center' });

    doc.setLineWidth(0.2);
    doc.rect(10, 28, 190, 18);

    doc.setFontSize(10);
    doc.setFont('helvetica', 'normal');

    doc.text(`Aluno(a): ${nomeAluno.toUpperCase()}`, 13, 35);
    doc.text(`Turma: ${turmaAluno}`, 13, 41);

    doc.text(`Ano Letivo: 2026`, 197, 41, { align: 'right' });

    const tabelaOriginal = document.getElementById('report-card');
    const linhas = tabelaOriginal.querySelectorAll('tbody tr');
    const dadosBoletim = [];

    linhas.forEach(linha => {
        const colunas = linha.querySelectorAll('td');
        if (colunas.length > 0) {
            dadosBoletim.push([
                colunas[0].innerText, // matéria
                colunas[1].innerText, // n1
                colunas[2].innerText, // n2
                colunas[3].innerText, // média
                colunas[4].innerText  // situação
            ]);
        }
    });

    doc.autoTable({
        startY: 52,
        margin: { left: 10, right: 10 },
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
            fontSize: 9
        },
        columnStyles: {
            0: { halign: 'left', fontStyle: 'bold', cellWidth: 70 },
        },
        didParseCell: function (data) {
            if (data.section === 'body' && data.column.index === 4) {
                const texto = data.cell.raw.trim().toUpperCase();
                if (texto === 'APROVADO') {
                    data.cell.styles.textColor = [0, 100, 0];
                } else if (texto === 'REPROVADO') {
                    data.cell.styles.textColor = [200, 0, 0];
                }
            }
        }
    });

    const finalY = doc.lastAutoTable.finalY + 45;
    doc.setFontSize(10);

    const sigWidth = 150;
    const sigHeight = 75;

    if (assinaturaDirImg) {
        doc.addImage(assinaturaDirImg, 'PNG', 55, finalY - 55, sigWidth, sigHeight);
    }
    doc.line(125, finalY, 200, finalY);
    doc.text('Assinatura do Diretor', 162.5, finalY + 5, { align: 'center' });

    doc.save(`Boletim_${nomeAluno.replace(/\s+/g, '_')}.pdf`);}