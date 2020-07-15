package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;

import javax.persistence.*;

@Entity
public class ChaveLutaIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer posicao;
    private Integer fase;

    @ManyToOne
    private Competidor competidorVermelho;

    private Integer pontosVermelhos = 0;
    private Integer advertenciasVermelhas = 0;
    private Integer penalidadesVermelhas = 0;
    private Boolean desqualificacaoVermelha = false;

    @ManyToOne
    private Competidor competidorBranco;

    private Integer pontosBrancos = 0;
    private Integer advertenciasBrancas = 0;
    private Integer penalidadesBrancas = 0;
    private Boolean desqualificacaoBranca = false;

    @ManyToOne
    private PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual;

    public ChaveLutaIndividual() {
    }

    public ChaveLutaIndividual(Integer posicao, Integer fase,
            Competidor competidorVermelho, Competidor competidorBranco,
            PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual) {
        this.posicao = posicao;
        this.fase = fase;
        this.competidorVermelho = competidorVermelho;
        this.competidorBranco = competidorBranco;
        this.planilhaChaveamentoIndividual = planilhaChaveamentoIndividual;
        this.desqualificacaoBranca = false;
        this.desqualificacaoVermelha = false;
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

    public Competidor getCompetidorVermelho() {
        return competidorVermelho;
    }

    public Integer getPontosVermelhos() {
        return pontosVermelhos;
    }

    public void setPontosVermelhos(Integer pontosVermelhos) {
        this.pontosVermelhos = pontosVermelhos;
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

    public Competidor getCompetidorBranco() {
        return competidorBranco;
    }

    public void setCompetidorBranco(Competidor competidorBranco) {
        this.competidorBranco = competidorBranco;
    }

    public Integer getPontosBrancos() {
        return pontosBrancos;
    }

    public void setPontosBrancos(Integer pontosBrancos) {
        this.pontosBrancos = pontosBrancos;
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

    public PlanilhaChaveamentoIndividual getPlanilhaChaveamentoIndividual() {
        return planilhaChaveamentoIndividual;
    }
}
