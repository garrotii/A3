package br.uam.motors.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculoTest {

    @Test
    public void testGettersAndSetters() {
        // Criação de um objeto Veiculo
        Veiculo veiculo = new Veiculo();

        // Configuração dos valores com os setters
        veiculo.setId(1);
        veiculo.setMarca("Toyota");
        veiculo.setModelo("Corolla");
        veiculo.setAno(2023);
        veiculo.setCor("Prata");
        veiculo.setPreco(120000.50f);
        veiculo.setStatus("Disponível");

        // Verificação dos valores com os getters
        assertEquals(1, veiculo.getId());
        assertEquals("Toyota", veiculo.getMarca());
        assertEquals("Corolla", veiculo.getModelo());
        assertEquals(2023, veiculo.getAno());
        assertEquals("Prata", veiculo.getCor());
        assertEquals(120000.50f, veiculo.getPreco(), 0.01); // Tolerância para float
        assertEquals("Disponível", veiculo.getStatus());
    }

    @Test
    public void testDefaultValues() {
        // Criação de um objeto Veiculo
        Veiculo veiculo = new Veiculo();

        // Verificação dos valores padrão (objetos recém-criados devem ter valores padrão)
        assertEquals(0, veiculo.getId());
        assertNull(veiculo.getMarca());
        assertNull(veiculo.getModelo());
        assertEquals(0, veiculo.getAno());
        assertNull(veiculo.getCor());
        assertEquals(0.0f, veiculo.getPreco(), 0.01);
        assertNull(veiculo.getStatus());
    }

    @Test
    public void testInvalidYear() {
        // Teste para validar anos fora de um intervalo lógico
        Veiculo veiculo = new Veiculo();
        veiculo.setAno(1800); // Ano inválido hipotético
        assertTrue(veiculo.getAno() < 1900 || veiculo.getAno() > 2100, "O ano não deveria ser válido: " + veiculo.getAno());
    }
}