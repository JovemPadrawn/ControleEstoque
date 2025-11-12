package Modelo;

import java.time.LocalDateTime;

public class Produto {
    private int id_produto;
    private String nome;
    private int quantidade;
    private double preco;
    //private int id_categoria;
    private Categoria categoria;
    private LocalDateTime data_criacao;
    private LocalDateTime data_atualizacao;

    public Produto() {
    }

    public Produto(String nome, int quantidade, double preco, Categoria categoria) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Getters e Setters
    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getDataCriacao() {
        return data_criacao;
    }

    public void setDataCriacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return data_atualizacao;
    }

    public void setDataAtualizacao(LocalDateTime data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id_produto=" + id_produto +
                ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", categoria=" + (categoria != null ? categoria.getNome_categoria() : "Sem categoria") +
                ", data_criacao=" + data_criacao +
                ", data_atualizacao=" + data_atualizacao +
                '}';
    }
}
