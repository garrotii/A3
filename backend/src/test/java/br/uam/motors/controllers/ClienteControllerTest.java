package br.uam.motors.controllers;

import br.uam.motors.models.Cliente;
import br.uam.motors.models.ClienteDTO;
import br.uam.motors.repositories.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // garante que os dados sejam rolados após cada teste
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper; //Para converter objetos Java para JSON

    @BeforeEach
    void setUp() {
        // Limpa a base de dados antes de cada teste
        clienteRepository.deleteAll();
    }

    @Test
    void testGetClientes() throws Exception {
        // Adiciona clientes para teste
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João");
        cliente1.setSobrenome("Silva");
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria");
        cliente2.setSobrenome("Santos");
        clienteRepository.save(cliente2);


        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetClienteById() throws Exception {
        // O método atual retorna todos os clientes, isso precisa ser corrigido no controller.
        // Este teste falhará até que o método getCliente seja corrigido.
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João");
        cliente1.setSobrenome("Silva");
        clienteRepository.save(cliente1);

        mockMvc.perform(get("/clientes/" + cliente1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // Esse teste só vai passar se o controller for corrigido
    }


    @Test
    void testCadastrarCliente() throws Exception {
        ClienteDTO dto = new ClienteDTO();
        dto.setNome("Teste");
        dto.setSobrenome("Unitário");
        dto.setCpf("12345678900");
        dto.setTelefone("11999999999");
        dto.setEmail("teste@example.com");
        dto.setEndereco("Rua dos testes");
        dto.setSenha("senha123");

        ResultActions result = mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1))); // Assumindo que o ID é gerado automaticamente como 1

    }

    @Test
    void testCadastrarClienteComEmailExistente() throws Exception {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setNome("Teste");
        clienteExistente.setEmail("emailExistente@example.com");
        clienteRepository.save(clienteExistente);

        ClienteDTO dto = new ClienteDTO();
        dto.setNome("Teste");
        dto.setEmail("emailExistente@example.com");

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email já cadastrado"));
    }

    @Test
    void testLoginCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Fulano");
        cliente.setEmail("fulano@email.com");
        cliente.setSenha("senha123");
        clienteRepository.save(cliente);

        ClienteDTO dto = new ClienteDTO();
        dto.setEmail("fulano@email.com");
        dto.setSenha("senha123");

        mockMvc.perform(post("/clientes/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Fulano"));
    }

    @Test
    void testLoginClienteFalha() throws Exception {
        ClienteDTO dto = new ClienteDTO();
        dto.setEmail("fulano@email.com");
        dto.setSenha("senhaErrada");

        mockMvc.perform(post("/clientes/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Email ou senha incorretos"));
    }
}