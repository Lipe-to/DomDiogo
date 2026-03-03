package com.domdiogo.model;

public class AlunoNotaDTO {

    // ===== DADOS DO ALUNO =====
    private int matricula;
    private String nomeAluno;
    private String turma;

    // ===== DADOS DA NOTA =====
    private Integer notaId;          // Pode ser null (LEFT JOIN)
    private Double n1;               // Pode ser null
    private Double n2;               // Pode ser null
    private Double media;            // Pode ser null
    private Integer idDisciplina;    // Pode ser null
    private String disciplinaNome;   // Pode ser null

    // ===== CONSTRUTOR COMPLETO =====
    public AlunoNotaDTO(int matricula,
                        String nomeAluno,
                        String turma,
                        Integer notaId,
                        Double n1,
                        Double n2,
                        Double media,
                        Integer idDisciplina,
                        String disciplinaNome) {

        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.turma = turma;
        this.notaId = notaId;
        this.n1 = n1;
        this.n2 = n2;
        this.media = media;
        this.idDisciplina = idDisciplina;
        this.disciplinaNome = disciplinaNome;
    }

    // ===== GETTERS =====

    public int getMatricula() {
        return matricula;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getTurma() {
        return turma;
    }

    public Integer getNotaId() {
        return notaId;
    }

    public Double getN1() {
        return n1;
    }

    public Double getN2() {
        return n2;
    }

    public Double getMedia() {
        return media;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public String getDisciplinaNome() {
        return disciplinaNome;
    }

    // ===== MÉTODOS AUXILIARES =====

    public boolean isAprovado() {
        return media != null && media >= 7.0;
    }

    public String getSituacao() {
        if (media == null) return "Sem nota";
        return media >= 7.0 ? "Aprovado" : "Reprovado";
    }

    public String getSituacaoCss() {
        if (media == null) return "";
        return media >= 7.0 ? "appr" : "repr";
    }

    public double getMediaCalculada() {
        if (n1 == null || n2 == null) return 0.0;
        return (n1 + n2) / 2.0;
    }
}