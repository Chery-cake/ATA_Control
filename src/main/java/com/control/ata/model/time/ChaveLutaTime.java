package com.control.ata.model.time;

import javax.persistence.*;

@Entity
public class ChaveLutaTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "timeVermelho_fk")
	private Time timeVermelho;

	private Integer pontosTotaisVermelhos;

	private Integer advertenciasVermelhas;

	private Integer penalidadesVermelhas;

	private Boolean desqualificacaoVermelha;

	@ManyToOne
	@JoinColumn(name = "timeBranco_fk")
	private Time timeBranco;

	private Integer pontosTotaisBrancos;

	private Integer advertenciasBrancas;

	private Integer penalidadesBrancas;

	private Boolean desqualificacaoBranca;

	@ManyToOne
	@JoinColumn(name = "planilhaChaveamentoTime_fk")
	private PlanilhaChaveamentoTime planilhaChaveamentoTime;

}
