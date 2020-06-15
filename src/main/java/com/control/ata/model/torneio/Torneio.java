package com.control.ata.model.torneio;

import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.time.RingueTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Torneio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Date dataInicio;

	private Date dataTermino;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "endereco_fk", nullable = false)
	private Endereco endereco;

	@JsonIgnore
	@OneToMany(mappedBy = "torneio")
	private Collection<RodadaJuiz> rodadaJuizList;

	@JsonIgnore
	@OneToMany(mappedBy = "torneio")
	private Collection<RingueIndividual> ringueIndividualCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "torneio")
	private Collection<RingueTime> ringueTimeCollection;

}
