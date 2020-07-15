package com.control.ata.model.torneio;

import com.control.ata.model.time.Time;
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
    private CategoriaTitulo categoriaTitulo;

    @ManyToOne
    private Competidor competidor;

    @ManyToOne
    private Time time;

    public Titulo() {
    }

    public Titulo(Integer ano, CategoriaCompeticao categoriaCompeticao,
            CategoriaTitulo categoriaTitulo, Competidor competidor) {
        this.ano = ano;
        this.categoriaCompeticao = categoriaCompeticao;
        this.categoriaTitulo = categoriaTitulo;
        this.competidor = competidor;
    }

    public Titulo(Integer ano, CategoriaCompeticao categoriaCompeticao,
            CategoriaTitulo categoriaTitulo, Time time) {
        this.ano = ano;
        this.categoriaCompeticao = categoriaCompeticao;
        this.categoriaTitulo = categoriaTitulo;
        this.time = time;
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

    public CategoriaTitulo getCategoriaTitulo() {
        return categoriaTitulo;
    }

    public Competidor getCompetidor() {
        return competidor;
    }

    public void setCompetidor(Competidor competidor) {
        this.competidor = competidor;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
