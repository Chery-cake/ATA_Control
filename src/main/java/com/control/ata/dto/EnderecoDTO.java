package com.control.ata.dto;

public class EnderecoDTO {

    private String rua;
    private Integer cidade;

    public EnderecoDTO() {
    }

    public EnderecoDTO(String rua, Integer cidade) {
        this.rua = rua;
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getCidade() {
        return cidade;
    }

    public void setCidade(Integer cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "EnderecoDTO{" +
                "rua='" + rua + '\'' +
                ", cidade=" + cidade +
                '}';
    }
}
