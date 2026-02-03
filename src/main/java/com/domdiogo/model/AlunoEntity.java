package com.domdiogo.model;

public class AlunoEntity {
    private int id;
    private String nome;
    private String matricula;
    private String email;
    private String senha;

    public AlunoEntity(int id, String nome, String matricula, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.senha = senha;
    }

    public AlunoEntity(String nome, String matricula, String email, String senha) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
