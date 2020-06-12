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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Estado{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", pais=" + pais +
				", cidades=" + cidades +
				'}';
	}
}
