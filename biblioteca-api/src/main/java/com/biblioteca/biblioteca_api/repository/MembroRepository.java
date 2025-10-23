package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.model.Membro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelo gerenciamento de dados dos membros.
 * Simula um banco de dados em memória para fins educacionais.
 */
public class MembroRepository {
    private List<Membro> membros;

    public MembroRepository() {
        this.membros = new ArrayList<>();
        inicializarDadosExemplo();
    }

    /**
     * Adiciona alguns membros de exemplo para demonstração
     */
    private void inicializarDadosExemplo() {
        adicionarMembro(new Membro("M001", "João Silva", "joao.silva@email.com", "(11) 99999-1111"));
        adicionarMembro(new Membro("M002", "Maria Santos", "maria.santos@email.com", "(11) 99999-2222"));
        adicionarMembro(new Membro("M003", "Pedro Oliveira", "pedro.oliveira@email.com", "(11) 99999-3333"));
    }

    /**
     * Adiciona um novo membro ao repositório
     */
    public void adicionarMembro(Membro membro) {
        membros.add(membro);
    }

    /**
     * Remove um membro do repositório pelo ID
     */
    public boolean removerMembro(String id) {
        return membros.removeIf(membro -> membro.getId().equals(id));
    }

    /**
     * Busca um membro pelo ID
     */
    public Optional<Membro> buscarPorId(String id) {
        return membros.stream()
                .filter(membro -> membro.getId().equals(id))
                .findFirst();
    }

    /**
     * Busca membros por nome (busca parcial, case-insensitive)
     */
    public List<Membro> buscarPorNome(String nome) {
        return membros.stream()
                .filter(membro -> membro.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }

    /**
     * Busca um membro por email
     */
    public Optional<Membro> buscarPorEmail(String email) {
        return membros.stream()
                .filter(membro -> membro.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * Retorna todos os membros
     */
    public List<Membro> listarTodos() {
        return new ArrayList<>(membros);
    }

    /**
     * Verifica se um email já está em uso
     */
    public boolean emailJaExiste(String email) {
        return membros.stream()
                .anyMatch(membro -> membro.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Retorna o total de membros no repositório
     */
    public int getTotalMembros() {
        return membros.size();
    }
}

