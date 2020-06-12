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

}