package br.uam.motors.models;

import br.uam.motors.models.VeiculoDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class VeiculoDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidaVeiculoDTOComDadosValidos() {
        VeiculoDTO veiculo = new VeiculoDTO();
        veiculo.setMarca("Fiat");
        veiculo.setModelo("Uno");
        veiculo.setAno(2023);
        veiculo.setCor("Vermelha");
        veiculo.setPreco(30000f); //Use 'f' para indicar float
        veiculo.setStatus("Disponível");

        Set<ConstraintViolation<VeiculoDTO>> violations = validator.validate(veiculo);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidaVeiculoDTOComMarcaInvalida() {
        VeiculoDTO veiculo = new VeiculoDTO();
        veiculo.setModelo("Uno");
        veiculo.setAno(2023);
        veiculo.setCor("Vermelha");
        veiculo.setPreco(30000f);
        veiculo.setStatus("Disponível");

        Set<ConstraintViolation<VeiculoDTO>> violations = validator.validate(veiculo);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Marca é obrigatória")));
    }

    @Test
    void testValidaVeiculoDTOComModeloInvalido() {
        VeiculoDTO veiculo = new VeiculoDTO();
        veiculo.setMarca("Fiat");
        veiculo.setAno(2023);
        veiculo.setCor("Vermelha");
        veiculo.setPreco(30000f);
        veiculo.setStatus("Disponível");

        Set<ConstraintViolation<VeiculoDTO>> violations = validator.validate(veiculo);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Modelo é obrigatório")));
    }

    @Test
    void testValidaVeiculoDTOComAnoInvalido() {
        VeiculoDTO veiculo = new VeiculoDTO();
        veiculo.setMarca("Fiat");
        veiculo.setModelo("Uno");
        veiculo.setCor("Vermelha");
        veiculo.setPreco(30000f);
        veiculo.setStatus("Disponível");

        Set<ConstraintViolation<VeiculoDTO>> violations = validator.validate(veiculo);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Ano é obrigatório")));
    }

    @Test
    void testValidaVeiculoDTOComCorInvalida() {
        VeiculoDTO veiculo = new VeiculoDTO();
        veiculo.setMarca("Fiat");
        veiculo.setModelo("Uno");
        veiculo.setAno(2023);
        veiculo.setPreco(30000f);
        veiculo.setStatus("Disponível");

        Set<ConstraintViolation<VeiculoDTO>> violations = validator.validate(veiculo);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Cor é obrigatória")));
    }

    @Test
    void testValidaVeiculoDTOComPrecoInvalido() {
        VeiculoDTO veiculo = new VeiculoDTO();
        veiculo.setMarca("Fiat");
        veiculo.setModelo("Uno");
        veiculo.setAno(2023);
        veiculo.setCor("Vermelha");
        veiculo.setPreco(-100f); // Preço negativo
        veiculo.setStatus("Disponível");

        Set<ConstraintViolation<VeiculoDTO>> violations = validator.validate(veiculo);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Preço deve ser maior que zero")));
    }

    @Test
    void testValidaVeiculoDTOComStatusInvalido() {
        VeiculoDTO veiculo = new VeiculoDTO();
        veiculo.setMarca("Fiat");
        veiculo.setModelo("Uno");
        veiculo.setAno(2023);
        veiculo.setCor("Vermelha");
        veiculo.setPreco(30000f);


        Set<ConstraintViolation<VeiculoDTO>> violations = validator.validate(veiculo);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Status é obrigatório")));
    }
}