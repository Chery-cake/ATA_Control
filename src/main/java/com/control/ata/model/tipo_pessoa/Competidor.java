package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.individual.ChaveLutaIndividual;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RankingIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.Time;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Competidor extends Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer peso;

	private Integer altura;

	private String nivel;

	private Boolean isInstrutor;

	@ManyToOne
	@JoinColumn(name = "instrutor_fk")
	private Instrutor instrutor;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Collection<CategoriaCompeticao> categoriaCompeticao;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Collection<Time> time;

	@JsonIgnore
	@OneToMany(mappedBy = "competidor")
	private Collection<PlanilhaListaIndividual> planilhaListaIndividualList;

	@JsonIgnore
	@OneToMany(mappedBy = "competidor")
	private Collection<RankingIndividual> rankingIndividualList;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Collection<RingueIndividual> ringueIndividualCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "competidorVermelho")
	private Collection<ChaveLutaIndividual> chaveLutaIndividualVermelho;

	@JsonIgnore
	@OneToMany(mappedBy = "competidorBranco")
	private Collection<ChaveLutaIndividual> chaveLutaIndividualBranco;

}
