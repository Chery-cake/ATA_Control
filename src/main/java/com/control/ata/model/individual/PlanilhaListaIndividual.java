package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;

import javax.persistence.*;

@Entity
public class PlanilhaListaIndividual {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "competidor_fk")
	private Competidor competidor;

	private Integer notaJuizA;

	private Integer notaJuizB;

	private Integer notaJuizC;

	@ManyToOne
	@JoinColumn(name = "categoriaCompeticao_fk")
	private CategoriaCompeticao categoriaCompeticao;

	@ManyToOne
	@JoinColumn(name = "ringueIndividual_fk")
	private RingueIndividual ringueIndividual;

}
