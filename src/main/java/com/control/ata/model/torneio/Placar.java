package com.control.ata.model.torneio;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.time.RingueTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Placar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonIgnore
	@OneToMany(mappedBy = "placar", cascade = CascadeType.ALL)
	private Collection<Cronometro> cronometroList;

	//private Cronometro cronometro;

	@JsonIgnore
	@OneToMany(mappedBy = "placar", cascade = CascadeType.ALL)
	private Collection<RingueIndividual> ringueIndividualList;

	//private RingueIndividual ringueIndividual;

	@JsonIgnore
	@OneToMany(mappedBy = "placar", cascade = CascadeType.ALL)
	private Collection<RingueTime> ringueTimeList;

	//private RingueTime ringueTime;

}
