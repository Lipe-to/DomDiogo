package com.domdiogo.model;

public class NotaEntity {
    private int id;
    private Double n1;
    private Double n2;
    private Double media;
    private int idDisciplina;
    private int matriculaAluno;
    private String nomeDisciplina;

    public NotaEntity(int id, Double n1, Double n2, Double media, int idDisciplina, int matriculaAluno) {
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
        this.media = media;
        this.idDisciplina = idDisciplina;
        this.matriculaAluno = matriculaAluno;
    }

    public NotaEntity(int id, Double n1, Double n2, Double media, int idDisciplina, int matriculaAluno, String nomeDisciplina) {
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
        this.media = media;
        this.idDisciplina = idDisciplina;
        this.matriculaAluno = matriculaAluno;
        this.nomeDisciplina = nomeDisciplina;
    }

    public NotaEntity(int id, Double n1, Double n2) {
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
    }

    public int getId() {
        return this.id;
    }

    public Double getN1() {
        return this.n1;
    }

    public Double getN2() {
        return this.n2;
    }

    public Double getMedia() {
        return this.media;
    }

    public String getMediaCalculada() {
        if (n1 == null || n2 == null) {
            return "-";
        }
        double media = (n1 + n2) / 2;
        return String.format("%.2f", media);
    }

    public String getSituacao() {
        if (media == null) return "Sem Nota";
        return media >= 7.0 ? "Aprovado" : "Reprovado";
    }

    public int getIdDisciplina() {
        return this.idDisciplina;
    }

    public int getMatriculaAluno() {
        return this.matriculaAluno;
    }

    public String getNomeDisciplina() {
        return this.nomeDisciplina;
    }
}