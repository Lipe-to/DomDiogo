package com.domdiogo.model;

public class DisciplinaEntity {
    private int id;
    private String nome;
    private int idProfessor;

    public DisciplinaEntity(int id, String nome, int idProfessor) {
        this.id = id;
        this.nome = nome;
        this.idProfessor = idProfessor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getId_professor() {
        return idProfessor;
    }

}