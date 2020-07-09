package com.control.ata.model.time;

import com.control.ata.model.torneio.CategoriaCompeticao;

import javax.persistence.*;

@Entity
public class PlanilhaListaTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "time_fk")
    private Time time;

    private Integer notaJuizA;

    private Integer notaJuizB;

    private Integer notaJuizC;

    @ManyToOne
    @JoinColumn(name = "ringueTime_fk")
    private RingueTime ringueTime;

    @ManyToOne
    @JoinColumn(name = "categoriaCompeticao_fk")
    private CategoriaCompeticao categoriaCompeticao;

    public PlanilhaListaTime() {
    }

    public PlanilhaListaTime(Time time, RingueTime ringueTime,
            CategoriaCompeticao categoriaCompeticao) {
        this.time = time;
        this.ringueTime = ringueTime;
        this.categoriaCompeticao = categoriaCompeticao;
    }

    public Time getTime() {
        return time;
    }

    public Integer getNotaJuizA() {
        return notaJuizA;
    }

    public void setNotaJuizA(Integer notaJuizA) {
        this.notaJuizA = notaJuizA;
    }

    public Integer getNotaJuizB() {
        return notaJuizB;
    }

    public void setNotaJuizB(Integer notaJuizB) {
        this.notaJuizB = notaJuizB;
    }

    public Integer getNotaJuizC() {
        return notaJuizC;
    }

    public void setNotaJuizC(Integer notaJuizC) {
        this.notaJuizC = notaJuizC;
    }

    public RingueTime getRingueTime() {
        return ringueTime;
    }

    public CategoriaCompeticao getCategoriaCompeticao() {
        return categoriaCompeticao;
    }
}