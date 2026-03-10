package com.domdiogo.model;

public class AptoEntity {
    private int id;
    private String nome;
    private String usuario;
    private boolean isMatriculado;

    public AptoEntity(int id, String nome, String usuario, boolean isMatriculado) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.isMatriculado = isMatriculado;
    }

    public AptoEntity(String nome, String usuario, boolean isMatriculado) {
        this.nome = nome;
        this.usuario = usuario;
        this.isMatriculado = isMatriculado;
    }

    public AptoEntity(String usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public boolean isMatriculado() {
        return isMatriculado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setMatriculado(boolean matriculado) {
        isMatriculado = matriculado;
    }
}