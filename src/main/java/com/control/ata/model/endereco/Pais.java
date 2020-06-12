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
	@OneToMany(mappedBy = "pais")
	private Collection<Estado> estados;

	public Pais() {
	}

	public Pais(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Pais{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", estados=" + estados +
				'}';
	}
}
