package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.Time;

import javax.persistence.*;

@Entity
public class Treinador extends Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "cidade_fk")
	private Time time;

}
