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

	public RankingTime() {
	}

	public RankingTime(Time time, Integer pontuacao, CategoriaCompeticao categoriaCompeticao) {
		this.time = time;
		this.pontuacao = pontuacao;
		this.categoriaCompeticao = categoriaCompeticao;
	}

	public Integer getId() {
		return id;
	}

	public Time getTime() {
		return time;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public CategoriaCompeticao getCategoriaCompeticao() {
		return categoriaCompeticao;
	}
}
