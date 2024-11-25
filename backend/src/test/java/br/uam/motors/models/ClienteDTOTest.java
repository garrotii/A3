package br.uam.motors.models;

import br.uam.motors.models.ClienteDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClienteDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidaClienteDTOComDadosValidos() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNome("João");
        cliente.setSobrenome("Silva");
        cliente.setCpf("12345678900");
        cliente.setEmail("joao.silva@example.com");
        cliente.setSenha("Senha123");

        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(cliente);
        assertTrue(violations.isEmpty()); // Nenhuma violação esperada
    }

    @Test
    void testValidaClienteDTOComNomeInvalido() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setSobrenome("Silva");
        cliente.setCpf("12345678900");
        cliente.setEmail("joao.silva@example.com");
        cliente.setSenha("Senha123");

        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(cliente);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Nome é obrigatório")));
    }

    @Test
    void testValidaClienteDTOComSobrenomeInvalido() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNome("João");
        cliente.setCpf("12345678900");
        cliente.setEmail("joao.silva@example.com");
        cliente.setSenha("Senha123");

        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(cliente);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Sobrenome é obrigatório")));

    }

    @Test
    void testValidaClienteDTOComCpfInvalido() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNome("João");
        cliente.setSobrenome("Silva");
        cliente.setEmail("joao.silva@example.com");
        cliente.setSenha("Senha123");

        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(cliente);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("cpf é obrigatório")));
    }


    @Test
    void testValidaClienteDTOComEmailInvalido() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNome("João");
        cliente.setSobrenome("Silva");
        cliente.setCpf("12345678900");
        cliente.setSenha("Senha123");

        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(cliente);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Email é obrigatório")));
    }

    @Test
    void testValidaClienteDTOComEmailFormatoInvalido() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNome("João");
        cliente.setSobrenome("Silva");
        cliente.setCpf("12345678900");
        cliente.setEmail("joao.silva"); // Email inválido
        cliente.setSenha("Senha123");

        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(cliente);
        assertEquals(1, violations.size()); // Somente a validação de email deve falhar
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email inválido")));

    }

    @Test
    void testValidaClienteDTOComSenhaInvalida() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNome("João");
        cliente.setSobrenome("Silva");
        cliente.setCpf("12345678900");
        cliente.setEmail("joao.silva@example.com");

        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(cliente);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Senha é obrigatório")));
    }
}