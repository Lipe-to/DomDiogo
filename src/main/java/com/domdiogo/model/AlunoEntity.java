package com.domdiogo.model;

public class AlunoEntity {
    private int matricula;
    private String nome;
    private String usuario;
    private String senha;
    private String palavra;

    public AlunoEntity(int matricula, String nome, String usuario, String senha, String palavra) {
        this.matricula = matricula;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.palavra = palavra;
    }

    public AlunoEntity(String nome, String usuario, String senha, String palavra) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.palavra = palavra;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getPalavra() {
        return palavra;
    }
}