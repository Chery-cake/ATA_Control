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
	@JoinColumn(name = "estado_fk")
	private Estado estado;

	@JsonIgnore
	@OneToMany(mappedBy = "cidade")
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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Cidade{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", estado=" + estado +
				", bairros=" + bairros +
				'}';
	}
}
