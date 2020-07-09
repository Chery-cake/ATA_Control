package com.control.ata.model.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "pais", cascade = CascadeType.ALL)
	private Collection<Estado> estados;

	public Pais() {
	}

	public Pais(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
