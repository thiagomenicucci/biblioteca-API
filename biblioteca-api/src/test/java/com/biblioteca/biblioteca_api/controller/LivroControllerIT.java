/*
 * Caminho do arquivo: src/test/java/com/biblioteca/controller/LivroControllerIT.java
 */
package com.biblioteca.biblioteca_api.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Anotações-chave para Testes de Integração:
 *
 * 1. @SpringBootTest: Sobe a aplicação Spring completa (contexto inteiro).
 * 2. @AutoConfigureMockMvc: Configura o MockMvc para fazer chamadas HTTP fakes.
 * 3. @ActiveProfiles("test"): Obriga o Spring a usar o 'application-test.properties' (banco H2).
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LivroControllerIT { // IT = Integration Test

    @Autowired
    private MockMvc mockMvc; // Objeto para simular requisições HTTP

    @Autowired
    private LivroRepository livroRepository; // Injetamos o repositório real

    @Autowired
    private ObjectMapper objectMapper; // Usado para converter Objetos Java para JSON

    // Este método roda ANTES de CADA teste
    @BeforeEach
    void setUp() {
        // Limpa o banco H2 em memória para garantir que um teste não interfira no outro
        livroRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um novo livro com sucesso (POST /livros)")
    void deveCriarNovoLivro() throws Exception {
        // Arrange (Arrumar)
        Livro novoLivro = new Livro();
        novoLivro.setTitulo("Código Limpo");
        novoLivro.setDisponivel(true);
        // Não precisamos setar Autor aqui para um teste simples de criação

        // Act (Agir) & Assert (Afirmar)
        mockMvc.perform(post("/livros") // Simula um POST para /livros
                        .contentType(MediaType.APPLICATION_JSON) // Diz que estamos enviando JSON
                        .content(objectMapper.writeValueAsString(novoLivro))) // Converte o objeto para JSON
                .andExpect(status().isCreated()) // Espera que o status HTTP seja 201 Created
                .andExpect(jsonPath("$.id", is(notNullValue()))) // Espera que a resposta JSON tenha um ID
                .andExpect(jsonPath("$.titulo", is("Código Limpo"))); // Espera que a resposta tenha o título
    }

    @Test
    @DisplayName("Deve listar todos os livros (GET /livros)")
    void deveListarTodosLivros() throws Exception {
        // Arrange (Arruma o banco de dados)
        livroRepository.save(new Livro("Java Efetivo", true));
        livroRepository.save(new Livro("O Programador Pragmático", true));

        // Act (Agir) & Assert (Afirmar)
        mockMvc.perform(get("/livros") // Simula um GET para /livros
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera HTTP 200 OK
                .andExpect(jsonPath("$", hasSize(2))) // Espera que a resposta seja uma lista de 2 elementos
                .andExpect(jsonPath("$[0].titulo", is("Java Efetivo")))
                .andExpect(jsonPath("$[1].titulo", is("O Programador Pragmático")));
    }
}