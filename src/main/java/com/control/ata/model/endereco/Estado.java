package com.control.ata.model.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "pais_fk")
	private Pais pais;

	@JsonIgnore
	@OneToMany(mappedBy = "estado")
	private Collection<Cidade> cidades;

	public Estado() {
	}

	public Estado(String nome, Pais pais) {
		this.nome = nome;
		this.pais = pais;
	}

	public String getNome() {
		return nome;
	}

	public Pais getPais() {
		return pais;
	}
}
