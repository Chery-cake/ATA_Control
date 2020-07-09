package com.control.ata.model.time;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Treinador;
import com.control.ata.model.torneio.Titulo;
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
	@OneToMany(mappedBy = "time", cascade = CascadeType.ALL)
	private Collection<Treinador> treinadorList;

	@ManyToMany(mappedBy = "time")
	private Collection<Competidor> competidores;

	@JsonIgnore
	@OneToMany(mappedBy = "time", cascade = CascadeType.ALL)
	private Collection<RankingTime> rankingTimeList;

	@JsonIgnore
	@OneToMany(mappedBy = "time", cascade = CascadeType.ALL)
	private Collection<PlanilhaListaTime> planilhaListaTimeList;

	@ManyToMany(cascade = { CascadeType.PERSIST})
	private Collection<RingueTime> ringueTimeCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "timeVermelho", cascade = CascadeType.ALL)
	private Collection<ChaveLutaTime> chaveLutaTimeVermelho;

	@JsonIgnore
	@OneToMany(mappedBy = "timeBranco", cascade = CascadeType.ALL)
	private Collection<ChaveLutaTime> chaveLutaTimeBranco;

	@JsonIgnore
	@OneToMany(mappedBy = "time", cascade = CascadeType.ALL)
	private Collection<Titulo> tituloList;

}
