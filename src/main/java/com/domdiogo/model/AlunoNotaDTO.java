package com.domdiogo.model;

public class AlunoNotaDTO {

    private int matricula;
    private String nomeAluno;
    private String turma;
    private String ultimoLogin;
    private Integer notaId;
    private Double n1;
    private Double n2;
    private Double media;
    private Integer idDisciplina;
    private String disciplinaNome;

    public AlunoNotaDTO(int matricula, String nomeAluno, String turma, String ultimoLogin, Integer notaId, Double n1, Double n2, Double media, Integer idDisciplina, String disciplinaNome) {
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.turma = turma;
        this.ultimoLogin = ultimoLogin;
        this.notaId = notaId;
        this.n1 = n1;
        this.n2 = n2;
        this.media = media;
        this.idDisciplina = idDisciplina;
        this.disciplinaNome = disciplinaNome;
    }

    public int getMatricula() { return matricula; }
    public String getNomeAluno() { return nomeAluno; }
    public String getTurma() { return turma; }
    public String getUltimoLogin() { return ultimoLogin; }
    public Integer getNotaId() { return notaId; }
    public Double getN1() { return n1; }
    public Double getN2() { return n2; }
    public Double getMedia() { return media; }
    public Integer getIdDisciplina() { return idDisciplina; }
    public String getDisciplinaNome() { return disciplinaNome; }

    public String getSituacao() {
        if (media == null) return "Sem Nota";
        return media >= 7.0 ? "Aprovado" : "Reprovado";
    }

    public String getSituacaoCss() {
        if (media == null) return "";
        return media >= 7.0 ? "appr" : "repr";
    }
}