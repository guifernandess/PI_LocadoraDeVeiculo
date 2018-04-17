/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jopos;

import Conexao.Conexao;
import exceptions.PessoaException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus Maia
 */
public class pessoa {

    private int id;
    private String nome;
    private String Sobrenome;
    private String endereco;
    private String cpf;
    private Date dataNasc;
    private String email;
    private String telefone;

    public void validarCPF(String cpf) throws PessoaException {

        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

            d1 = d1 + (11 - nCount) * digitoCPF;

            d2 = d2 + (12 - nCount) * digitoCPF;
        };

        resto = (d1 % 11);

        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        resto = (d2 % 11);

        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        if (nDigVerific.equals(nDigResult) == false) {
            throw new PessoaException("CPF INVALIDO!");
        };
    }

    public void adicionar(pessoa p) throws ClassNotFoundException, SQLException {
        Conexao connm = new Conexao();
        try (Connection conn = connm.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO Pessoa (nome, sobrenome, entedereco, cpf, dataNasc, email, telefone)"
                        + "VALUES (?,?,?,?,?,?,?) ")) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getSobrenome());
            stmt.setString(3, p.getEndereco());
            stmt.setString(4, p.getCpf());
            stmt.setDate(5, p.getDataNasc());
            stmt.setString(6, p.getEmail());
            stmt.setString(7, p.getTelefone());

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
                        "DELETE FROM PESSOA WHERE id = ? ")) {
            stmt.setString(1, "id");
            stmt.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void atualizar(pessoa p) throws ClassNotFoundException, SQLException {
        Conexao connm = new Conexao();
        try {
            Connection conn = connm.obterConexao();
            PreparedStatement stmt = conn.prepareStatement("UPDATE PESSOA SET "
                    + "nome = ?,sobrenomeo =?,entedereco=?, cpf=?, dataNasc=?, email=?, telefone=?"
                    + "FROM PRODUTO WHERE ID = ?");
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getSobrenome());
            stmt.setString(3, p.getEndereco());
            stmt.setString(4, p.getCpf());
            stmt.setDate(5, p.getDataNasc());
            stmt.setString(6, p.getEmail());
            stmt.setString(7, p.getTelefone());
            stmt.setLong(8, p.getId());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public pessoa obter(int id) throws SQLException, Exception {
        Conexao connm = new Conexao();
        String sql = "SELECT * FROM PESSOA WHERE (id=?)";
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Connection conn = connm.obterConexao();
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setBoolean(2, true);
            result = preparedStatement.executeQuery();
            if (result.next()) {
                pessoa p = new pessoa();
                
                p.setId(result.getInt("id"));
                p.setNome(result.getString("nome"));
                p.setSobrenome(result.getString("sobrenome"));
                p.setEndereco(result.getString("entedereco"));
                p.setCpf(result.getString("cpf"));
                p.setDataNasc(result.getDate("dataNasc"));
                p.setEmail(result.getString("email"));
                p.setTelefone(result.getString("telefone"));

                return p;
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

    public List<pessoa> procurar(String valor) throws SQLException, Exception {

        String sql = "SELECT * FROM PESSOA WHERE ((UPPER(nome) LIKE UPPER(?) ";
        List<pessoa> listaPessoas = null;
        Conexao connm = new Conexao();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Connection conn = connm.obterConexao();
        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "%" + valor + "%");
            result = preparedStatement.executeQuery();
            
            while (result.next()) {
                if (listaPessoas == null) {
                    listaPessoas= new ArrayList<pessoa>();
                }
                pessoa p = new pessoa();

                p.setId(result.getInt("id"));
                p.setNome(result.getString("nome"));
                p.setSobrenome(result.getString("sobrenome"));
                p.setEndereco(result.getString("entedereco"));
                p.setCpf(result.getString("cpf"));
                p.setDataNasc(result.getDate("dataNasc"));
                p.setEmail(result.getString("email"));
                p.setTelefone(result.getString("telefone"));

                listaPessoas.add(p);
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
        return listaPessoas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return Sobrenome;
    }

    public void setSobrenome(String Sobrenome) {
        this.Sobrenome = Sobrenome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpg) {
        this.cpf = cpf;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
