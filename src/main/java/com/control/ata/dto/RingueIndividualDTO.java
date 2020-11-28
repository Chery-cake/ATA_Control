package com.control.ata.dto;

import java.util.ArrayList;

public class RingueIndividualDTO {

    private Integer id;

    private Boolean fechado;
    private Boolean genero;

    private Integer numeroRingue;
    private Integer numeroRodada;

    private Integer idade;
    private Integer nivel;

    private ArrayList<Integer> juizes;
    private ArrayList<Integer> categorias;

    private Integer torneio;
    private Integer rodada;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFechado() {
        return fechado;
    }

    public void setFechado(Boolean fechado) {
        this.fechado = fechado;
    }

    public Boolean getGenero() {
        return genero;
    }

    public void setGenero(Boolean genero) {
        this.genero = genero;
    }

    public Integer getNumeroRingue() {
        return numeroRingue;
    }

    public void setNumeroRingue(Integer numeroRingue) {
        this.numeroRingue = numeroRingue;
    }

    public Integer getNumeroRodada() {
        return numeroRodada;
    }

    public void setNumeroRodada(Integer numeroRodada) {
        this.numeroRodada = numeroRodada;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public ArrayList<Integer> getJuizes() {
        return juizes;
    }

    public void setJuizes(ArrayList<Integer> juizes) {
        this.juizes = juizes;
    }

    public ArrayList<Integer> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Integer> categorias) {
        this.categorias = categorias;
    }

    public Integer getTorneio() {
        return torneio;
    }

    public void setTorneio(Integer torneio) {
        this.torneio = torneio;
    }

    public Integer getRodada() {
        return rodada;
    }

    public void setRodada(Integer rodada) {
        this.rodada = rodada;
    }

    @Override
    public String toString() {
        return "RingueIndividualDTO{" +
                "id=" + id +
                ", fechado=" + fechado +
                ", genero=" + genero +
                ", numeroRingue=" + numeroRingue +
                ", numeroRodada=" + numeroRodada +
                ", idade=" + idade +
                ", nivel=" + nivel +
                ", juizes=" + juizes.size() +
                ", categorias=" + categorias.size() +
                ", torneio=" + torneio +
                ", rodada=" + rodada +
                '}';
    }
}
