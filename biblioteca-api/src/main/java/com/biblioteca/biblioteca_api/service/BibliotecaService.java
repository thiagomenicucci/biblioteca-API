package com.biblioteca.biblioteca_api.service;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Membro;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.MembroRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serviço principal que contém a lógica de negócio da biblioteca.
 * Coordena as operações entre os diferentes repositórios.
 */
public class BibliotecaService {
    private LivroRepository livroRepository;
    private MembroRepository membroRepository;
    private EmprestimoRepository emprestimoRepository;

    public BibliotecaService() {
        this.livroRepository = new LivroRepository();
        this.membroRepository = new MembroRepository();
        this.emprestimoRepository = new EmprestimoRepository();
    }

    // ===== OPERAÇÕES DE LIVROS =====

    /**
     * Adiciona um novo livro à biblioteca
     */
    public void adicionarLivro(String id, String titulo, String autor, String isbn) {
        Livro livro = new Livro(id, titulo, autor, isbn);
        livroRepository.adicionarLivro(livro);
    }

    /**
     * Remove um livro da biblioteca
     */
    public boolean removerLivro(String id) {
        // Verifica se o livro está emprestado
        Optional<Emprestimo> emprestimoAtivo = emprestimoRepository.buscarEmprestimoAtivoPorLivro(id);
        if (emprestimoAtivo.isPresent()) {
            return false; // Não pode remover livro emprestado
        }
        return livroRepository.removerLivro(id);
    }

    /**
     * Lista todos os livros
     */
    public List<Livro> listarLivros() {
        return livroRepository.listarTodos();
    }

    /**
     * Lista apenas livros disponíveis
     */
    public List<Livro> listarLivrosDisponiveis() {
        return livroRepository.listarDisponiveis();
    }

    /**
     * Busca livros por título
     */
    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return livroRepository.buscarPorTitulo(titulo);
    }

    /**
     * Busca livros por autor
     */
    public List<Livro> buscarLivrosPorAutor(String autor) {
        return livroRepository.buscarPorAutor(autor);
    }

    // ===== OPERAÇÕES DE MEMBROS =====

    /**
     * Registra um novo membro na biblioteca
     */
    public boolean registrarMembro(String id, String nome, String email, String telefone) {
        // Verifica se o email já existe
        if (membroRepository.emailJaExiste(email)) {
            return false;
        }
        Membro membro = new Membro(id, nome, email, telefone);
        membroRepository.adicionarMembro(membro);
        return true;
    }

    /**
     * Remove um membro da biblioteca
     */
    public boolean removerMembro(String id) {
        // Verifica se o membro tem empréstimos ativos
        List<Emprestimo> emprestimosAtivos = emprestimoRepository.buscarPorMembro(id)
                .stream()
                .filter(emprestimo -> !emprestimo.isDevolvido())
                .toList();
        
        if (!emprestimosAtivos.isEmpty()) {
            return false; // Não pode remover membro com empréstimos ativos
        }
        return membroRepository.removerMembro(id);
    }

    /**
     * Lista todos os membros
     */
    public List<Membro> listarMembros() {
        return membroRepository.listarTodos();
    }

    /**
     * Busca membros por nome
     */
    public List<Membro> buscarMembrosPorNome(String nome) {
        return membroRepository.buscarPorNome(nome);
    }

    // ===== OPERAÇÕES DE EMPRÉSTIMO =====

    /**
     * Realiza um empréstimo de livro
     */
    public String emprestarLivro(String idMembro, String idLivro) {
        // Verifica se o membro existe
        Optional<Membro> membro = membroRepository.buscarPorId(idMembro);
        if (membro.isEmpty()) {
            return "Membro não encontrado.";
        }

        // Verifica se o livro existe
        Optional<Livro> livro = livroRepository.buscarPorId(idLivro);
        if (livro.isEmpty()) {
            return "Livro não encontrado.";
        }

        // Verifica se o livro está disponível
        if (!livro.get().isDisponivel()) {
            return "Livro não está disponível para empréstimo.";
        }

        // Verifica se o membro já tem empréstimos atrasados
        List<Emprestimo> emprestimosAtrasados = emprestimoRepository.buscarPorMembro(idMembro)
                .stream()
                .filter(Emprestimo::isAtrasado)
                .toList();
        
        if (!emprestimosAtrasados.isEmpty()) {
            return "Membro possui empréstimos em atraso. Regularize a situação antes de fazer novos empréstimos.";
        }

        // Realiza o empréstimo
        String idEmprestimo = "E" + System.currentTimeMillis();
        Emprestimo emprestimo = new Emprestimo(idEmprestimo, idMembro, idLivro);
        emprestimoRepository.adicionarEmprestimo(emprestimo);
        livro.get().setDisponivel(false);

        return "Empréstimo realizado com sucesso! ID: " + idEmprestimo;
    }

    /**
     * Realiza a devolução de um livro
     */
    public String devolverLivro(String idEmprestimo) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.buscarPorId(idEmprestimo);
        if (emprestimo.isEmpty()) {
            return "Empréstimo não encontrado.";
        }

        if (emprestimo.get().isDevolvido()) {
            return "Este empréstimo já foi devolvido.";
        }

        // Realiza a devolução
        emprestimo.get().setDataDevolucaoReal(LocalDate.now());
        
        // Marca o livro como disponível novamente
        Optional<Livro> livro = livroRepository.buscarPorId(emprestimo.get().getIdLivro());
        if (livro.isPresent()) {
            livro.get().setDisponivel(true);
        }

        return "Devolução realizada com sucesso!";
    }

    /**
     * Lista todos os empréstimos ativos
     */
    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoRepository.listarAtivos();
    }

    /**
     * Lista todos os empréstimos atrasados
     */
    public List<Emprestimo> listarEmprestimosAtrasados() {
        return emprestimoRepository.listarAtrasados();
    }

    /**
     * Lista empréstimos de um membro específico
     */
    public List<Emprestimo> listarEmprestimosPorMembro(String idMembro) {
        return emprestimoRepository.buscarPorMembro(idMembro);
    }

    // ===== RELATÓRIOS =====

    /**
     * Gera relatório geral da biblioteca
     */
    public String gerarRelatorioGeral() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO GERAL DA BIBLIOTECA ===\n");
        relatorio.append("Total de livros: ").append(livroRepository.getTotalLivros()).append("\n");
        relatorio.append("Livros disponíveis: ").append(livroRepository.listarDisponiveis().size()).append("\n");
        relatorio.append("Total de membros: ").append(membroRepository.getTotalMembros()).append("\n");
        relatorio.append("Empréstimos ativos: ").append(emprestimoRepository.getTotalEmprestimosAtivos()).append("\n");
        relatorio.append("Empréstimos atrasados: ").append(emprestimoRepository.listarAtrasados().size()).append("\n");
        return relatorio.toString();
    }
}

