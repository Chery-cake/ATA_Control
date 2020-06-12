package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.pessoa.Pessoa;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Administrador extends Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

}
