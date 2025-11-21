package Modelo;

public class Produto {
    private int id_produto;
    private String nome;
    private int quantidade;
    private double preco;
    private Categoria categoria;

    public Produto() { }

    public Produto(String nome, int quantidade, double preco, Categoria categoria) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.categoria = categoria;
    }

    public int getId_produto() { return id_produto; }
    public void setId_produto(int id_produto) { this.id_produto = id_produto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}