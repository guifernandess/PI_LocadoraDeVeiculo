/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jopos;

import Conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vitoria.csilva15
 */
public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private String ano;
    private String placa;
    private String statusDisponivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getStatusDisponivel() {
        return statusDisponivel;
    }

    public void setStatusDisponivel(String statusDisponivel) {
        this.statusDisponivel = statusDisponivel;
    }
    
public void adicionar(Carro carro) throws ClassNotFoundException, SQLException {
    Conexao connm = new Conexao();
    try (Connection conn = connm.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Carro (id, marca, modelo, ano, placa, statusDisponivel)"
                    + "VALUES (?,?,?,?,?,?) ")) {
        stmt.setInt(1, carro.getId());
        stmt.setString(2, carro.getMarca());
        stmt.setString(3, carro.getModelo());
        stmt.setString(4, carro.getAno());
        stmt.setString(6, carro.getPlaca());
        stmt.setString(7, carro.getStatusDisponivel());

        int status = stmt.executeUpdate();

        conn.close();
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    } catch (ClassNotFoundException ex) {
        System.err.println(ex.getMessage());
    }
}
    
    public void excluir() throws ClassNotFoundException, SQLException {
        Conexao connm = new Conexao();
        try (Connection conn = connm.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM CARRO WHERE id = ? ")) {
            stmt.setString(1, "id");
            stmt.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void atualizar(Carro carro) throws ClassNotFoundException, SQLException {
        Conexao connm = new Conexao();
        try {
            Connection conn = connm.obterConexao();
            PreparedStatement stmt = conn.prepareStatement("UPDATE CARRO SET "
                    + "id=?, marca=?, modelo=?, ano=?, placa=?, statusDisponivel=?"
                    + "FROM CARRO WHERE ID = ?");
            stmt.setInt(1, carro.getId());
            stmt.setString(2, carro.getMarca());
            stmt.setString(3, carro.getModelo());
            stmt.setString(4, carro.getAno());
            stmt.setString(6, carro.getPlaca());
            stmt.setString(7, carro.getStatusDisponivel());
           
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    public Carro obter(int id) throws SQLException, Exception {
        Conexao connm = new Conexao();
        String sql = "SELECT * FROM CARRO WHERE (id=?)";
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Connection conn = connm.obterConexao();
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setBoolean(2, true);
            result = preparedStatement.executeQuery();
            if (result.next()) {
                Carro carro = new Carro();
                
                carro.setId(result.getInt("id"));
                carro.setMarca(result.getString("marca"));
                carro.setModelo(result.getString("modelo"));
                carro.setAno(result.getString("ano"));
                carro.setPlaca(result.getString("placa"));
                carro.setStatusDisponivel(result.getString("status disponivel"));
                

                return carro;
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
    
    public List<Carro> procurar(String valor) throws SQLException, Exception {

        String sql = "SELECT * FROM CARRO WHERE ((UPPER(id) LIKE UPPER(?) ";
        List<Carro> listaCarros = null;
        Conexao connm = new Conexao();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Connection conn = connm.obterConexao();
        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "%" + valor + "%");
            result = preparedStatement.executeQuery();
            
            while (result.next()) {
                if (listaCarros == null) {
                    listaCarros= new ArrayList<Carro>();
                }
                Carro carro = new Carro();

                carro.setId(result.getInt("id"));
                carro.setMarca(result.getString("marca"));
                carro.setModelo(result.getString("modelo"));
                carro.setAno(result.getString("ano"));
                carro.setPlaca(result.getString("placa"));
                carro.setStatusDisponivel(result.getString("status disponivel"));
               

                listaCarros.add(carro);
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
        return listaCarros;
    }
}
