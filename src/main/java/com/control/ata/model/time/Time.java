package com.control.ata.model.time;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Treinador;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Time {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String representa;

	@JsonIgnore
	@OneToMany(mappedBy = "time")
	private Collection<Treinador> treinadorList;

	@ManyToMany(mappedBy = "time")
	private Collection<Competidor> competidores;

	@JsonIgnore
	@OneToMany(mappedBy = "time")
	private Collection<RankingTime> rankingTimeList;

	@JsonIgnore
	@OneToMany(mappedBy = "time")
	private Collection<PlanilhaListaTime> planilhaListaTimeList;

	@ManyToMany(cascade = { CascadeType.PERSIST})
	private Collection<RingueTime> ringueTimeCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "timeVermelho")
	private Collection<ChaveLutaTime> chaveLutaTimeVermelho;

	@JsonIgnore
	@OneToMany(mappedBy = "timeBranco")
	private Collection<ChaveLutaTime> chaveLutaTimeBranco;

}
