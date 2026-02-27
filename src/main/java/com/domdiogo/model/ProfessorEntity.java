package com.domdiogo.model;

public class ProfessorEntity {
    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private String palavra;

    public ProfessorEntity(int id, String nome, String usuario, String senha, String palavra) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.palavra = palavra;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getSenha() {
        return this.senha;
    }

    public String getPalavra() {
        return this.palavra;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String novaSenh) {
        this.senha = novaSenh;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
}