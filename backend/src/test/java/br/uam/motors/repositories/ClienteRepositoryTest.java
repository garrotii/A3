package br.uam.motors.repositories;

import br.uam.motors.models.Cliente;
import br.uam.motors.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll(); // Limpa o banco antes de cada teste
    }

    @Test
    void testFindByEmail() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEmail("joao@example.com");
        entityManager.persist(cliente);
        entityManager.flush();//Garante a persistencia dos dados

        Cliente foundCliente = clienteRepository.findByEmail("joao@example.com");
        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getEmail()).isEqualTo("joao@example.com");
    }


    @Test
    void testFindByCpf() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setCpf("12345678900");
        entityManager.persist(cliente);
        entityManager.flush();

        Cliente foundCliente = clienteRepository.findByCpf("12345678900");
        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getCpf()).isEqualTo("12345678900");
    }

    @Test
    void testFindByEmailAndSenha() {
        Cliente cliente = new Cliente();
        cliente.setNome("Pedro");
        cliente.setEmail("pedro@example.com");
        cliente.setSenha("senha123");
        entityManager.persist(cliente);
        entityManager.flush();

        Cliente foundCliente = clienteRepository.findByEmailAndSenha("pedro@example.com", "senha123");
        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getEmail()).isEqualTo("pedro@example.com");
        assertThat(foundCliente.getSenha()).isEqualTo("senha123");


        Cliente notFoundCliente = clienteRepository.findByEmailAndSenha("pedro@example.com", "senhaErrada");
        assertThat(notFoundCliente).isNull();

    }


    @Test
    void testSaveCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome("Teste");
        cliente.setEmail("teste@teste.com");
        cliente.setCpf("00000000000");
        cliente.setSenha("123456");

        Cliente savedCliente = clienteRepository.save(cliente);
        assertThat(savedCliente.getId()).isNotNull();

    }
}