package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.Cronometro;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.Placar;
import com.control.ata.model.torneio.Torneio;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class RingueIndividual {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Boolean fechado;

	private Integer numero;

	@ManyToMany(mappedBy = "ringueIndividualCollection")
	private Collection<Competidor> competidor;

	@ManyToMany(mappedBy = "ringueIndividualCollection")
	private Collection<Juiz> juiz;

	@ManyToOne
	@JoinColumn(name = "torneio_fk")
	private Torneio torneio;

	//private Collection<Torneio> torneio;

	@JsonIgnore
	@OneToMany(mappedBy = "ringueIndividual")
	private Collection<PlanilhaListaIndividual> planilhaListaIndividual;

	@JsonIgnore
	@OneToMany(mappedBy = "ringueIndividual")
	private Collection<PlanilhaChaveamentoIndividual> planilhaChaveamentoIndividual;

	@OneToOne(mappedBy = "ringueIndividual")
	private Cronometro cronometro;

	@ManyToOne
	@JoinColumn(name = "placar_fk")
	private Placar placar;

}
