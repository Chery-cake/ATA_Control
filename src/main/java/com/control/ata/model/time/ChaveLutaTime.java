package com.control.ata.model.time;

import javax.persistence.*;

@Entity
public class ChaveLutaTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer posicao;
    private Integer fase;
    private Boolean terminou = false;

    @ManyToOne
    private Time timeVermelho;

    private Integer pontosTotaisVermelhos = 0;
    private Integer advertenciasVermelhas = 0;
    private Integer penalidadesVermelhas = 0;
    private Boolean desqualificacaoVermelha = false;

    @ManyToOne
    private Time timeBranco;

    private Integer pontosTotaisBrancos = 0;
    private Integer advertenciasBrancas = 0;
    private Integer penalidadesBrancas = 0;
    private Boolean desqualificacaoBranca = false;

    @ManyToOne
    private PlanilhaChaveamentoTime planilhaChaveamentoTime;

    public ChaveLutaTime() {
    }

    public ChaveLutaTime(Integer posicao, Integer fase, Time timeVermelho, Time timeBranco,
            PlanilhaChaveamentoTime planilhaChaveamentoTime) {
        this.posicao = posicao;
        this.fase = fase;
        this.timeVermelho = timeVermelho;
        this.timeBranco = timeBranco;
        this.planilhaChaveamentoTime = planilhaChaveamentoTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public Integer getFase() {
        return fase;
    }

    public Boolean getTerminou() {
        return terminou;
    }

    public void setTerminou(Boolean terminou) {
        this.terminou = terminou;
    }

    public Time getTimeVermelho() {
        return timeVermelho;
    }

    public void setTimeVermelho(Time timeVermelho) {
        this.timeVermelho = timeVermelho;
    }

    public Integer getPontosTotaisVermelhos() {
        return pontosTotaisVermelhos;
    }

    public void setPontosTotaisVermelhos(Integer pontosTotaisVermelhos) {
        this.pontosTotaisVermelhos = pontosTotaisVermelhos;
    }

    public Integer getAdvertenciasVermelhas() {
        return advertenciasVermelhas;
    }

    public void setAdvertenciasVermelhas(Integer advertenciasVermelhas) {
        this.advertenciasVermelhas = advertenciasVermelhas;
    }

    public Integer getPenalidadesVermelhas() {
        return penalidadesVermelhas;
    }

    public void setPenalidadesVermelhas(Integer penalidadesVermelhas) {
        this.penalidadesVermelhas = penalidadesVermelhas;
    }

    public Boolean getDesqualificacaoVermelha() {
        return desqualificacaoVermelha;
    }

    public void setDesqualificacaoVermelha(Boolean desqualificacaoVermelha) {
        this.desqualificacaoVermelha = desqualificacaoVermelha;
    }

    public Time getTimeBranco() {
        return timeBranco;
    }

    public void setTimeBranco(Time timeBranco) {
        this.timeBranco = timeBranco;
    }

    public Integer getPontosTotaisBrancos() {
        return pontosTotaisBrancos;
    }

    public void setPontosTotaisBrancos(Integer pontosTotaisBrancos) {
        this.pontosTotaisBrancos = pontosTotaisBrancos;
    }

    public Integer getAdvertenciasBrancas() {
        return advertenciasBrancas;
    }

    public void setAdvertenciasBrancas(Integer advertenciasBrancas) {
        this.advertenciasBrancas = advertenciasBrancas;
    }

    public Integer getPenalidadesBrancas() {
        return penalidadesBrancas;
    }

    public void setPenalidadesBrancas(Integer penalidadesBrancas) {
        this.penalidadesBrancas = penalidadesBrancas;
    }

    public Boolean getDesqualificacaoBranca() {
        return desqualificacaoBranca;
    }

    public void setDesqualificacaoBranca(Boolean desqualificacaoBranca) {
        this.desqualificacaoBranca = desqualificacaoBranca;
    }

    public PlanilhaChaveamentoTime getPlanilhaChaveamentoTime() {
        return planilhaChaveamentoTime;
    }
}
