package com.control.ata.model.torneio;

import com.control.ata.model.individual.PlanilhaChaveamentoIndividual;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RankingIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.time.PlanilhaChaveamentoTime;
import com.control.ata.model.time.PlanilhaListaTime;
import com.control.ata.model.time.RankingTime;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class CategoriaCompeticao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Boolean tipoChave;
    private Boolean tipoTime;
    private Integer limiteTempo;
    private Integer limitePonto;

    private Integer minimoMasculino;
    private Integer minimoFeminino;
    private Integer maximoTotal;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<PlanilhaListaIndividual> planilhaListaIndividualList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<PlanilhaListaTime> planilhaListaTimeList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<PlanilhaChaveamentoIndividual> planilhaChaveamentoIndividualList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<PlanilhaChaveamentoTime> planilhaChaveamentoTimeList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<RankingTime> rankingTimeList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<RankingIndividual> rankingIndividualList;

    @ManyToMany(mappedBy = "categoriaCompeticao")
    private Collection<Competidor> competidorList;

    @ManyToMany(mappedBy = "categoriaCompeticao")
    private Collection<RingueIndividual> ringueIndividualList;

    @ManyToMany(mappedBy = "categoriaCompeticao")
    private Collection<RingueTime> ringueTimeList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<Titulo> tituloList;

    public CategoriaCompeticao() {
    }

    public CategoriaCompeticao(String nome, Boolean tipoChave, Boolean tipoTime, Integer limiteTempo,
            Integer limitePonto, Integer minimoMasculino, Integer minimoFeminino, Integer maximoTotal) {
        this.nome = nome;
        this.tipoChave = tipoChave;
        this.tipoTime = tipoTime;
        this.limiteTempo = limiteTempo;
        this.limitePonto = limitePonto;
        this.minimoMasculino = minimoMasculino;
        this.minimoFeminino = minimoFeminino;
        this.maximoTotal = maximoTotal;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getTipoChave() {
        return tipoChave;
    }

    public Boolean getTipoTime() {
        return tipoTime;
    }

    public Integer getLimiteTempo() {
        return limiteTempo;
    }

    public Integer getLimitePonto() {
        return limitePonto;
    }

    public Integer getMinimoMasculino() {
        return minimoMasculino;
    }

    public Integer getMinimoFeminino() {
        return minimoFeminino;
    }

    public Integer getMaximoTotal() {
        return maximoTotal;
    }
}