package DAO;

import Modelo.Categoria;
import Modelo.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, quantidade, id_categoria) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());

            if(produto.getCategoria() != null) {
                stmt.setInt(4, produto.getCategoria().getId_categoria());
            } else {
                return false;
            }

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = """
            SELECT 
                p.id, 
                p.nome, 
                p.quantidade, 
                p.preco, 
                c.id_categoria, 
                c.nome_categoria
            FROM produto p
            INNER JOIN categoria c ON p.id_categoria = c.id_categoria
            ORDER BY p.id DESC
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                Categoria c = new Categoria();

                p.setId_produto(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getDouble("preco"));

                c.setId_categoria(rs.getInt("id_categoria"));
                c.setNome_categoria(rs.getString("nome_categoria"));

                p.setCategoria(c);
                produtos.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, quantidade = ?, id_categoria = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getCategoria().getId_categoria());
            stmt.setInt(5, produto.getId_produto());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id){
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}