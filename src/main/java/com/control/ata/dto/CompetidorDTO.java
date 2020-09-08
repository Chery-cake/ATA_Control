package com.control.ata.dto;

import java.util.ArrayList;

public class CompetidorDTO {

    private Double peso;
    private Double altura;
    private Integer pessoa;
    private Integer torneio;
    private ArrayList<Integer> categoriaCompeticao;

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Integer getPessoa() {
        return pessoa;
    }

    public void setPessoa(Integer pessoa) {
        this.pessoa = pessoa;
    }

    public Integer getTorneio() {
        return torneio;
    }

    public void setTorneio(Integer torneio) {
        this.torneio = torneio;
    }

    public ArrayList<Integer> getCategoriaCompeticao() {
        return categoriaCompeticao;
    }

    public void setCategoriaCompeticao(ArrayList<Integer> categoriaCompeticao) {
        this.categoriaCompeticao = categoriaCompeticao;
    }

    @Override
    public String toString() {

        String aux = "CompetidorDTO{" +
                "peso=" + peso +
                ", altura=" + altura +
                ", pessoa=" + pessoa +
                ", torneio=" + torneio;

        for (int i = 0; i < categoriaCompeticao.size(); i++) {
            aux = aux +
                    ", categoriaCompeticao[" + i + "]=" + categoriaCompeticao.get(0);
        }

        return aux + '}';
    }
}
