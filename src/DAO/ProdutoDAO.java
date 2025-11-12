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
        String sql = "INSERT INTO produtos (nome, preco, quantidade, id_categoria, data_criacao, data_atualizacao) VALUES (?, ?, ?, ?, NOW(), NOW())";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getCategoria().getId_categoria()); // ✅ categoria associada
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
        p.id_produto,
        p.nome,
        p.quantidade,
        p.preco,
        p.data_criacao,
        p.data_atualizacao,
        c.id_categoria,
        c.nome_categoria
    FROM produtos p
    INNER JOIN categorias c ON p.id_categoria = c.id_categoria
    ORDER BY p.id_produto DESC
""";


        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Produto p = new Produto();
                Categoria c = new Categoria();

                p.setId_produto(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getDouble("preco"));
                p.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
                p.setDataAtualizacao(rs.getTimestamp("data_atualizacao").toLocalDateTime());

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
        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ?, id_categoria = ?, data_atualizacao = NOW() WHERE id_produto = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
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
        String sql = "DELETE FROM produtos WHERE id_produto = ?";

        try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
