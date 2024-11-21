package com.example.demo.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VeiculoDTO {

    @NotEmpty(message = "Marca é obrigatória")
    private String marca;

    @NotEmpty(message = "Modelo é obrigatório")
    private String modelo;

    @Max(value = 2024, message = "Ano não pode ser maior que o ano atual")
    private int ano;

    @NotEmpty(message = "Cor é obrigatória")
    private String cor;

    @Positive(message = "Preço deve ser maior que zero")
    private float preco;

    @NotEmpty(message = "Status é obrigatório")
    private String status;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}