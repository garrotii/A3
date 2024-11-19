package controllers;

import com.example.demo.model.Veiculo;
import com.example.demo.model.VeiculoDTO;
import repositories.VeiculoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    public VeiculoRepository veiculoRepository;

    // Endpoint para obter todos os veículos
    @GetMapping
    public ResponseEntity<Iterable<Veiculo>> getVeiculos() {
        Iterable<Veiculo> veiculos = veiculoRepository.findAll();
        return ResponseEntity.ok(veiculos);
    }

    // Endpoint para obter um veículo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> getVeiculoById(@PathVariable int id) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(id);
        return veiculo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para obter veículos por marca
    @GetMapping("/marca/{marca}")
    public ResponseEntity<Iterable<Veiculo>> getVeiculosByMarca(@PathVariable String marca) {
        Iterable<Veiculo> veiculos = veiculoRepository.findByMarca(marca);
        return ResponseEntity.ok(veiculos);
    }

    // Endpoint para cadastrar um novo veículo
    @PostMapping
    public ResponseEntity<?> cadastrarVeiculo(@Valid @RequestBody VeiculoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setAno(dto.getAno());
        veiculo.setCor(dto.getCor());
        veiculo.setPreco(dto.getPreco());
        veiculo.setStatus(dto.getStatus());

        veiculoRepository.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculo);
    }

    // Endpoint para atualizar um veículo
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVeiculo(@PathVariable int id, @Valid @RequestBody VeiculoDTO dto, BindingResult result) {
        Optional<Veiculo> veiculoExistente = veiculoRepository.findById(id);
        if (!veiculoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado");
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        Veiculo veiculo = veiculoExistente.get();
        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setAno(dto.getAno());
        veiculo.setCor(dto.getCor());
        veiculo.setPreco(dto.getPreco());
        veiculo.setStatus(dto.getStatus());

        veiculoRepository.save(veiculo);
        return ResponseEntity.ok(veiculo);
    }

    // Endpoint para deletar um veículo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarVeiculo(@PathVariable int id) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(id);
        if (!veiculo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado");
        }
        veiculoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


