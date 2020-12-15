package com.control.ata.model.torneio;

import com.control.ata.model.tipo_pessoa.Competidor;

import javax.persistence.*;

@Entity
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer ano;

    @ManyToOne
    private CategoriaCompeticao categoriaCompeticao;

    @ManyToOne
    private CategoriaTorneio categoriaTorneio;

    @ManyToOne
    private Competidor competidor;

    public Titulo() {
    }

    public Titulo(Integer ano, CategoriaCompeticao categoriaCompeticao,
            CategoriaTorneio categoriaTorneio, Competidor competidor) {
        this.ano = ano;
        this.categoriaCompeticao = categoriaCompeticao;
        this.categoriaTorneio = categoriaTorneio;
        this.competidor = competidor;
    }

    public Titulo(Integer ano, CategoriaCompeticao categoriaCompeticao,
            CategoriaTorneio categoriaTorneio) {
        this.ano = ano;
        this.categoriaCompeticao = categoriaCompeticao;
        this.categoriaTorneio = categoriaTorneio;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAno() {
        return ano;
    }

    public CategoriaCompeticao getCategoriaCompeticao() {
        return categoriaCompeticao;
    }

    public CategoriaTorneio getCategoriaTorneio() {
        return categoriaTorneio;
    }

    public Competidor getCompetidor() {
        return competidor;
    }

    public void setCompetidor(Competidor competidor) {
        this.competidor = competidor;
    }
}
