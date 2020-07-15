package com.control.ata.model.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@ManyToOne
	private Estado estado;

	@JsonIgnore
	@OneToMany(mappedBy = "cidade", cascade = CascadeType.ALL)
	private Collection<Bairro> bairros;

	public Cidade() {
	}

	public Cidade(String nome, Estado estado) {
		this.nome = nome;
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public Estado getEstado() {
		return estado;
	}
}
