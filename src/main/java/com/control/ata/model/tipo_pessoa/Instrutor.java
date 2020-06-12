package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.Academia;
import com.control.ata.model.pessoa.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Instrutor extends Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "academia_fk")
	private Academia academia;

	@JsonIgnore
	@OneToMany(mappedBy = "instrutor")
	private Collection<Competidor> competidores;

}
