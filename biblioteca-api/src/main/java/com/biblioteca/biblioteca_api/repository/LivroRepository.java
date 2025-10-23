package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.model.Livro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelo gerenciamento de dados dos livros.
 * Simula um banco de dados em memória para fins educacionais.
 */
public class LivroRepository {
    private List<Livro> livros;

    public LivroRepository() {
        this.livros = new ArrayList<>();
        inicializarDadosExemplo();
    }

    /**
     * Adiciona alguns livros de exemplo para demonstração
     */
    private void inicializarDadosExemplo() {
        adicionarLivro(new Livro("L001", "O Senhor dos Anéis", "J.R.R. Tolkien", "978-0544003415"));
        adicionarLivro(new Livro("L002", "1984", "George Orwell", "978-0451524935"));
        adicionarLivro(new Livro("L003", "Dom Casmurro", "Machado de Assis", "978-8525406958"));
        adicionarLivro(new Livro("L004", "Clean Code", "Robert C. Martin", "978-0132350884"));
        adicionarLivro(new Livro("L005", "Java: The Complete Reference", "Herbert Schildt", "978-1260440232"));
    }

    /**
     * Adiciona um novo livro ao repositório
     */
    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    /**
     * Remove um livro do repositório pelo ID
     */
    public boolean removerLivro(String id) {
        return livros.removeIf(livro -> livro.getId().equals(id));
    }

    /**
     * Busca um livro pelo ID
     */
    public Optional<Livro> buscarPorId(String id) {
        return livros.stream()
                .filter(livro -> livro.getId().equals(id))
                .findFirst();
    }

    /**
     * Busca livros por título (busca parcial, case-insensitive)
     */
    public List<Livro> buscarPorTitulo(String titulo) {
        return livros.stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    /**
     * Busca livros por autor (busca parcial, case-insensitive)
     */
    public List<Livro> buscarPorAutor(String autor) {
        return livros.stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    /**
     * Retorna todos os livros
     */
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros);
    }

    /**
     * Retorna apenas os livros disponíveis
     */
    public List<Livro> listarDisponiveis() {
        return livros.stream()
                .filter(Livro::isDisponivel)
                .toList();
    }

    /**
     * Retorna o total de livros no repositório
     */
    public int getTotalLivros() {
        return livros.size();
    }
}

