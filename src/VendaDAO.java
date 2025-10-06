/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josue
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class VendaDAO {
    
  public void cadastrar(Vendas venda) {
        Connection con = Conexao.conectar();
        PreparedStatement pstVenda = null;
        PreparedStatement pstItens = null;
        ResultSet rs = null;

        try {
            
            String sqlVenda = "INSERT INTO venda (nome_cliente, cpf, total) VALUES (?, ?, ?)";
            pstVenda = con.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
            pstVenda.setString(1, venda.getNomeCliente());
            pstVenda.setString(2, venda.getCpf());
            pstVenda.setDouble(3, venda.getTotal());
            pstVenda.executeUpdate();

            
            rs = pstVenda.getGeneratedKeys();
            int codigoVenda = 0;
            if (rs.next()) {
                codigoVenda = rs.getInt(1);
            }

           
            String sqlItens = "INSERT INTO item_venda (codigo_venda, codigo_produto, valor) VALUES (?, ?, ?)";
            pstItens = con.prepareStatement(sqlItens);

            for (Produtos p : venda.getItens()) {
                pstItens.setInt(1, codigoVenda);
                pstItens.setInt(2, p.getCodigo());
                pstItens.setDouble(3, p.getValor());
                pstItens.addBatch();
            }
            pstItens.executeBatch();

            JOptionPane.showMessageDialog(null, "Venda cadastrada com sucesso!\nTotal: R$ " + venda.getTotal());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar venda: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstVenda != null) pstVenda.close();
                if (pstItens != null) pstItens.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }
    }


    public void excluir(int codigoVenda) throws SQLException {
       
        String sqlItens = "DELETE FROM item_venda WHERE codigo_venda=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sqlItens)) {
            stmt.setInt(1, codigoVenda);
            stmt.executeUpdate();
        }
       
        String sqlVenda = "DELETE FROM venda WHERE codigo=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sqlVenda)) {
            stmt.setInt(1, codigoVenda);
            stmt.executeUpdate();
        }
    }

    public List<Vendas> listar() throws SQLException {
        List<Vendas> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vendas v = new Vendas();
                v.setCodigo(rs.getInt("codigo"));
                v.setNomeCliente(rs.getString("nome_cliente"));
                v.setCpf(rs.getString("cpf"));
                v.setTotal(rs.getDouble("total"));
               
                lista.add(v);
            }
        }
        return lista;
    }
    
    
    public void alterar(Vendas venda) throws SQLException {
    Connection con = Conexao.conectar(); 
    PreparedStatement ps = null;
    PreparedStatement psItens = null;

    try {
        
        String sqlVenda = "UPDATE vendas SET nome_cliente=?, cpf=?, total=? WHERE id=?";
        ps = con.prepareStatement(sqlVenda);
        ps.setString(1, venda.getNomeCliente());
        ps.setString(2, venda.getCpf());
        ps.setDouble(3, venda.getTotal());
        ps.setInt(4, venda.getCodigo());
        ps.executeUpdate();

        
        String sqlDeleteItens = "DELETE FROM itens_venda WHERE venda_id=?";
        psItens = con.prepareStatement(sqlDeleteItens);
        psItens.setInt(1, venda.getCodigo());
        psItens.executeUpdate();
        psItens.close();

        
        String sqlInserirItem = "INSERT INTO itens_venda (venda_id, produto_id, descricao, valor) VALUES (?, ?, ?, ?)";
        psItens = con.prepareStatement(sqlInserirItem);
        for (Produtos p : venda.getItens()) {
            psItens.setInt(1, venda.getCodigo());
            psItens.setInt(2, p.getCodigo());
            psItens.setString(3, p.getDescricao());
            psItens.setDouble(4, p.getValor());
            psItens.executeUpdate();
        }

    } finally {
        if (psItens != null) psItens.close();
        if (ps != null) ps.close();
        con.close();
    }
}
    
}
