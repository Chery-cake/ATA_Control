package com.control.ata.model.endereco;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.torneio.Torneio;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rua;

    @ManyToOne
    private Bairro bairro;

    @JsonIgnore
    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private Collection<Pessoa> pessoa;

    @JsonIgnore
    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private Collection<Academia> academia;

    @JsonIgnore
    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private Collection<Torneio> torneio;

    public Endereco() {
    }

    public Endereco(String rua, Bairro bairro) {
        this.rua = rua;
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public Bairro getBairro() {
        return bairro;
    }
}
