package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.Time;

import javax.persistence.*;

@Entity
public class Treinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa_fk")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "cidade_fk")
    private Time time;

    public Treinador() {
    }

    public Treinador(Pessoa pessoa, Time time) {
        this.pessoa = pessoa;
        this.time = time;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
