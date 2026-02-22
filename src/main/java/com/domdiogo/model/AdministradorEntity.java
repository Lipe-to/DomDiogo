package com.domdiogo.model;

public class AdministradorEntity {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String palavra;

    public AdministradorEntity(int id, String nome, String email, String senha, String palavra) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.palavra = palavra;
    }

    public AdministradorEntity(String nome, String email, String senha, String palavra) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.palavra = palavra;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getPalavra() {
        return palavra;
    }
}
