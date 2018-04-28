/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.jopos;

import Conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class Pedido {

    private long Id;
    private long IdPessoa;
    private long IdPlano;
    private long IdCarro;
    private long IdFuncionario;
    private long Idfilial;
    private String DataDevolucao;
    private int DiasAlugados;
    private String Status;
    private double ValorTotal;

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public long getIdPessoa() {
        return IdPessoa;
    }

    public void setIdPessoa(long IdPessoa) {
        this.IdPessoa = IdPessoa;
    }

    public long getIdPlano() {
        return IdPlano;
    }

    public void setIdPlano(long IdPlano) {
        this.IdPlano = IdPlano;
    }

    public long getIdCarro() {
        return IdCarro;
    }

    public void setIdCarro(long IdCarro) {
        this.IdCarro = IdCarro;
    }

    public long getIdFuncionario() {
        return IdFuncionario;
    }

    public void setIdFuncionario(long IdFuncionario) {
        this.IdFuncionario = IdFuncionario;
    }

    public long getIdfilial() {
        return Idfilial;
    }

    public void setIdfilial(long Idfilial) {
        this.Idfilial = Idfilial;
    }

    public String getDataDevolucao() {
        return DataDevolucao;
    }

    public void setDataDevolucao(String DataDevolucao) {
        this.DataDevolucao = DataDevolucao;
    }

    public int getDiasAlugados() {
        return DiasAlugados;
    }

    public void setDiasAlugados(int DiasAlugados) {
        this.DiasAlugados = DiasAlugados;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public double getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(double ValorTotal) {
        this.ValorTotal = ValorTotal;
    }

    public void adicionarPedido(Pedido pedido) throws ClassNotFoundException, SQLException {
        Conexao connm = new Conexao();
        try (Connection conn = connm.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO Pedido (id, IdPessoa, IdPlano, IdCarro, IdFuncionario, Idfilial"
                        + "DataDevolucao, DiasAlugados, Status, ValorTotal)"
                        + "VALUES (?,?,?,?,?,?,?,?,?,?) ")) {
            stmt.setLong(1, pedido.getId());
            stmt.setLong(2, pedido.getIdPessoa());
            stmt.setLong(3, pedido.getIdPlano());
            stmt.setLong(4, pedido.getIdCarro());
            stmt.setLong(5, pedido.getIdFuncionario());
            stmt.setLong(6, pedido.getIdfilial());
            stmt.setString(7, pedido.getDataDevolucao());
            stmt.setInt(8, pedido.getDiasAlugados());
            stmt.setString(9, pedido.getStatus());
            stmt.setDouble(10, pedido.getValorTotal());

            int status = stmt.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void atualizarPedido(Pedido pedido) throws ClassNotFoundException, SQLException {
        Conexao connm = new Conexao();
        try {
            Connection conn = connm.obterConexao();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Pedido SET "
                    + "id=?, IdPessoa=?, IdPlano=?, IdCarro=?, IdFuncionario=?, Idfilial=?"
                    + "DataDevolucao=?, DiasAlugados=?, Status=?, ValorTota=?"
                    + "FROM Pedido WHERE ID = ?");
            stmt.setLong(1, pedido.getId());
            stmt.setLong(2, pedido.getIdPessoa());
            stmt.setLong(3, pedido.getIdPlano());
            stmt.setLong(4, pedido.getIdCarro());
            stmt.setLong(5, pedido.getIdFuncionario());
            stmt.setLong(6, pedido.getIdfilial());
            stmt.setString(7, pedido.getDataDevolucao());
            stmt.setInt(8, pedido.getDiasAlugados());
            stmt.setString(9, pedido.getStatus());
            stmt.setDouble(10, pedido.getValorTotal());

            stmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public Pedido obter(int id) throws SQLException, Exception {
        Conexao connm = new Conexao();
        String sql = "SELECT * FROM Pedido WHERE (id=?)";
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Connection conn = connm.obterConexao();
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setBoolean(2, true);
            result = preparedStatement.executeQuery();
            if (result.next()) {
                Pedido pedido = new Pedido();
                
                pedido.setId(result.getLong("id"));
                pedido.setIdPessoa(result.getLong("IdPessoa"));
                pedido.setIdPlano(result.getLong("IdPlano"));
                pedido.setIdCarro(result.getLong("IdCarro"));
                pedido.setIdFuncionario(result.getLong("IdFuncionario"));
                pedido.setIdfilial(result.getLong("Idfilial"));
                pedido.setDataDevolucao(result.getString("DataDevolucao"));
                pedido.setDiasAlugados(result.getInt("DiasAlugados"));
                pedido.setStatus(result.getString("Status"));
                pedido.setValorTotal(result.getDouble("ValorTotal"));
                

                return pedido;
            }
        } finally {
            if (result != null && !result.isClosed()) {
                result.close();
            }
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return null;
    }
    
    public List<Pedido> procurar(Long Id) throws SQLException, Exception {

        String sql = "SELECT * FROM Pedido WHERE id = ? ";
        List<Pedido> listaPedido = null;
        Conexao connm = new Conexao();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Connection conn = connm.obterConexao();
        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, Id);
            result = preparedStatement.executeQuery();
            
            while (result.next()) {
                if (listaPedido == null) {
                    listaPedido = new ArrayList<Pedido>();
                }
                Pedido pedido = new Pedido();

                pedido.setId(result.getLong("id"));
                pedido.setIdPessoa(result.getLong("IdPessoa"));
                pedido.setIdPlano(result.getLong("IdPlano"));
                pedido.setIdCarro(result.getLong("IdCarro"));
                pedido.setIdFuncionario(result.getLong("IdFuncionario"));
                pedido.setIdfilial(result.getLong("Idfilial"));
                pedido.setDataDevolucao(result.getString("DataDevolucao"));
                pedido.setDiasAlugados(result.getInt("DiasAlugados"));
                pedido.setStatus(result.getString("Status"));
                pedido.setValorTotal(result.getDouble("ValorTotal"));
               

                listaPedido.add(pedido);
            }
        } finally {
            if (result != null && !result.isClosed()) {
                result.close();
            }
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return listaPedido;
    }
    public Double calcularMulta (double valor, Pedido pedido){
        double aux = valor * 2;
        return pedido.DiasAlugados * aux;
    } 
}
