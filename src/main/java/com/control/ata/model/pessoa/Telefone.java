package com.control.ata.model.pessoa;

import javax.persistence.*;

@Entity
public class Telefone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String telefone;

	private Boolean Celular;

	@ManyToOne
	@JoinColumn(name = "pessoa_fk")
	private Pessoa pessoa;

}
