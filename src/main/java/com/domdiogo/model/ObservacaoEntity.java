package com.domdiogo.model;

public class ObservacaoEntity {
    private int id;
    private int matriculaAluno;
    private int idProfessor;
    private String observacao;

    public ObservacaoEntity( int id, int matriculaAluno, int idProfessor, String observacoes){
        this.id = id;
        this.matriculaAluno = matriculaAluno;
        this.idProfessor =idProfessor;
        this.observacao = observacoes;
    }

    public ObservacaoEntity(int matriculaAluno, int idProfessor, String observacoes){
        this.matriculaAluno = matriculaAluno;
        this.idProfessor =idProfessor;
        this.observacao = observacoes;
    }

    public int getId() {
        return this.id;
    }

    public int getMatriculaAluno() {
        return this.matriculaAluno;
    }

    public int getIdProfessor() {
        return this.idProfessor;
    }

    public String getObservacao(){
        return this.observacao;
    }
}
