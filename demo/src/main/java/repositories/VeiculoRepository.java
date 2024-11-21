package repositories;

import com.example.demo.model.Veiculo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends CrudRepository<Veiculo, Integer> {
    Iterable<Veiculo> findByMarca(String marca);
}