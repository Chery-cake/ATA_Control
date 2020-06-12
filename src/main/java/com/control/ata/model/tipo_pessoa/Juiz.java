package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.torneio.RodadaJuiz;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Juiz extends Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonIgnore
	@OneToMany(mappedBy = "juiz")
	private Collection<RodadaJuiz> rodadaJuizList;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Collection<RingueIndividual> ringueIndividualCollection;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Collection<RingueTime> ringueTimeCollection;

}
