package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.torneio.CategoriaCompeticao;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class ListaCategoriaCompetidorFechada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Competidor competidor;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<CategoriaCompeticao> categoriaCompeticao;

    public ListaCategoriaCompetidorFechada() {
    }

    public ListaCategoriaCompetidorFechada(Competidor competidor,
            Collection<CategoriaCompeticao> categoriaCompeticao) {
        this.competidor = competidor;
        this.categoriaCompeticao = categoriaCompeticao;
    }

    public ListaCategoriaCompetidorFechada(Competidor competidor) {
        this.competidor = competidor;
    }

    public Integer getId() {
        return id;
    }

    public Competidor getCompetidor() {
        return competidor;
    }

    public Collection<CategoriaCompeticao> getCategoriaCompeticao() {
        return categoriaCompeticao;
    }

    public void setCategoriaCompeticao(
            Collection<CategoriaCompeticao> categoriaCompeticao) {
        this.categoriaCompeticao = categoriaCompeticao;
    }
}
