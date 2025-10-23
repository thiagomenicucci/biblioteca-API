package com.biblioteca.biblioteca_api.model;

import java.time.LocalDate;

public class Membro {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataRegistro;

    public Membro(String id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataRegistro = LocalDate.now();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Membro{" +
               "id='" + id + '\'' +
               ", nome='" + nome + '\'' +
               ", email='" + email + '\'' +
               ", telefone='" + telefone + '\'' +
               ", dataRegistro=" + dataRegistro +
               '}';
    }
}

