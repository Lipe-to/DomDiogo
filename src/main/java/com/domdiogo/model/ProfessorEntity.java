package com.domdiogo.model;

public class ProfessorEntity {
    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private String palavra;
    private boolean ativo;

    public ProfessorEntity(int id, String nome, String usuario, String senha, String palavra, boolean ativo){
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.palavra = palavra;
        this.ativo = ativo;
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

    public boolean getAtivo() {
        return this.ativo;
    }
}