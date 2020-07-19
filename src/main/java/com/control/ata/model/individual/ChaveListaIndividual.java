package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;

import javax.persistence.*;

@Entity
public class ChaveListaIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Competidor competidor;

    private Integer notaJuizA;

    private Integer notaJuizB;

    private Integer notaJuizC;

    @ManyToOne
    private PlanilhaListaIndividual planilhaListaIndividual;

    public ChaveListaIndividual() {
    }

    public ChaveListaIndividual(Competidor competidor, PlanilhaListaIndividual planilhaListaIndividual) {
        this.competidor = competidor;
        this.planilhaListaIndividual = planilhaListaIndividual;
    }

    public Integer getId() {
        return id;
    }

    public Competidor getCompetidor() {
        return competidor;
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

    public PlanilhaListaIndividual getPlanilhaListaIndividual() {
        return planilhaListaIndividual;
    }

    public Integer getSoma() {
        return notaJuizA + notaJuizB + notaJuizC;
    }
}
