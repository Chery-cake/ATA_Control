package com.control.ata.model.individual;

import com.control.ata.model.torneio.CategoriaCompeticao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class PlanilhaChaveamentoIndividual {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonIgnore
	@OneToMany(mappedBy = "planilhaChaveamentoIndividual")
	private Collection<ChaveLutaIndividual> chaveLutaIndividual;

	@ManyToOne
	@JoinColumn(name = "categoriaCompeticao_fk")
	private CategoriaCompeticao categoriaCompeticao;

	@ManyToOne
	@JoinColumn(name = "ringueIndividual_fk")
	private RingueIndividual ringueIndividual;

}
