package com.control.ata.model.time;

import com.control.ata.model.torneio.CategoriaCompeticao;

import javax.persistence.*;

@Entity
public class RankingTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "time_fk")
	private Time time;

	private Integer pontuacao;

	//private RingueTime ringueTime;

	@ManyToOne
	@JoinColumn(name = "categoriaCompeticao_fk")
	private CategoriaCompeticao categoriaCompeticao;

}
