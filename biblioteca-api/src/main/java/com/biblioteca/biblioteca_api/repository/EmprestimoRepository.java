package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.model.Emprestimo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelo gerenciamento de dados dos empréstimos.
 * Simula um banco de dados em memória para fins educacionais.
 */
public class EmprestimoRepository {
    private List<Emprestimo> emprestimos;

    public EmprestimoRepository() {
        this.emprestimos = new ArrayList<>();
    }

    /**
     * Adiciona um novo empréstimo ao repositório
     */
    public void adicionarEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

    /**
     * Busca um empréstimo pelo ID
     */
    public Optional<Emprestimo> buscarPorId(String id) {
        return emprestimos.stream()
                .filter(emprestimo -> emprestimo.getId().equals(id))
                .findFirst();
    }

    /**
     * Busca empréstimos por ID do membro
     */
    public List<Emprestimo> buscarPorMembro(String idMembro) {
        return emprestimos.stream()
                .filter(emprestimo -> emprestimo.getIdMembro().equals(idMembro))
                .toList();
    }

    /**
     * Busca empréstimos por ID do livro
     */
    public List<Emprestimo> buscarPorLivro(String idLivro) {
        return emprestimos.stream()
                .filter(emprestimo -> emprestimo.getIdLivro().equals(idLivro))
                .toList();
    }

    /**
     * Retorna todos os empréstimos
     */
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }

    /**
     * Retorna empréstimos ativos (não devolvidos)
     */
    public List<Emprestimo> listarAtivos() {
        return emprestimos.stream()
                .filter(emprestimo -> !emprestimo.isDevolvido())
                .toList();
    }

    /**
     * Retorna empréstimos atrasados
     */
    public List<Emprestimo> listarAtrasados() {
        return emprestimos.stream()
                .filter(Emprestimo::isAtrasado)
                .toList();
    }

    /**
     * Busca empréstimo ativo de um livro específico
     */
    public Optional<Emprestimo> buscarEmprestimoAtivoPorLivro(String idLivro) {
        return emprestimos.stream()
                .filter(emprestimo -> emprestimo.getIdLivro().equals(idLivro) && !emprestimo.isDevolvido())
                .findFirst();
    }

    /**
     * Retorna o total de empréstimos no repositório
     */
    public int getTotalEmprestimos() {
        return emprestimos.size();
    }

    /**
     * Retorna o total de empréstimos ativos
     */
    public int getTotalEmprestimosAtivos() {
        return (int) emprestimos.stream()
                .filter(emprestimo -> !emprestimo.isDevolvido())
                .count();
    }
}

