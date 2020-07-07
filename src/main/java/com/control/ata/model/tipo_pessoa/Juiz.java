package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.torneio.RodadaJuiz;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Juiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa_fk")
    private Pessoa pessoa;

    @JsonIgnore
    @OneToMany(mappedBy = "juiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RodadaJuiz> rodadaJuizList;

    @ManyToMany(mappedBy = "juiz")
    private Collection<RingueIndividual> ringueIndividualCollection;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private Collection<RingueTime> ringueTimeCollection;

    public Juiz() {
    }

    public Juiz(Pessoa pessoa) {
        this.pessoa = pessoa;
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
