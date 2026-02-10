package com.domdiogo.model;

public class NotaEntity {
    private int id;
    private double n1;
    private double n2;
    private double media;
    private int idDisciplina;
    private int matriculaAluno;

    public NotaEntity(int id, double n1, double n2, double media, int idDisciplina, int matriculaAluno){
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
        this.media = media;
        this.idDisciplina = idDisciplina;
        this.matriculaAluno = matriculaAluno;
    }
    public int getId() {
        return this.id;
    }

    public double getN1() {
        return this.n1;
    }

    public double getN2() {
        return this.n2;
    }

    public double getMedia() {
        return this.media;
    }

    public int getId_disciplina() {
        return this.idDisciplina;
    }

    public int getId_aluno() {
        return this.matriculaAluno;
    }

    public String toString() {
        return "Nota:" +
                "\tId=" + id +
                "\tN1=" + n1 +
                "\tN2=" + n2 +
                "\tMedia=" + media +
                "\tId_disciplina=" + idDisciplina +
                "\tId_aluno=" + matriculaAluno;
    }
}