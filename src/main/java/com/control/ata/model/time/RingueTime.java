package com.control.ata.model.time;

import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Cronometro;
import com.control.ata.model.torneio.Placar;
import com.control.ata.model.torneio.Torneio;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class RingueTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numero;

    @ManyToMany(mappedBy = "ringueTimeCollection")
    private Collection<Juiz> juiz;

    @ManyToMany(mappedBy = "ringueTimeCollection")
    private Collection<Time> time;

    @ManyToOne
    @JoinColumn(name = "torneio_fk")
    private Torneio torneio;

    //private Collection<Torneio> torneio;

    @JsonIgnore
    @OneToMany(mappedBy = "ringueTime")
    private Collection<PlanilhaListaTime> planilhaListaTime;

    @JsonIgnore
    @OneToMany(mappedBy = "ringueTime")
    private Collection<PlanilhaChaveamentoTime> planilhaChaveamentoTime;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "ringueTime")
    private Cronometro cronometro;

    @ManyToOne
    @JoinColumn(name = "placar_fk")
    private Placar placar;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private Collection<CategoriaCompeticao> categoriaCompeticao;

}
