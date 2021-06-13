package com.control.ata.security.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String confirmationToken;

    @OneToOne(fetch = FetchType.EAGER)
    private Usuario user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(Usuario user) {
        this.user = user;
        this.confirmationToken = UUID.randomUUID().toString();
    }

    public Integer getId() {
        return id;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Usuario getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ConfirmationToken{" +
                "id=" + id +
                ", confirmationToken='" + confirmationToken + '\'' +
                ", user=" + user +
                '}';
    }
}
