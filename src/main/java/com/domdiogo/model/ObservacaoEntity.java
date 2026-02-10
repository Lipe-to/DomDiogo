package com.domdiogo.model;

public class ObservacaoEntity {
    private int id;
    private int matriculaAluno;
    private int idProfessor;
    private String observacoes;

    public ObservacaoEntity( int id, int matriculaAluno, int idProfessor, String observacoes){
        this.id = id;
        this.matriculaAluno = matriculaAluno;
        this.idProfessor =idProfessor;
        this.observacoes = observacoes;
    }

    public ObservacaoEntity(int matriculaAluno, int idProfessor, String observacoes){
        this.matriculaAluno = matriculaAluno;
        this.idProfessor =idProfessor;
        this.observacoes = observacoes;
    }

    public int getId() {
        return this.id;
    }

    public int getId_aluno() {
        return this.matriculaAluno;
    }

    public int getId_professor() {
        return this.idProfessor;
    }

    public String getObservacoes(){
        return this.observacoes;
    }

    public String toString() {
        return "Observacao:" +
                "\tId=" + id +
                "\tObservacoes='" + observacoes+
                "\tId_aluno=" + matriculaAluno +
                "\tId_professor=" + idProfessor;
    }
}
