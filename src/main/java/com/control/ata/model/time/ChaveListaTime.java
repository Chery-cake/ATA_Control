package com.control.ata.model.time;

import javax.persistence.*;

@Entity
public class ChaveListaTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Time time;

    private Integer notaJuizA;
    private Integer notaJuizB;
    private Integer notaJuizC;

    @ManyToOne
    private PlanilhaListaTime planilhaListaTime;

    public ChaveListaTime() {
    }

    public ChaveListaTime(Time time, PlanilhaListaTime planilhaListaTime) {
        this.time = time;
        this.planilhaListaTime = planilhaListaTime;
    }

    public Integer getId() {
        return id;
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

    public PlanilhaListaTime getPlanilhaListaTime() {
        return planilhaListaTime;
    }

    public Integer getSoma() {
        return this.notaJuizA + this.notaJuizB + this.notaJuizC;
    }
}
