package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;

import javax.persistence.*;

@Entity
public class ChaveLutaIndividual {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "competidorVermelho_fk")
	private Competidor competidorVermelho;

	private Integer pontosVermelhos;

	private Integer advertenciasVermelhas;

	private Integer penalidadesVermelhas;

	private Boolean desqualificacaoVermelha;

	@ManyToOne
	@JoinColumn(name = "competidorBranco_fk")
	private Competidor competidorBranco;

	private Integer pontosBrancos;

	private Integer advertenciasBrancas;

	private Integer penalidadesBrancas;

	private Boolean desqualificacaoBranca;

	@ManyToOne
	@JoinColumn(name = "planilhaChaveamentoIndividual_fk")
	private PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual;

}
