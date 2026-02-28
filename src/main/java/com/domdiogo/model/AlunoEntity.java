package com.domdiogo.model;

public class AlunoEntity {
    private int matricula;
    private String nome;
    private String usuario;
    private String senha;
    private String palavra;
    private String turma;

    public AlunoEntity(int matricula, String nome, String usuario, String senha, String palavra, String turma) {
        this.matricula = matricula;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.palavra = palavra;
        this.turma = turma;
    }

    public AlunoEntity(String usuario, String senha, String palavra) {
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

    public String getTurma() {
        return turma;
    }

    public void setSenha(String novaSenh) {
        this.senha = novaSenh;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
}