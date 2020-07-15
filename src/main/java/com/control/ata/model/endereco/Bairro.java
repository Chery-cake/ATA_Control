package com.control.ata.model.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Bairro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@ManyToOne
	private Cidade cidade;

	@JsonIgnore
	@OneToMany(mappedBy = "bairro", cascade = CascadeType.ALL)
	private Collection<Endereco> enderecos;

	public Bairro() {
	}

	public Bairro(String nome, Cidade cidade) {
		this.nome = nome;
		this.cidade = cidade;
	}

	public String getNome() {
		return nome;
	}

	public Cidade getCidade() {
		return cidade;
	}
}
