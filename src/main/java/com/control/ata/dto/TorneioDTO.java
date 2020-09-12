package com.control.ata.dto;

import java.util.Date;

public class TorneioDTO {

    private Date dataInicio;
    private Date dataTermino;
    private Integer maxNumeroRingues;
    private Boolean pontuar;
    private EnderecoDTO enderecoDTO;
    private Integer categoriaTorneio;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Integer getMaxNumeroRingues() {
        return maxNumeroRingues;
    }

    public void setMaxNumeroRingues(Integer maxNumeroRingues) {
        this.maxNumeroRingues = maxNumeroRingues;
    }

    public Boolean getPontuar() {
        return pontuar;
    }

    public void setPontuar(Boolean pontuar) {
        this.pontuar = pontuar;
    }

    public EnderecoDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }

    public Integer getCategoriaTorneio() {
        return categoriaTorneio;
    }

    public void setCategoriaTorneio(Integer categoriaTorneio) {
        this.categoriaTorneio = categoriaTorneio;
    }
}
