package com.control.ata.model.pessoa;

import javax.persistence.*;

@Entity
public class Faixa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@OneToOne(mappedBy = "faixa")
	private Pessoa pessoa;

}
