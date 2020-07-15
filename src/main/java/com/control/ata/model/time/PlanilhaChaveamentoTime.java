package com.control.ata.model.time;

import com.control.ata.model.torneio.CategoriaCompeticao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class PlanilhaChaveamentoTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonIgnore
	@OneToMany(mappedBy = "planilhaChaveamentoTime", cascade = CascadeType.ALL)
	private Collection<ChaveLutaTime> chaveLutaTime;

	@ManyToOne
	private CategoriaCompeticao categoriaCompeticao;

	@ManyToOne
	private RingueTime ringueTime;

	public PlanilhaChaveamentoTime() {
	}

	public PlanilhaChaveamentoTime(CategoriaCompeticao categoriaCompeticao,
			RingueTime ringueTime) {
		this.categoriaCompeticao = categoriaCompeticao;
		this.ringueTime = ringueTime;
	}

	public Integer getId() {
		return id;
	}

	public Collection<ChaveLutaTime> getChaveLutaTime() {
		return chaveLutaTime;
	}

	public CategoriaCompeticao getCategoriaCompeticao() {
		return categoriaCompeticao;
	}

	public RingueTime getRingueTime() {
		return ringueTime;
	}
}
