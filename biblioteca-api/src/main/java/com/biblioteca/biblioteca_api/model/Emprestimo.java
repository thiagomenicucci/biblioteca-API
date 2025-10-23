package com.biblioteca.biblioteca_api.model;

import java.time.LocalDate;

public class Emprestimo {
    private String id;
    private String idMembro;
    private String idLivro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private boolean devolvido;

    public Emprestimo(String id, String idMembro, String idLivro) {
        this.id = id;
        this.idMembro = idMembro;
        this.idLivro = idLivro;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = LocalDate.now().plusDays(14); // 14 dias para devolução
        this.devolvido = false;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getIdMembro() {
        return idMembro;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    // Setters
    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
        this.devolvido = true;
    }

    public boolean isAtrasado() {
        return !devolvido && LocalDate.now().isAfter(dataDevolucaoPrevista);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
               "id='" + id + '\'' +
               ", idMembro='" + idMembro + '\'' +
               ", idLivro='" + idLivro + '\'' +
               ", dataEmprestimo=" + dataEmprestimo +
               ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
               ", dataDevolucaoReal=" + dataDevolucaoReal +
               ", devolvido=" + devolvido +
               '}';
    }
}

