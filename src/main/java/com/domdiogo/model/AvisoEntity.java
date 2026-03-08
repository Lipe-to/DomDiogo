package com.domdiogo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AvisoEntity {
    private int id;
    private String titulo;
    private String aviso;
    private LocalDate prazo;
    private int idProfessor;
    private String cor;

    public AvisoEntity(int id, String titulo, String aviso, LocalDate prazo, int idProfessor, String cor) {
        this.id = id;
        this.titulo = titulo;
        this.aviso = aviso;
        this.prazo = prazo;
        this.idProfessor = idProfessor;
        this.cor = cor;
    }

    public AvisoEntity(String titulo, String aviso, LocalDate prazo, int idProfessor, String cor) {
        this.titulo = titulo;
        this.aviso = aviso;
        this.prazo = prazo;
        this.idProfessor = idProfessor;
        this.cor = cor;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAviso() {
        return aviso;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public String getCor() {
        return cor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}

