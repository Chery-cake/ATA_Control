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

}
