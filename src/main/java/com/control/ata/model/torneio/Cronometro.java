package com.control.ata.model.torneio;

import com.control.ata.model.individual.RingueIndividual;

import javax.persistence.*;

@Entity
public class Cronometro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer tempo_mim;
    private Integer tempo_seg;

    private Boolean rodando = Boolean.FALSE;

    @OneToOne(fetch = FetchType.LAZY)
    private RingueIndividual ringueIndividual;

    public Cronometro() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getTempo_mim() {
        return tempo_mim;
    }

    public void setTempo_mim(Integer tempo_mim) {
        this.tempo_mim = tempo_mim;
    }

    public Integer getTempo_seg() {
        return tempo_seg;
    }

    public void setTempo_seg(Integer tempo_seg) {
        this.tempo_seg = tempo_seg;
    }

    public Boolean getRodando() {
        return rodando;
    }

    public void setRodando(Boolean rodando) {
        this.rodando = rodando;
    }

    public RingueIndividual getRingueIndividual() {
        return ringueIndividual;
    }

    public void setRingueIndividual(RingueIndividual ringueIndividual) {
        this.ringueIndividual = ringueIndividual;
    }
}
