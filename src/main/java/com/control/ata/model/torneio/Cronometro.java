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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private RingueIndividual ringueIndividual;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private RingueTime ringueTime;

    @ManyToOne
    private Placar placar;

}
