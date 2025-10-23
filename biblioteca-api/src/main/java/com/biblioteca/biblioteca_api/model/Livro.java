package com.biblioteca.biblioteca_api.model;

public class Livro {
    private String id;
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponivel;

    public Livro(String id, String titulo, String autor, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    // Setters
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Livro{" +
               "id='" + id + '\'' +
               ", titulo='" + titulo + '\'' +
               ", autor='" + autor + '\'' +
               ", isbn='" + isbn + '\'' +
               ", disponivel=" + disponivel +
               '}';
    }
}

