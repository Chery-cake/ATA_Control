package com.control.ata.model.torneio;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RankingIndividual;
import com.control.ata.model.time.PlanilhaChaveamentoTime;
import com.control.ata.model.time.PlanilhaListaTime;
import com.control.ata.model.time.RankingTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class CategoriaCompeticao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	private Boolean tipoChave;

	private Boolean tipoTime;

	private Integer limiteTempo;

	private Integer limitePonto;

	private Integer minimoMasculino;

	private Integer minimoFeminino;

	private Integer maximoTotal;

	@JsonIgnore
	@OneToMany(mappedBy = "categoriaCompeticao")
	private Collection<PlanilhaListaIndividual> planilhaListaIndividualList;

	@JsonIgnore
	@OneToMany(mappedBy = "categoriaCompeticao")
	private Collection<PlanilhaListaTime> planilhaListaTimeList;

	@JsonIgnore
	@OneToMany(mappedBy = "categoriaCompeticao")
	private Collection<PlanilhaChaveamentoIndividual> planilhaChaveamentoIndividualList;

	@JsonIgnore
	@OneToMany(mappedBy = "categoriaCompeticao")
	private Collection<PlanilhaChaveamentoTime> planilhaChaveamentoTimeList;

	@JsonIgnore
	@OneToMany(mappedBy = "categoriaCompeticao")
	private Collection<RankingTime> rankingTimeList;

	@JsonIgnore
	@OneToMany(mappedBy = "categoriaCompeticao")
	private Collection<RankingIndividual> rankingIndividualList;

	@ManyToMany(mappedBy = "categoriaCompeticao")
	private Collection<Competidor> competidorList;

}