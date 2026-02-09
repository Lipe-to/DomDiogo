package com.domdiogo.model;

public class AptosEntity {
    private int id;
    private String nome;
    private String email;
    private boolean is_matriculado;

    public AptosEntity(int id, String nome, String email, boolean is_matriculado) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.is_matriculado = is_matriculado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_matriculado() {
        return is_matriculado;
    }

    public void setIs_matriculado(boolean is_matriculado) {
        this.is_matriculado = is_matriculado;
    }
}
