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
	@OneToMany(mappedBy = "planilhaChaveamentoTime")
	private Collection<ChaveLutaTime> chaveLutaTime;

	@ManyToOne
	@JoinColumn(name = "categoriaCompeticao_fk")
	private CategoriaCompeticao categoriaCompeticao;

	@ManyToOne
	@JoinColumn(name = "ringueTime_fk")
	private RingueTime ringueTime;

}
