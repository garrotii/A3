package br.uam.motors.controllers;

import br.uam.motors.models.Veiculo;
import br.uam.motors.models.VeiculoDTO;
import br.uam.motors.repositories.VeiculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Rollback das alterações no banco após cada teste
public class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ObjectMapper objectMapper; //Para converter objetos Java para JSON

    @BeforeEach
    void setUp() {
        veiculoRepository.deleteAll();
    }

    @Test
    void testGetVeiculos() throws Exception {
        // Adiciona veículos para teste
        Veiculo veiculo1 = new Veiculo();
        veiculo1.setMarca("Fiat");
        veiculo1.setModelo("Uno");
        veiculoRepository.save(veiculo1);

        Veiculo veiculo2 = new Veiculo();
        veiculo2.setMarca("VW");
        veiculo2.setModelo("Gol");
        veiculoRepository.save(veiculo2);

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testCadastrarVeiculo() throws Exception {
        VeiculoDTO dto = new VeiculoDTO();
        dto.setMarca("Ford");
        dto.setModelo("Fiesta");
        dto.setAno(2023);
        dto.setCor("Azul");
        dto.setPreco(70000);
        dto.setStatus("Disponível");


        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marca").value("Ford"))
                .andExpect(jsonPath("$.modelo").value("Fiesta"));
    }

    @Test
    void testCadastrarVeiculoComDadosInvalidos() throws Exception {
        VeiculoDTO dto = new VeiculoDTO(); // Dados inválidos (faltam campos obrigatórios)

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()); // Verifica se retorna erro 400
    }
}