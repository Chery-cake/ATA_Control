package com.control.ata.dto;

import java.util.ArrayList;

public class CompetidorDTO {

    private Double peso;
    private Double altura;
    private Integer pessoa;
    private Integer torneio;
    private ArrayList<Integer> categoriaCompeticao;
    private ArrayList<Integer> categoriaCompeticaoFechada;

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

    public ArrayList<Integer> getCategoriaCompeticaoFechada() {
        return categoriaCompeticaoFechada;
    }

    public void setCategoriaCompeticaoFechada(ArrayList<Integer> categoriaCompeticaoFechada) {
        this.categoriaCompeticaoFechada = categoriaCompeticaoFechada;
    }

    @Override
    public String toString() {

        StringBuilder aux = new StringBuilder("CompetidorDTO{" +
                                                      "peso=" + peso +
                                                      ", altura=" + altura +
                                                      ", pessoa=" + pessoa +
                                                      ", torneio=" + torneio);

        for (int i = 0; i < categoriaCompeticao.size(); i++) {
            aux.append(", categoriaCompeticao[").append(i).append("]=").append(categoriaCompeticao.get(0));
        }
        for (int i = 0; i < categoriaCompeticaoFechada.size(); i++) {
            aux.append(", categoriaCompeticao[").append(i).append("]=").append(categoriaCompeticaoFechada.get(0));
        }

        return aux.toString() + '}';
    }
}
