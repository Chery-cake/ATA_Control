package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.torneio.RodadaJuiz;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Juiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Pessoa pessoa;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<RodadaJuiz> rodadaJuizList;

    @ManyToMany(mappedBy = "juiz")
    private Collection<RingueIndividual> ringueIndividualCollection;

    @ManyToMany(mappedBy = "juiz")
    private Collection<RingueTime> ringueTimeCollection;

    public Juiz() {
    }

    public Juiz(Pessoa pessoa, Collection<RodadaJuiz> rodadaJuizList) {
        this.pessoa = pessoa;
        this.rodadaJuizList = rodadaJuizList;
    }

    public Integer getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Collection<RodadaJuiz> getRodadaJuizList() {
        return rodadaJuizList;
    }

    public void setRodadaJuizList(Collection<RodadaJuiz> rodadaJuizList) {
        this.rodadaJuizList = rodadaJuizList;
    }

}
