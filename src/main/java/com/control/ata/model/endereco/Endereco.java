package com.control.ata.model.endereco;

import com.control.ata.model.Academia;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.torneio.Torneio;

import javax.persistence.*;

@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String rua;

	@ManyToOne
	@JoinColumn(name = "bairro_fk")
	private Bairro bairro;

	@OneToOne(mappedBy = "endereco")
	private Academia academia;

	@OneToOne(mappedBy = "endereco")
	private Torneio torneio;

	@OneToOne(mappedBy = "endereco")
	private Pessoa pessoa;

	public Endereco() {
	}

	public Endereco(String rua, Bairro bairro) {
		this.rua = rua;
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	@Override
	public String toString() {
		return "Endereco{" +
				"id=" + id +
				", rua='" + rua + '\'' +
				", bairro=" + bairro +
				", academia=" + academia +
				", torneio=" + torneio +
				", pessoa=" + pessoa +
				'}';
	}
}
