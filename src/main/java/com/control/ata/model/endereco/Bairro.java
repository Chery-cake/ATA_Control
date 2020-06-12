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
	@JoinColumn(name = "cidade_fk")
	private Cidade cidade;

	@JsonIgnore
	@OneToMany(mappedBy = "bairro")
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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return "Bairro{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", cidade=" + cidade +
				", enderecos=" + enderecos +
				'}';
	}
}
