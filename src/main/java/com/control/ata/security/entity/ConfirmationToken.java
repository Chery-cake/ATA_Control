package com.control.ata.security.entity;

import com.control.ata.model.pessoa.Pessoa;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String confirmationToken;
    private LocalDate createdDate;

    @OneToOne(targetEntity = Pessoa.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Pessoa pessoa;

    public ConfirmationToken() {
    }

    public ConfirmationToken(Pessoa pessoa) {
        this.pessoa = pessoa;
        this.createdDate = LocalDate.now();
        this.confirmationToken = UUID.randomUUID().toString();
    }

    public Integer getId() {
        return id;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
