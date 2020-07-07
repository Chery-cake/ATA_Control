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

	public PlanilhaChaveamentoIndividual() {
	}

	public PlanilhaChaveamentoIndividual(CategoriaCompeticao categoriaCompeticao,
			RingueIndividual ringueIndividual) {
		this.categoriaCompeticao = categoriaCompeticao;
		this.ringueIndividual = ringueIndividual;
	}

	public Integer getId() {
		return id;
	}

	public Collection<ChaveLutaIndividual> getChaveLutaIndividual() {
		return chaveLutaIndividual;
	}

	public CategoriaCompeticao getCategoriaCompeticao() {
		return categoriaCompeticao;
	}

	public RingueIndividual getRingueIndividual() {
		return ringueIndividual;
	}

}
