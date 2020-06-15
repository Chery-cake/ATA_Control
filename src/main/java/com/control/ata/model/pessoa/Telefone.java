package com.control.ata.model.pessoa;

import javax.persistence.*;

@Entity
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String telefone;

    private Boolean Celular;

    @ManyToOne
    @JoinColumn(name = "pessoa_fk")
    private Pessoa pessoa;

    public Telefone() {
    }

    public Telefone(String telefone, Boolean celular, Pessoa pessoa) {
        this.telefone = telefone;
        Celular = celular;
        this.pessoa = pessoa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getCelular() {
        return Celular;
    }

    public void setCelular(Boolean celular) {
        Celular = celular;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "id=" + id +
                ", telefone='" + telefone + '\'' +
                ", Celular=" + Celular +
                ", pessoa=" + pessoa +
                '}';
    }
}
