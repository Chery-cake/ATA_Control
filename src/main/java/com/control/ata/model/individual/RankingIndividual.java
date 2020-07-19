package com.control.ata.model.individual;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.torneio.CategoriaCompeticao;

import javax.persistence.*;

@Entity
public class RankingIndividual {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Pessoa pessoa;

	private Integer pontuacao;

	//private RingueIndividual ringueIndividual;

	@ManyToOne
	private CategoriaCompeticao categoriaCompeticao;

	public RankingIndividual() {
	}

	public RankingIndividual(Pessoa pessoa, Integer pontuacao,
			CategoriaCompeticao categoriaCompeticao) {
		this.pessoa = pessoa;
		this.pontuacao = pontuacao;
		this.categoriaCompeticao = categoriaCompeticao;
	}

	public Integer getId() {
		return id;
	}

	public Pessoa getPessoa() {
		return pessoa;
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
