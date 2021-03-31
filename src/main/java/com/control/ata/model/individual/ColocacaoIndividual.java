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

}
