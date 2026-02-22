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

    public NotaEntity(int id, double n1, double n2) {
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
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

    public int getIdDisciplina() {
        return this.idDisciplina;
    }

    public int getMatriculaAluno() {
        return this.matriculaAluno;
    }


}