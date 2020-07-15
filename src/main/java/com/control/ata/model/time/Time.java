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
	private Boolean junior;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Collection<Treinador> treinadorList;

	@ManyToMany(cascade = CascadeType.PERSIST)
	private Collection<Competidor> competidores;

	@JsonIgnore
	@OneToMany(mappedBy = "time", cascade = CascadeType.ALL)
	private Collection<RankingTime> rankingTimeList;

	@JsonIgnore
	@OneToMany(mappedBy = "time", cascade = CascadeType.ALL)
	private Collection<PlanilhaListaTime> planilhaListaTimeList;

	@ManyToMany(mappedBy = "time")
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

	public Time() {
	}

	public Time(String representa, Boolean junior, Collection<Competidor> competidores) {
		this.representa = representa;
		this.junior = junior;
		this.competidores = competidores;
	}

	public Time(String representa, Boolean junior, Collection<Competidor> competidores,
			Collection<Titulo> tituloList) {
		this.representa = representa;
		this.junior = junior;
		this.competidores = competidores;
		this.tituloList = tituloList;
	}

	public Integer getId() {
		return id;
	}

	public String getRepresenta() {
		return representa;
	}

	public Boolean getJunior() {
		return junior;
	}

	public Collection<Treinador> getTreinadorList() {
		return treinadorList;
	}

	public void setTreinadorList(Collection<Treinador> treinadorList) {
		this.treinadorList = treinadorList;
	}

	public Collection<Competidor> getCompetidores() {
		return competidores;
	}

	public void setCompetidores(Collection<Competidor> competidores) {
		this.competidores = competidores;
	}

	public Collection<RankingTime> getRankingTimeList() {
		return rankingTimeList;
	}

	public Collection<Titulo> getTituloList() {
		return tituloList;
	}

	public void setTituloList(Collection<Titulo> tituloList) {
		this.tituloList = tituloList;
	}
}
