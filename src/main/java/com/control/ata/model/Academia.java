package com.control.ata.model;

import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Academia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "endereco_fk")
	private Endereco endereco;

	@JsonIgnore
	@OneToMany(mappedBy = "academia")
	private Collection<Instrutor> instrutores;

	public Academia() {
	}

	public Academia(String nome, Endereco endereco) {
		this.nome = nome;
		this.endereco = endereco;
	}

}
