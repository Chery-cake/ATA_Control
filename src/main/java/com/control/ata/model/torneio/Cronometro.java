package com.control.ata.model.torneio;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.time.RingueTime;

import javax.persistence.*;

@Entity
public class Cronometro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer tempo;

    private Integer numeroRingue;

	/*
	private ChaveLutaIndividual chaveLutaIndividual;

	private ChaveLutaTime chaveLutaTime;
	*/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ringueIndividual_fk")
    private RingueIndividual ringueIndividual;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ringueTime_fk")
    private RingueTime ringueTime;

    @ManyToOne
    @JoinColumn(name = "placar_fk")
    private Placar placar;

}
