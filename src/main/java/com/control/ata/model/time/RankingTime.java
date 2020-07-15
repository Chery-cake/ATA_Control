package com.control.ata.model.time;

import com.control.ata.model.torneio.CategoriaCompeticao;

import javax.persistence.*;

@Entity
public class RankingTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Time time;

	private Integer pontuacao;

	//private RingueTime ringueTime;

	@ManyToOne
	private CategoriaCompeticao categoriaCompeticao;

}
