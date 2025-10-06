
/**
 *
 * @author josue
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
               

public class ProdutoDAO {
    
    public void cadastrar(Produtos p) throws SQLException {
        String sql = "INSERT INTO produto (descricao, valor) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getDescricao());
            stmt.setDouble(2, p.getValor());
            stmt.executeUpdate();
        }
    }

    public void alterar(Produtos p) throws SQLException {
        String sql = "UPDATE produto SET descricao=?, valor=? WHERE codigo=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getDescricao());
            stmt.setDouble(2, p.getValor());
            stmt.setInt(3, p.getCodigo());
            stmt.executeUpdate();
        }
    }

    public void excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM produto WHERE codigo=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }

    public List<Produtos> listar() throws SQLException {
        List<Produtos> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produtos p = new Produtos();
                p.setCodigo(rs.getInt("codigo"));
                p.setDescricao(rs.getString("descricao"));
                p.setValor(rs.getDouble("valor"));
               
                lista.add(p);
            }
        }
        return lista;
    }
}
