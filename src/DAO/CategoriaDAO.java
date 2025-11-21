package DAO;

import Modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public boolean inserir(Categoria categoria){
        String sql = "INSERT INTO categoria (nome_categoria) VALUES (?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, categoria.getNome_categoria());
            stmt.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Categoria> listarTodos(){
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria ORDER BY nome_categoria ASC";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Categoria c = new Categoria();
                c.setId_categoria(rs.getInt("id_categoria"));
                c.setNome_categoria(rs.getString("nome_categoria"));
                categorias.add(c);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return categorias;
    }

    public boolean atualizar(Categoria categoria){
        String sql = "UPDATE categoria SET nome_categoria = ? WHERE id_categoria = ?";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, categoria.getNome_categoria());
            stmt.setInt(2, categoria.getId_categoria());
            stmt.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id){
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}