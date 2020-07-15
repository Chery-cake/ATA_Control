package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.Time;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Treinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Pessoa pessoa;

    @ManyToMany(mappedBy = "treinadorList")
    private Collection<Time> timeList;

    public Treinador() {
    }

    public Treinador(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Treinador(Pessoa pessoa, Collection<Time> timeList) {
        this.pessoa = pessoa;
        this.timeList = timeList;
    }

    public Treinador(Pessoa pessoa, Time time) {
        this.pessoa = pessoa;
        ArrayList<Time> times = new ArrayList<>();
        times.add(time);
        this.timeList = times;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Collection<Time> getTimeList() {
        return timeList;
    }

}
