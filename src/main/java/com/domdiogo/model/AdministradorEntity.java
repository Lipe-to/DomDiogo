package com.domdiogo.model;

public class AdministradorEntity {
    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private String palavra_chave;

    public AdministradorEntity(int id, String nome, String usuario, String senha, String palavra_chave) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.palavra_chave = palavra_chave;
    }

    public AdministradorEntity(String nome, String usuario, String senha, String palavra_chave) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.palavra_chave = palavra_chave;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getPalavra_chave() {
        return palavra_chave;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPalavra_chave(String palavra_chave) {
        this.palavra_chave = palavra_chave;
    }
}
