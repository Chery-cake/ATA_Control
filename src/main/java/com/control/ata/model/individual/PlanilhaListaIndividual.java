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

	public PlanilhaListaIndividual() {
	}

	public PlanilhaListaIndividual(Competidor competidor,
			CategoriaCompeticao categoriaCompeticao, RingueIndividual ringueIndividual) {
		this.competidor = competidor;
		this.categoriaCompeticao = categoriaCompeticao;
		this.ringueIndividual = ringueIndividual;
	}

	public Competidor getCompetidor() {
		return competidor;
	}

	public Integer getNotaJuizA() {
		return notaJuizA;
	}

	public void setNotaJuizA(Integer notaJuizA) {
		this.notaJuizA = notaJuizA;
	}

	public Integer getNotaJuizB() {
		return notaJuizB;
	}

	public void setNotaJuizB(Integer notaJuizB) {
		this.notaJuizB = notaJuizB;
	}

	public Integer getNotaJuizC() {
		return notaJuizC;
	}

	public void setNotaJuizC(Integer notaJuizC) {
		this.notaJuizC = notaJuizC;
	}

	public CategoriaCompeticao getCategoriaCompeticao() {
		return categoriaCompeticao;
	}

	public RingueIndividual getRingueIndividual() {
		return ringueIndividual;
	}
}
