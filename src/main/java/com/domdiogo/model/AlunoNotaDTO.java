package com.domdiogo.model;

public class AlunoNotaDTO {

    private int matricula;
    private String nomeAluno;
    private String turma;

    private Integer notaId;
    private Double n1;
    private Double n2;
    private Double media;
    private Integer idDisciplina;
    private String disciplinaNome;
    private String ultimoLogin;


    public AlunoNotaDTO(int matricula, String nomeAluno, String turma, Integer notaId, Double n1, Double n2, Double media, Integer idDisciplina, String disciplinaNome, String ultimoLogin) {
        this.matricula = matricula; this.nomeAluno = nomeAluno;
        this.turma = turma;
        this.notaId = notaId;
        this.n1 = n1;
        this.n2 = n2;
        this.media = media;
        this.idDisciplina = idDisciplina;
        this.disciplinaNome = disciplinaNome;
        this.ultimoLogin = ultimoLogin;
    }

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

    public boolean isAprovado() {
        return media != null && media >= 7.0;
    }

    public String getSituacao() {
        if (media == null) return "Sem Nota";
        return media >= 7.0 ? "Aprovado" : "Reprovado";
    }

    public String getSituacaoCss() {
        if (media == null) return "";
        return media >= 7.0 ? "appr" : "repr";
    }

    public String getMediaCalculada() {
        if (n1 == null || n2 == null) return "-";
        double media = (n1 + n2) / 2.0;
        return String.format("%.2f", media);
    }
}