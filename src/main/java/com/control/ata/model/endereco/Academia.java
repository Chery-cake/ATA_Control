package com.control.ata.model.endereco;

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
	private Endereco endereco;

	@JsonIgnore
	@OneToMany(mappedBy = "academia", cascade = CascadeType.ALL)
	private Collection<Instrutor> instrutores;

	public Academia() {
	}

	public Academia(String nome, Endereco endereco) {
		this.nome = nome;
		this.endereco = endereco;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public Collection<Instrutor> getInstrutores() {
		return instrutores;
	}
}
