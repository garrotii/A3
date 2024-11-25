package br.uam.motors.repositories;

import br.uam.motors.models.Veiculo;
import br.uam.motors.repositories.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class VeiculoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @BeforeEach
    void setUp() {
        veiculoRepository.deleteAll();
    }

    @Test
    void testSaveVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca("Fiat");
        veiculo.setModelo("Uno");
        veiculo.setAno(2023);
        veiculo.setCor("Azul");
        veiculo.setPreco(30000f);
        veiculo.setStatus("Disponível");

        Veiculo savedVeiculo = veiculoRepository.save(veiculo);
        assertThat(savedVeiculo.getId()).isNotNull();
    }

    @Test
    void testFindAllVeiculos() {
        Veiculo veiculo1 = new Veiculo();
        veiculo1.setMarca("Fiat");
        veiculo1.setModelo("Uno");
        entityManager.persist(veiculo1);

        Veiculo veiculo2 = new Veiculo();
        veiculo2.setMarca("Volkswagen");
        veiculo2.setModelo("Gol");
        entityManager.persist(veiculo2);
        entityManager.flush();


        Iterable<Veiculo> veiculos = veiculoRepository.findAll();
        assertThat(veiculos).hasSize(2);
    }

    @Test
    void testDeleteVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca("Fiat");
        veiculo.setModelo("Uno");
        Veiculo savedVeiculo = entityManager.persist(veiculo);
        entityManager.flush();

        veiculoRepository.deleteById(savedVeiculo.getId());
        assertThat(veiculoRepository.findById(savedVeiculo.getId())).isEmpty();

    }

}