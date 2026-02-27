package com.domdiogo.model;

public class ObservacaoEntity {
    private int id;
    private String titulo;
    private int matriculaAluno;
    private int idProfessor;
    private String observacao;
    private ColorPalette cor;

    public ObservacaoEntity(int id, String titulo, int matriculaAluno, int idProfessor, String observacao, ColorPalette cor) {
        this.id = id;
        this.titulo = titulo;
        this.matriculaAluno = matriculaAluno;
        this.idProfessor = idProfessor;
        this.observacao = observacao;
        this.cor = cor;
    }

    public ObservacaoEntity(String titulo, int matriculaAluno, int idProfessor, String observacao, ColorPalette cor) {
        this.titulo = titulo;
        this.matriculaAluno = matriculaAluno;
        this.idProfessor = idProfessor;
        this.observacao = observacao;
        this.cor = cor;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getMatriculaAluno() {
        return matriculaAluno;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public String getObservacao() {
        return observacao;
    }

    public ColorPalette getCor() {
        return cor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setCor(ColorPalette cor) {
        this.cor = cor;
    }
}