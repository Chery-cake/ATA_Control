package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;

import javax.persistence.*;

@Entity
public class ColocacaoIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual;

    @OneToOne
    private PlanilhaListaIndividual planilhaListaIndividual;

    @ManyToOne
    private Competidor competidor_1;

    @ManyToOne
    private Competidor competidor_2;

    @ManyToOne
    private Competidor competidor_3;

    public ColocacaoIndividual() {
    }

    public ColocacaoIndividual(PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual, Competidor competidor_1, Competidor competidor_2, Competidor competidor_3) {
        this.planilhaChaveamentoIndividual = planilhaChaveamentoIndividual;
        this.competidor_1 = competidor_1;
        this.competidor_2 = competidor_2;
        this.competidor_3 = competidor_3;
    }

    public ColocacaoIndividual(PlanilhaListaIndividual planilhaListaIndividual, Competidor competidor_1, Competidor competidor_2, Competidor competidor_3) {
        this.planilhaListaIndividual = planilhaListaIndividual;
        this.competidor_1 = competidor_1;
        this.competidor_2 = competidor_2;
        this.competidor_3 = competidor_3;
    }

    public Integer getId() {
        return id;
    }

    public PlanilhaChaveamentoIndividual getPlanilhaChaveamentoIndividual() {
        return planilhaChaveamentoIndividual;
    }

    public PlanilhaListaIndividual getPlanilhaListaIndividual() {
        return planilhaListaIndividual;
    }

    public Competidor getCompetidor_1() {
        return competidor_1;
    }

    public void setCompetidor_1(Competidor competidor_1) {
        this.competidor_1 = competidor_1;
    }

    public Competidor getCompetidor_2() {
        return competidor_2;
    }

    public void setCompetidor_2(Competidor competidor_2) {
        this.competidor_2 = competidor_2;
    }

    public Competidor getCompetidor_3() {
        return competidor_3;
    }

    public void setCompetidor_3(Competidor competidor_3) {
        this.competidor_3 = competidor_3;
    }
}
