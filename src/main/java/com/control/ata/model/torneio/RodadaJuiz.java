package com.control.ata.model.torneio;

import com.control.ata.model.tipo_pessoa.Juiz;

import javax.persistence.*;

@Entity
public class RodadaJuiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "torneio_fk")
	private Torneio torneio;

	@ManyToOne
	@JoinColumn(name = "juiz_fk")
	private Juiz juiz;

}
